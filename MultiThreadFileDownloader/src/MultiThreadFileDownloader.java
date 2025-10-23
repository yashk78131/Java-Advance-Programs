import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * MultiThreadFileDownloader
 *
 * Usage:
 *   java MultiThreadFileDownloader <fileURL> <outputFilePath> [threads]
 *
 * Example:
 *   java MultiThreadFileDownloader https://example.com/large.zip downloaded.zip 4
 *
 * Notes:
 * - If server supports "Accept-Ranges: bytes" and provides Content-Length, the downloader will
 *   split work across threads. Otherwise it falls back to a single-thread download.
 */
public class MultiThreadFileDownloader {

    private static final int DEFAULT_THREADS = 4;
    private static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java MultiThreadFileDownloader <fileURL> <outputFilePath> [threads]");
            return;
        }

        String fileURL = args[0];
        String outputFile = args[1];
        int threads = args.length >= 3 ? Integer.parseInt(args[2]) : DEFAULT_THREADS;

        try {
            download(fileURL, outputFile, threads);
        } catch (Exception e) {
            System.err.println("Download failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void download(String fileURL, String outputFile, int threads) throws Exception {
        System.out.println("Preparing to download: " + fileURL);
        URL url = new URL(fileURL);

        HttpURLConnection headConn = (HttpURLConnection) url.openConnection();
        headConn.setRequestMethod("HEAD");
        headConn.setConnectTimeout(10_000);
        headConn.setReadTimeout(10_000);
        headConn.connect();

        long contentLength = headConn.getContentLengthLong();
        String acceptRanges = headConn.getHeaderField("Accept-Ranges");
        boolean supportsRanges = (acceptRanges != null && acceptRanges.equalsIgnoreCase("bytes")) && contentLength > 0;

        // Some servers don't send Accept-Ranges but still support Range -> try a small ranged request probe if contentLength > 0
        if (!supportsRanges && contentLength > 0) {
            supportsRanges = probeRangeSupport(url);
        }

        headConn.disconnect();

        if (contentLength <= 0) {
            System.out.println("Warning: Server did not provide Content-Length. Falling back to single-threaded download.");
            supportsRanges = false;
        }

        if (!supportsRanges) {
            singleThreadDownload(url, outputFile);
            return;
        }

        System.out.printf("Content-Length: %d bytes. Using %d threads.%n", contentLength, threads);

        RandomAccessFile raf = new RandomAccessFile(outputFile, "rw");
        raf.setLength(contentLength);
        raf.close();

        long chunkSize = contentLength / threads;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Future<Boolean>> futures = new ArrayList<>();
        AtomicLong downloaded = new AtomicLong(0);
        CountDownLatch latch = new CountDownLatch(threads);

        ScheduledExecutorService progressReporter = Executors.newSingleThreadScheduledExecutor();
        progressReporter.scheduleAtFixedRate(() -> {
            long d = downloaded.get();
            double percent = contentLength > 0 ? (d * 100.0 / contentLength) : -1;
            if (percent >= 0) {
                System.out.printf("Progress: %.2f%% (%d / %d bytes)%n", percent, d, contentLength);
            } else {
                System.out.printf("Progress: %d bytes downloaded%n", d);
            }
        }, 1, 1, TimeUnit.SECONDS);

        for (int i = 0; i < threads; i++) {
            long start = i * chunkSize;
            long end = (i == threads - 1) ? contentLength - 1 : (start + chunkSize - 1);
            int part = i + 1;

            futures.add(executor.submit(() -> {
                boolean ok = downloadChunk(url, outputFile, start, end, downloaded, part);
                latch.countDown();
                return ok;
            }));
        }

        // Wait for all parts to complete (or cancel on failure)
        latch.await();
        progressReporter.shutdownNow();
        executor.shutdown();

        // verify all futures succeeded
        boolean allOk = true;
        for (Future<Boolean> f : futures) {
            try {
                if (!f.get()) allOk = false;
            } catch (Exception e) {
                allOk = false;
            }
        }

        if (allOk) {
            System.out.println("✅ Download completed successfully: " + outputFile);
        } else {
            System.err.println("❌ Some parts failed — you may have a corrupted file. Consider re-running.");
        }
    }

    private static boolean probeRangeSupport(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Range", "bytes=0-0");
            conn.setConnectTimeout(10_000);
            conn.setReadTimeout(10_000);
            int code = conn.getResponseCode();
            conn.disconnect();
            return code == 206; // Partial Content
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean downloadChunk(URL url, String outputFile, long start, long end, AtomicLong downloaded, int part) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Range", "bytes=" + start + "-" + end);
            conn.setConnectTimeout(15_000);
            conn.setReadTimeout(30_000);

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_PARTIAL && responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("Part " + part + ": server returned response code " + responseCode);
                conn.disconnect();
                return false;
            }

            try (InputStream in = conn.getInputStream();
                 RandomAccessFile raf = new RandomAccessFile(outputFile, "rw")) {

                raf.seek(start);
                byte[] buffer = new byte[BUFFER_SIZE];
                int read;
                long totalRead = 0;
                while ((read = in.read(buffer)) != -1) {
                    raf.write(buffer, 0, read);
                    downloaded.addAndGet(read);
                    totalRead += read;
                }
                // Sanity check
                long expected = end - start + 1;
                if (totalRead < expected) {
                    System.err.printf("Part %d: expected %d bytes but read %d bytes.%n", part, expected, totalRead);
                } else {
                    System.out.println("Part " + part + " done (" + totalRead + " bytes).");
                }
            } finally {
                conn.disconnect();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Part " + part + " failed: " + e.getMessage());
            return false;
        }
    }

    private static void singleThreadDownload(URL url, String outputFile) {
        System.out.println("Starting single-threaded download...");
        try (InputStream in = url.openStream();
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            long total = 0;
            long lastReport = System.currentTimeMillis();

            while ((read = in.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
                total += read;

                if (System.currentTimeMillis() - lastReport >= 1000) {
                    System.out.printf("Downloaded %d bytes%n", total);
                    lastReport = System.currentTimeMillis();
                }
            }
            System.out.println("✅ Single-threaded download completed: " + outputFile);
        } catch (IOException e) {
            System.err.println("Single-threaded download failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

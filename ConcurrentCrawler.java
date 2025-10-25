// File: ConcurrentCrawler.java
import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class ConcurrentCrawler {
    private final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final ExecutorService executor;
    private final Set<String> visited = ConcurrentHashMap.newKeySet();
    private final Pattern href = Pattern.compile("href\\s*=\\s*\"(http[s]?://[^\"]+)\"", Pattern.CASE_INSENSITIVE);

    public ConcurrentCrawler(int threads) { this.executor = Executors.newFixedThreadPool(threads); }

    public CompletableFuture<Void> crawl(String startUrl, int maxDepth) {
        return crawlRecursive(startUrl, 0, maxDepth);
    }

    private CompletableFuture<Void> crawlRecursive(String url, int depth, int maxDepth) {
        if (depth > maxDepth || !visited.add(url)) return CompletableFuture.completedFuture(null);
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        return client.sendAsync(req, HttpResponse.BodyHandlers.ofString())
            .thenComposeAsync(resp -> {
                System.out.println("[" + depth + "] " + url + " -> " + resp.statusCode());
                if (depth == maxDepth) return CompletableFuture.completedFuture(null);
                Matcher m = href.matcher(resp.body());
                List<CompletableFuture<Void>> futures = new ArrayList<>();
                while (m.find()) {
                    String link = m.group(1);
                    futures.add(crawlRecursive(link, depth + 1, maxDepth));
                }
                return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            }, executor)
            .exceptionally(e -> { System.err.println("Failed: " + url + " -> " + e.getMessage()); return null; });
    }

    public void shutdown() { executor.shutdown(); }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java ConcurrentCrawler <startUrl> <maxDepth> [threads]");
            return;
        }
        String start = args[0];
        int depth = Integer.parseInt(args[1]);
        int threads = args.length >= 3 ? Integer.parseInt(args[2]) : 8;
        ConcurrentCrawler c = new ConcurrentCrawler(threads);
        c.crawl(start, depth).join();
        c.shutdown();
    }
}

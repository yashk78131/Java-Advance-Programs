// File: DirectoryWatcher.java
import java.nio.file.*;
import java.io.*;
import java.util.concurrent.*;

public class DirectoryWatcher {
    public static void main(String[] args) throws Exception {
        Path dir = Paths.get(args.length > 0 ? args[0] : ".");
        WatchService ws = FileSystems.getDefault().newWatchService();
        dir.register(ws, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
        System.out.println("Watching " + dir.toAbsolutePath());
        while (true) {
            WatchKey key = ws.take();
            for (WatchEvent<?> ev : key.pollEvents()) {
                Path p = dir.resolve((Path) ev.context());
                if (Files.isRegularFile(p) && p.toString().endsWith(".txt")) {
                    System.out.println("Processing " + p);
                    String content = Files.readString(p);
                    System.out.println("First line: " + content.lines().findFirst().orElse("<empty>"));
                }
            }
            if (!key.reset()) break;
        }
    }
}

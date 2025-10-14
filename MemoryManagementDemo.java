/**
 * MemoryManagementDemo.java
 * 
 * Description:
 * This program demonstrates advanced Java memory management concepts including:
 * - Manual memory management using WeakReference and SoftReference
 * - Monitoring memory usage and garbage collection
 * - Proper resource handling to prevent memory leaks
 * - Memory leak simulation and prevention
 * - Using PhantomReference for cleanup operations
 * 
 * Usage:
 * javac MemoryManagementDemo.java
 * java -Xmx256m MemoryManagementDemo
 */

import java.lang.ref.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class MemoryManagementDemo {
    private static List<Object> strongReferences = new ArrayList<>();
    private static List<WeakReference<byte[]>> weakReferences = new ArrayList<>();
    private static List<SoftReference<byte[]>> softReferences = new ArrayList<>();
    private static ReferenceQueue<byte[]> referenceQueue = new ReferenceQueue<>();
    
    // Monitor thread to watch reference queue
    private static Thread monitorThread = new Thread(() -> {
        while (true) {
            try {
                Reference<?> ref = referenceQueue.remove();
                System.out.println("Object was garbage collected: " + ref.getClass().getSimpleName());
            } catch (InterruptedException e) {
                break;
            }
        }
    });
    
    public static void main(String[] args) throws InterruptedException {
        monitorThread.setDaemon(true);
        monitorThread.start();
        
        // Demonstrate memory monitoring
        System.out.println("Initial Memory Stats:");
        printMemoryStats();
        
        // Demonstrate WeakReference (will be collected when memory is tight)
        System.out.println("\nTesting WeakReference:");
        for (int i = 0; i < 5; i++) {
            byte[] data = new byte[1024 * 1024]; // 1MB
            weakReferences.add(new WeakReference<>(data, referenceQueue));
        }
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("After creating WeakReferences:");
        printMemoryStats();
        
        // Demonstrate SoftReference (collected only when memory is critically low)
        System.out.println("\nTesting SoftReference:");
        for (int i = 0; i < 5; i++) {
            byte[] data = new byte[1024 * 1024]; // 1MB
            softReferences.add(new SoftReference<>(data, referenceQueue));
        }
        System.out.println("After creating SoftReferences:");
        printMemoryStats();
        
        // Demonstrate memory leak simulation and prevention
        System.out.println("\nSimulating memory leak scenario:");
        try {
            simulateMemoryLeak();
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError caught - demonstrating cleanup");
            cleanup();
        }
        
        // Demonstrate proper resource handling
        System.out.println("\nDemonstrating proper resource handling:");
        try (CustomResource resource = new CustomResource()) {
            resource.processData();
        }
        
        System.out.println("\nFinal Memory Stats:");
        printMemoryStats();
    }
    
    private static void printMemoryStats() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        System.out.println("Total Memory: " + formatSize(totalMemory));
        System.out.println("Free Memory: " + formatSize(freeMemory));
        System.out.println("Used Memory: " + formatSize(usedMemory));
    }
    
    private static String formatSize(long bytes) {
        return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
    }
    
    private static void simulateMemoryLeak() {
        Map<String, Object> leakyMap = new HashMap<>();
        int counter = 0;
        
        while (true) {
            // Create temporary objects that should be garbage collected
            byte[] tempData = new byte[1024 * 1024]; // 1MB
            leakyMap.put("key" + counter++, tempData);
            
            if (counter % 10 == 0) {
                System.out.println("Created " + counter + " objects");
                printMemoryStats();
            }
        }
    }
    
    private static void cleanup() {
        System.out.println("Performing cleanup...");
        strongReferences.clear();
        weakReferences.clear();
        softReferences.clear();
        System.gc();
    }
}

class CustomResource implements AutoCloseable {
    private byte[] data;
    
    public CustomResource() {
        data = new byte[1024 * 1024]; // 1MB
        System.out.println("Resource allocated");
    }
    
    public void processData() {
        System.out.println("Processing data...");
        // Simulate some data processing
        Arrays.fill(data, (byte) 1);
    }
    
    @Override
    public void close() {
        System.out.println("Resource cleanup in progress...");
        data = null;
    }
}
/**
 * Advanced Task Scheduler Implementation
 * 
 * This program demonstrates advanced concepts including:
 * - Thread Pool Management with Custom ThreadPoolExecutor
 * - Priority-based Task Scheduling using PriorityBlockingQueue
 * - Task Dependencies and Execution Chains
 * - Monitoring and Statistics Collection
 * - Graceful Shutdown with Resource Management
 * - Custom Task Types with different behaviors
 * - Thread-safe operations and concurrent programming
 * 
 * Features:
 * 1. Priority-based task execution
 * 2. Scheduled tasks with delays and intervals
 * 3. Task dependency management
 * 4. Real-time performance monitoring
 * 5. Dynamic thread pool scaling
 * 6. Task retry mechanism with exponential backoff
 * 
 * @author Contributing to Hacktoberfest 2025
 * @version 2.0
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with priority, dependencies, and execution context
 */
class AdvancedTask implements Comparable<AdvancedTask> {
    private static final AtomicLong taskIdGenerator = new AtomicLong(0);
    
    private final long taskId;
    private final String name;
    private final Runnable task;
    private final int priority; // Higher number = higher priority
    private final List<Long> dependencies;
    private final long scheduledTime;
    private final int maxRetries;
    private final long retryDelay;
    
    private volatile TaskStatus status;
    private volatile int attemptCount;
    private volatile String errorMessage;
    private volatile long executionTime;
    
    public enum TaskStatus {
        PENDING, RUNNING, COMPLETED, FAILED, CANCELLED, WAITING_FOR_DEPENDENCIES
    }
    
    public AdvancedTask(String name, Runnable task, int priority) {
        this(name, task, priority, new ArrayList<>(), 0, 3, 1000);
    }
    
    public AdvancedTask(String name, Runnable task, int priority, List<Long> dependencies, 
                       long delayMs, int maxRetries, long retryDelay) {
        this.taskId = taskIdGenerator.incrementAndGet();
        this.name = name;
        this.task = task;
        this.priority = priority;
        this.dependencies = new ArrayList<>(dependencies);
        this.scheduledTime = System.currentTimeMillis() + delayMs;
        this.maxRetries = maxRetries;
        this.retryDelay = retryDelay;
        this.status = TaskStatus.PENDING;
        this.attemptCount = 0;
    }
    
    @Override
    public int compareTo(AdvancedTask other) {
        // First compare by scheduled time (earlier tasks first)
        int timeComparison = Long.compare(this.scheduledTime, other.scheduledTime);
        if (timeComparison != 0) return timeComparison;
        
        // Then by priority (higher priority first)
        int priorityComparison = Integer.compare(other.priority, this.priority);
        if (priorityComparison != 0) return priorityComparison;
        
        // Finally by task ID (FIFO for same priority and time)
        return Long.compare(this.taskId, other.taskId);
    }
    
    // Getters and setters
    public long getTaskId() { return taskId; }
    public String getName() { return name; }
    public Runnable getTask() { return task; }
    public int getPriority() { return priority; }
    public List<Long> getDependencies() { return new ArrayList<>(dependencies); }
    public long getScheduledTime() { return scheduledTime; }
    public int getMaxRetries() { return maxRetries; }
    public long getRetryDelay() { return retryDelay; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public int getAttemptCount() { return attemptCount; }
    public void incrementAttemptCount() { this.attemptCount++; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public long getExecutionTime() { return executionTime; }
    public void setExecutionTime(long executionTime) { this.executionTime = executionTime; }
}

/**
 * Thread Pool Statistics for monitoring
 */
class ThreadPoolStats {
    private final AtomicLong tasksExecuted = new AtomicLong(0);
    private final AtomicLong tasksCompleted = new AtomicLong(0);
    private final AtomicLong tasksFailed = new AtomicLong(0);
    private final AtomicLong totalExecutionTime = new AtomicLong(0);
    private final Map<String, AtomicLong> taskTypeCount = new ConcurrentHashMap<>();
    
    public void recordTaskExecution(String taskName, long executionTime, boolean success) {
        tasksExecuted.incrementAndGet();
        if (success) {
            tasksCompleted.incrementAndGet();
        } else {
            tasksFailed.incrementAndGet();
        }
        totalExecutionTime.addAndGet(executionTime);
        taskTypeCount.computeIfAbsent(taskName, k -> new AtomicLong(0)).incrementAndGet();
    }
    
    public void printStats() {
        System.out.println("\n=== Thread Pool Statistics ===");
        System.out.println("Tasks Executed: " + tasksExecuted.get());
        System.out.println("Tasks Completed: " + tasksCompleted.get());
        System.out.println("Tasks Failed: " + tasksFailed.get());
        System.out.println("Average Execution Time: " + 
            (tasksExecuted.get() > 0 ? totalExecutionTime.get() / tasksExecuted.get() : 0) + "ms");
        System.out.println("Success Rate: " + 
            (tasksExecuted.get() > 0 ? (tasksCompleted.get() * 100.0 / tasksExecuted.get()) : 0) + "%");
        System.out.println("Task Type Distribution:");
        taskTypeCount.forEach((type, count) -> 
            System.out.println("  " + type + ": " + count.get()));
    }
}

/**
 * Main Task Scheduler Implementation
 */
public class AdvancedTaskScheduler {
    private final ThreadPoolExecutor executor;
    private final PriorityBlockingQueue<AdvancedTask> taskQueue;
    private final ScheduledExecutorService schedulerService;
    private final Map<Long, AdvancedTask> allTasks;
    private final Set<Long> completedTasks;
    private final ThreadPoolStats stats;
    private volatile boolean shutdown = false;
    
    public AdvancedTaskScheduler(int corePoolSize, int maximumPoolSize) {
        this.taskQueue = new PriorityBlockingQueue<>();
        this.executor = new ThreadPoolExecutor(
            corePoolSize, 
            maximumPoolSize, 
            60L, 
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new CustomThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy()
        );
        
        this.schedulerService = Executors.newScheduledThreadPool(2);
        this.allTasks = new ConcurrentHashMap<>();
        this.completedTasks = ConcurrentHashMap.newKeySet();
        this.stats = new ThreadPoolStats();
        
        // Start the main scheduling loop
        schedulerService.scheduleAtFixedRate(this::processTaskQueue, 0, 100, TimeUnit.MILLISECONDS);
        
        // Start statistics reporting
        schedulerService.scheduleAtFixedRate(stats::printStats, 5, 5, TimeUnit.SECONDS);
    }
    
    /**
     * Custom ThreadFactory for better thread naming and monitoring
     */
    private static class CustomThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        
        CustomThreadFactory() {
            namePrefix = "AdvancedScheduler-" + poolNumber.getAndIncrement() + "-thread-";
        }
        
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
            if (t.isDaemon()) t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY) t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
    
    /**
     * Submit a task for execution
     */
    public long submitTask(AdvancedTask task) {
        if (shutdown) {
            throw new IllegalStateException("Scheduler is shutdown");
        }
        
        allTasks.put(task.getTaskId(), task);
        taskQueue.offer(task);
        
        System.out.printf("[%s] Task '%s' (ID: %d, Priority: %d) submitted%n", 
            getCurrentTime(), task.getName(), task.getTaskId(), task.getPriority());
        
        return task.getTaskId();
    }
    
    /**
     * Process the task queue and execute ready tasks
     */
    private void processTaskQueue() {
        if (shutdown) return;
        
        List<AdvancedTask> readyTasks = new ArrayList<>();
        AdvancedTask task;
        
        // Collect all ready tasks
        while ((task = taskQueue.poll()) != null) {
            if (isTaskReady(task)) {
                readyTasks.add(task);
            } else {
                // Put back in queue if not ready
                taskQueue.offer(task);
                break; // Queue is ordered, so if this isn't ready, neither are the rest
            }
        }
        
        // Execute ready tasks
        for (AdvancedTask readyTask : readyTasks) {
            executeTask(readyTask);
        }
    }
    
    /**
     * Check if a task is ready for execution
     */
    private boolean isTaskReady(AdvancedTask task) {
        // Check if scheduled time has arrived
        if (System.currentTimeMillis() < task.getScheduledTime()) {
            return false;
        }
        
        // Check if dependencies are satisfied
        for (Long depId : task.getDependencies()) {
            if (!completedTasks.contains(depId)) {
                task.setStatus(AdvancedTask.TaskStatus.WAITING_FOR_DEPENDENCIES);
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Execute a task with error handling and retry logic
     */
    private void executeTask(AdvancedTask task) {
        task.setStatus(AdvancedTask.TaskStatus.RUNNING);
        task.incrementAttemptCount();
        
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            long startTime = System.currentTimeMillis();
            
            try {
                System.out.printf("[%s] Executing task '%s' (Attempt %d/%d)%n", 
                    getCurrentTime(), task.getName(), task.getAttemptCount(), task.getMaxRetries());
                
                task.getTask().run();
                
                long executionTime = System.currentTimeMillis() - startTime;
                task.setExecutionTime(executionTime);
                task.setStatus(AdvancedTask.TaskStatus.COMPLETED);
                completedTasks.add(task.getTaskId());
                
                stats.recordTaskExecution(task.getName(), executionTime, true);
                
                System.out.printf("[%s] Task '%s' completed in %dms%n", 
                    getCurrentTime(), task.getName(), executionTime);
                
            } catch (Exception e) {
                long executionTime = System.currentTimeMillis() - startTime;
                task.setExecutionTime(executionTime);
                task.setErrorMessage(e.getMessage());
                
                System.err.printf("[%s] Task '%s' failed: %s%n", 
                    getCurrentTime(), task.getName(), e.getMessage());
                
                // Retry logic with exponential backoff
                if (task.getAttemptCount() < task.getMaxRetries()) {
                    long delay = task.getRetryDelay() * (1L << (task.getAttemptCount() - 1)); // Exponential backoff
                    
                    System.out.printf("[%s] Retrying task '%s' in %dms%n", 
                        getCurrentTime(), task.getName(), delay);
                    
                    schedulerService.schedule(() -> {
                        task.setStatus(AdvancedTask.TaskStatus.PENDING);
                        taskQueue.offer(task);
                    }, delay, TimeUnit.MILLISECONDS);
                } else {
                    task.setStatus(AdvancedTask.TaskStatus.FAILED);
                    stats.recordTaskExecution(task.getName(), executionTime, false);
                    System.err.printf("[%s] Task '%s' failed permanently after %d attempts%n", 
                        getCurrentTime(), task.getName(), task.getAttemptCount());
                }
            }
        }, executor);
        
        // Handle uncaught exceptions
        future.exceptionally(throwable -> {
            System.err.printf("[%s] Uncaught exception in task '%s': %s%n", 
                getCurrentTime(), task.getName(), throwable.getMessage());
            task.setStatus(AdvancedTask.TaskStatus.FAILED);
            return null;
        });
    }
    
    /**
     * Get current formatted time
     */
    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
    }
    
    /**
     * Get task status by ID
     */
    public AdvancedTask.TaskStatus getTaskStatus(long taskId) {
        AdvancedTask task = allTasks.get(taskId);
        return task != null ? task.getStatus() : null;
    }
    
    /**
     * Get detailed task information
     */
    public AdvancedTask getTaskInfo(long taskId) {
        return allTasks.get(taskId);
    }
    
    /**
     * Graceful shutdown
     */
    public void shutdown() {
        shutdown = true;
        System.out.println("\n[" + getCurrentTime() + "] Initiating graceful shutdown...");
        
        schedulerService.shutdown();
        executor.shutdown();
        
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
            if (!schedulerService.awaitTermination(10, TimeUnit.SECONDS)) {
                schedulerService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            schedulerService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        stats.printStats();
        System.out.println("[" + getCurrentTime() + "] Shutdown completed");
    }
    
    /**
     * Main method demonstrating the Advanced Task Scheduler
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Advanced Task Scheduler Demo ===\n");
        
        AdvancedTaskScheduler scheduler = new AdvancedTaskScheduler(4, 8);
        
        // Demo tasks with different priorities and characteristics
        
        // High priority task
        long task1 = scheduler.submitTask(new AdvancedTask(
            "DataProcessing", 
            () -> {
                try {
                    Thread.sleep(2000); // Simulate work
                    System.out.println("  -> Processing large dataset...");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, 
            10
        ));
        
        // Medium priority task that depends on task1
        long task2 = scheduler.submitTask(new AdvancedTask(
            "ReportGeneration", 
            () -> {
                try {
                    Thread.sleep(1500);
                    System.out.println("  -> Generating comprehensive report...");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, 
            5, 
            Arrays.asList(task1), // Depends on task1
            0, 
            2, 
            500
        ));
        
        // Low priority scheduled task
        scheduler.submitTask(new AdvancedTask(
            "SystemCleanup", 
            () -> {
                System.out.println("  -> Performing system cleanup...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, 
            1, 
            new ArrayList<>(), 
            3000, // 3 second delay
            3, 
            1000
        ));
        
        // Task that will fail and retry
        scheduler.submitTask(new AdvancedTask(
            "NetworkOperation", 
            () -> {
                System.out.println("  -> Attempting network connection...");
                if (Math.random() < 0.7) { // 70% chance to fail
                    throw new RuntimeException("Network timeout");
                }
                System.out.println("  -> Network operation successful!");
            }, 
            7, 
            new ArrayList<>(), 
            1000, 
            4, 
            2000
        ));
        
        // CPU intensive task
        scheduler.submitTask(new AdvancedTask(
            "CpuIntensiveTask", 
            () -> {
                System.out.println("  -> Starting CPU intensive calculation...");
                long start = System.currentTimeMillis();
                // Simulate CPU work
                long result = 0;
                for (int i = 0; i < 10000000; i++) {
                    result += Math.sqrt(i);
                }
                long duration = System.currentTimeMillis() - start;
                System.out.printf("  -> CPU task completed in %dms (result: %.2f)%n", duration, result);
            }, 
            3
        ));
        
        // Multiple quick tasks
        for (int i = 1; i <= 5; i++) {
            final int taskNum = i;
            scheduler.submitTask(new AdvancedTask(
                "QuickTask" + taskNum, 
                () -> {
                    System.out.printf("  -> Quick task %d executed%n", taskNum);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, 
                2
            ));
        }
        
        System.out.println("\nAll tasks submitted. Processing...\n");
        
        // Let the scheduler run for a while
        Thread.sleep(15000);
        
        // Graceful shutdown
        scheduler.shutdown();
    }
}
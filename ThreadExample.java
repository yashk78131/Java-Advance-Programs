// filename: ThreadExample.java
// Compile: javac ThreadExample.java
// Run: java ThreadExample

class Task implements Runnable {
    private final String name;

    public Task(String name) {
        this.name = name;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + " - Count: " + i);
            try { Thread.sleep(500); } catch (InterruptedException e) { }
        }
    }
}

public class ThreadExample {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Task("Worker-1"));
        Thread t2 = new Thread(new Task("Worker-2"));
        t1.start();
        t2.start();
    }
}

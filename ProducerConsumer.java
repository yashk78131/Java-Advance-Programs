// filename: ProducerConsumer.java
// Compile: javac ProducerConsumer.java
// Run: java ProducerConsumer
import java.util.concurrent.*;
class Producer implements Runnable {
 private BlockingQueue<Integer> queue;
 public Producer(BlockingQueue<Integer> q) { this.queue = q; }
 public void run() {
 try {
 for (int i = 1; i <= 5; i++) {
 System.out.println("Produced: " + i);
 queue.put(i);
 Thread.sleep(500);
 }
 queue.put(-1); // poison pill
 } catch (InterruptedException e) { e.printStackTrace(); }
 }
}
class Consumer implements Runnable {
 private BlockingQueue<Integer> queue;
 public Consumer(BlockingQueue<Integer> q) { this.queue = q; }
 public void run() {
 try {
 while (true) {
 int val = queue.take();
 if (val == -1) break;
 System.out.println("Consumed: " + val);
 Thread.sleep(700);
 }
 } catch (InterruptedException e) { e.printStackTrace(); }
 }
}
public class ProducerConsumer {
 public static void main(String[] args) {
 BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
 new Thread(new Producer(queue)).start();
 new Thread(new Consumer(queue)).start();
 }
}

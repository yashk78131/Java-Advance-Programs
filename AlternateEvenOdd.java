/**
 * Program: AlternateEvenOdd.java
 *
 * Description:
 * This program demonstrates multithreading in Java by printing 
 * odd and even numbers alternately using two threads. 
 * It showcases thread synchronization using wait() and notify().
 *
 * Approach:
 * - Two threads (oddThread and evenThread) are created.
 * - Shared counter is maintained and threads take turns printing values.
 * - Synchronization ensures correct ordering using lock, wait(), and notify().
 *
 * Input:
 *  - Maximum number (N) up to which odd and even numbers will be printed.
 *  - In this demo, N = 10 (hardcoded).
 *
 * Output (Example for N=10):
 *  Odd: 1
 *  Even: 2
 *  Odd: 3
 *  Even: 4
 *  Odd: 5
 *  Even: 6
 *  Odd: 7
 *  Even: 8
 *  Odd: 9
 *  Even: 10
 *
 * Time Complexity: O(N)
 *   - Each number from 1 to N is printed once.
 * Space Complexity: O(1)
 *   - Only a few variables and a lock object are used.
 */

public class AlternateEvenOdd {
    private final int max;
    private int counter = 1;
    private final Object lock = new Object();

    public AlternateEvenOdd(int max) {
        this.max = max;
    }

    // Method to print odd numbers
    public void printOdd() {
        synchronized (lock) {
            while (counter <= max) {
                if (counter % 2 == 0) {
                    try {
                        lock.wait(); // wait until it's odd's turn
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (counter <= max) {
                    System.out.println("Odd: " + counter);
                    counter++;
                    lock.notify(); // notify even thread
                }
            }
        }
    }

    // Method to print even numbers
    public void printEven() {
        synchronized (lock) {
            while (counter <= max) {
                if (counter % 2 != 0) {
                    try {
                        lock.wait(); // wait until it's even's turn
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (counter <= max) {
                    System.out.println("Even: " + counter);
                    counter++;
                    lock.notify(); // notify odd thread
                }
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        AlternateEvenOdd printer = new AlternateEvenOdd(10);

        Thread t1 = new Thread(printer::printOdd);
        Thread t2 = new Thread(printer::printEven);

        t1.start();
        t2.start();
    }
}


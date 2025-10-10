// filename: PrimeRangeFinder.java
// Compile: javac PrimeRangeFinder.java
// Run: java PrimeRangeFinder

import java.util.*;

public class PrimeRangeFinder {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Prime Range Finder ===");
        System.out.print("Enter start of range: ");
        int start = sc.nextInt();
        System.out.print("Enter end of range: ");
        int end = sc.nextInt();

        System.out.println("\nPrime numbers between " + start + " and " + end + ":");
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println("\n=== End ===");

        sc.close();
    }

    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}

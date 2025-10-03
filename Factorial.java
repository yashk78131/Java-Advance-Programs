import java.util.Scanner;

public class Factorial {
    
    // Iterative method
    public static long factorialIterative(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Recursive method
    public static long factorialRecursive(int n) {
        if (n == 0 || n == 1) return 1;
        return n * factorialRecursive(n - 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int n = sc.nextInt();

        System.out.println("Factorial (Iterative): " + factorialIterative(n));
        System.out.println("Factorial (Recursive): " + factorialRecursive(n));
        
        sc.close();
    }
}

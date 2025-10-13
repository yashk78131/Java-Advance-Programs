import java.util.Scanner;

public class AdvancedStarPatterns {

    // Method 1: Pyramid Pattern
    public static void pyramid(int n) {
        System.out.println("\nPyramid Pattern:");
        for (int i = 1; i <= n; i++) {
            for (int j = i; j < n; j++)
                System.out.print(" ");
            for (int j = 1; j <= (2 * i - 1); j++)
                System.out.print("*");
            System.out.println();
        }
    }

    // Method 2: Diamond Pattern
    public static void diamond(int n) {
        System.out.println("\nDiamond Pattern:");
        // upper half
        for (int i = 1; i <= n; i++) {
            for (int j = i; j < n; j++)
                System.out.print(" ");
            for (int j = 1; j <= (2 * i - 1); j++)
                System.out.print("*");
            System.out.println();
        }
        // lower half
        for (int i = n - 1; i >= 1; i--) {
            for (int j = n; j > i; j--)
                System.out.print(" ");
            for (int j = 1; j <= (2 * i - 1); j++)
                System.out.print("*");
            System.out.println();
        }
    }

    // Method 3: Hollow Square
    public static void hollowSquare(int n) {
        System.out.println("\nHollow Square Pattern:");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == 1 || i == n || j == 1 || j == n)
                    System.out.print("* ");
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
    }

    // Method 4: Hourglass
    public static void hourglass(int n) {
        System.out.println("\nHourglass Pattern:");
        for (int i = n; i >= 1; i--) {
            for (int j = i; j < n; j++)
                System.out.print(" ");
            for (int j = 1; j <= (2 * i - 1); j++)
                System.out.print("*");
            System.out.println();
        }
        for (int i = 2; i <= n; i++) {
            for (int j = i; j < n; j++)
                System.out.print(" ");
            for (int j = 1; j <= (2 * i - 1); j++)
                System.out.print("*");
            System.out.println();
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of rows (e.g., 5): ");
        int n = sc.nextInt();

        pyramid(n);
        diamond(n);
        hollowSquare(n);
        hourglass(n);

        sc.close();
    }
}

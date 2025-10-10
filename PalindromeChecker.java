// filename: PalindromeChecker.java
// Compile: javac PalindromeChecker.java
// Run: java PalindromeChecker

import java.util.*;

public class PalindromeChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Palindrome Checker ===");
        System.out.print("Enter a string: ");
        String input = sc.nextLine();

        if (isPalindrome(input)) {
            System.out.println("✅ The string \"" + input + "\" is a palindrome!");
        } else {
            System.out.println("❌ The string \"" + input + "\" is not a palindrome.");
        }

        sc.close();
    }

    public static boolean isPalindrome(String str) {
        str = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int left = 0, right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right))
                return false;
            left++;
            right--;
        }
        return true;
    }
}

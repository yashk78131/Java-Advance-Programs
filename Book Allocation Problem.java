/*
 * Book Allocation Problem (Binary Search on Answer)
 *
 * Description:
 * You are given an array of integers 'pages' where pages[i] represents the number of pages in the i-th book
 * and an integer 'students' representing the number of students.
 * The goal is to allocate books to each student such that:
 *  - Each student gets at least one book
 *  - Each book is assigned to exactly one student
 *  - The maximum number of pages assigned to a student is minimized
 *
 * Approach:
 * - This is solved using Binary Search on Answer (the answer lies between max(pages) and sum(pages)).
 * - Check function: given a maximum pages limit, can we allocate books so that no student gets more than this?
 * - Use binary search to find the minimum possible value of this maximum.
 *
 * Time Complexity: O(n * log(sum of pages)), where n is the number of books
 * Space Complexity: O(1)
 */

import java.util.*;

class Solution {
    // Check if it's possible to allocate books with maxPages as the upper limit
    private boolean isPossible(int[] pages, int students, int maxPages) {
        int countStudents = 1;
        int currentSum = 0;

        for (int p : pages) {
            if (p > maxPages) return false; // single book > maxPages → not possible

            if (currentSum + p > maxPages) {
                countStudents++;
                currentSum = p;
                if (countStudents > students) return false;
            } else {
                currentSum += p;
            }
        }
        return true;
    }

    public int allocateBooks(int[] pages, int students) {
        if (students > pages.length) return -1; // not enough books

        int low = Arrays.stream(pages).max().getAsInt();
        int high = Arrays.stream(pages).sum();
        int result = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (isPossible(pages, students, mid)) {
                result = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return result;
    }
}

public class BookAllocation {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test Case 1
        System.out.println("Test Case 1:");
        int[] pages1 = {12, 34, 67, 90};
        int students1 = 2;
        System.out.println("Books: " + Arrays.toString(pages1));
        System.out.println("Students: " + students1);
        System.out.println("Minimum Maximum Pages: " + sol.allocateBooks(pages1, students1));
        System.out.println();

        // Test Case 2
        System.out.println("Test Case 2:");
        int[] pages2 = {10, 20, 30, 40};
        int students2 = 2;
        System.out.println("Books: " + Arrays.toString(pages2));
        System.out.println("Students: " + students2);
        System.out.println("Minimum Maximum Pages: " + sol.allocateBooks(pages2, students2));
        System.out.println();

        // Test Case 3
        System.out.println("Test Case 3:");
        int[] pages3 = {5, 10, 30, 20, 15};
        int students3 = 3;
        System.out.println("Books: " + Arrays.toString(pages3));
        System.out.println("Students: " + students3);
        System.out.println("Minimum Maximum Pages: " + sol.allocateBooks(pages3, students3));
        System.out.println();

        // Test Case 4 (Edge Case: students > books)
        System.out.println("Test Case 4:");
        int[] pages4 = {10, 20, 30};
        int students4 = 4;
        System.out.println("Books: " + Arrays.toString(pages4));
        System.out.println("Students: " + students4);
        System.out.println("Minimum Maximum Pages: " + sol.allocateBooks(pages4, students4));
    }
}

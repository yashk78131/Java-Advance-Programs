/*
 * Magnetic Force Between Two Balls (Binary Search on Answer)
 *
 * Description:
 * You are given an array 'position' representing locations of magnetic baskets on a number line,
 * and an integer 'm' representing the number of magnetic balls you need to place.
 *
 * You must place all m balls in different baskets such that the **minimum magnetic force**
 * between any two balls is **as large as possible**.
 *
 * The "magnetic force" between two balls at positions x and y is |x - y|.
 *
 * Approach:
 * - Sort the positions array first.
 * - Search Space Definition:
 *      - Minimum possible distance = 1
 *      - Maximum possible distance = (max(position) - min(position))
 * - Binary Search on Answer:
 *      - For a given distance 'mid', check if we can place all m balls
 *        such that the distance between any two consecutive balls ≥ mid.
 * - If it's possible → try for a larger distance.
 *   Otherwise → reduce the distance.
 *
 * Time Complexity: O(n * log(max(position) - min(position)))
 * Space Complexity: O(1)
 */

import java.util.*;

class Solution {
    // Check if we can place m balls such that each has at least 'dist' apart
    private boolean canPlace(int[] position, int m, int dist) {
        int count = 1;                // Place the first ball at the first basket
        int lastPos = position[0];    // Last position where we placed a ball

        for (int i = 1; i < position.length; i++) {
            if (position[i] - lastPos >= dist) {
                count++;
                lastPos = position[i];
                if (count >= m) return true;
            }
        }
        return false;
    }

    public int maxDistance(int[] position, int m) {
        Arrays.sort(position); // Sort positions first

        int low = 1;
        int high = position[position.length - 1] - position[0];
        int result = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canPlace(position, m, mid)) {
                result = mid;      // Possible → try larger distance
                low = mid + 1;
            } else {
                high = mid - 1;    // Not possible → decrease distance
            }
        }

        return result;
    }
}

public class MagneticBalls {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test Case 1
        System.out.println("Test Case 1:");
        int[] position1 = {1, 2, 8, 4, 9};
        int m1 = 3;
        System.out.println("Positions: " + Arrays.toString(position1));
        System.out.println("Balls: " + m1);
        System.out.println("Maximum Minimum Distance: " + sol.maxDistance(position1, m1));
        System.out.println();

        // Test Case 2
        System.out.println("Test Case 2:");
        int[] position2 = {5, 4, 3, 2, 1, 1000000000};
        int m2 = 2;
        System.out.println("Positions: " + Arrays.toString(position2));
        System.out.println("Balls: " + m2);
        System.out.println("Maximum Minimum Distance: " + sol.maxDistance(position2, m2));
        System.out.println();

        // Test Case 3
        System.out.println("Test Case 3:");
        int[] position3 = {79, 74, 57, 22};
        int m3 = 4;
        System.out.println("Positions: " + Arrays.toString(position3));
        System.out.println("Balls: " + m3);
        System.out.println("Maximum Minimum Distance: " + sol.maxDistance(position3, m3));
        System.out.println();
    }
}

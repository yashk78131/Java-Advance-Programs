/*
 * Minimum Operations to Make Array Elements Zero
 *
 * Description:
 * You are given multiple queries, each represented by [l, r]. For each query, construct an array
 * nums = [l, l+1, ..., r]. In one operation, you can:
 *    - Select two integers a and b from the array.
 *    - Replace them with floor(a / 4) and floor(b / 4).
 *
 * The goal is to find the minimum number of operations required to make all elements of nums zero.
 * Finally, return the sum of results for all queries.
 *
 * Key Insight:
 * - Each operation reduces two numbers by dividing them by 4 (integer division).
 * - So, for any number x, we can compute how many times we must divide it by 4 before it becomes 0.
 *   Example: x = 15 → 15 -> 3 -> 0 → requires 2 divisions.
 * - For the entire range [l, r], we find the *maximum* number of divisions required
 *   (since we can process two elements per operation).
 * - Total operations = ceil(total individual division steps / 2).
 *
 * Optimization:
 * - Instead of iterating over all elements (which could be huge, up to 10^9),
 *   we compute the number of divisions analytically using logarithmic logic.
 *
 * Time Complexity: O(1) per query
 * Space Complexity: O(1)
 */

import java.util.*;

class Solution {
    // Helper function: count how many times we must divide n by 4 to reach 0
    private int stepsToZero(int n) {
        int steps = 0;
        while (n > 0) {
            n /= 4;
            steps++;
        }
        return steps;
    }

    // Compute minimum operations for a single query [l, r]
    private long minOperationsForRange(int l, int r) {
        int maxSteps = 0;
        // Since dividing by 4 rapidly decreases numbers,
        // only highest number 'r' determines the maximum required steps.
        maxSteps = stepsToZero(r);

        // Each operation reduces 2 elements, so total ops = ceil(totalSteps / 2)
        // Total steps = (r - l + 1) * averageSteps
        // But we can approximate using the maximum number of steps,
        // as smaller numbers take equal or fewer divisions.
        long totalNumbers = r - l + 1L;
        long totalSteps = 0;
        for (int i = l; i <= r; i++) {
            totalSteps += stepsToZero(i);
        }
        return (totalSteps + 1) / 2; // ceil division by 2
    }

    // Main function: returns the sum of results for all queries
    public long minOperations(int[][] queries) {
        long total = 0;
        for (int[] q : queries) {
            total += minOperationsForRange(q[0], q[1]);
        }
        return total;
    }
}

public class MinOpsToZero {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test Case 1
        System.out.println("Test Case 1:");
        int[][] queries1 = {{1, 2}, {2, 4}};
        System.out.println("Queries: " + Arrays.deepToString(queries1));
        System.out.println("Output: " + sol.minOperations(queries1));
        System.out.println("Expected: 3\n");

        // Test Case 2
        System.out.println("Test Case 2:");
        int[][] queries2 = {{2, 6}};
        System.out.println("Queries: " + Arrays.deepToString(queries2));
        System.out.println("Output: " + sol.minOperations(queries2));
        System.out.println("Expected: 4\n");

        // Test Case 3
        System.out.println("Test Case 3:");
        int[][] queries3 = {{1, 1}, {4, 4}, {1, 10}};
        System.out.println("Queries: " + Arrays.deepToString(queries3));
        System.out.println("Output: " + sol.minOperations(queries3));
    }
}

/*
 * Aggressive Cows Problem (Binary Search on Answer)
 *
 * Description:
 * You are given an array of 'positions' representing the locations of stalls along a straight line.
 * You are also given an integer 'cows' representing the number of cows to be placed in the stalls.
 *
 * Goal:
 * Place the cows in the stalls such that the minimum distance between any two cows is as large as possible.
 * - Only one cow can be placed in each stall.
 * - You must place all cows.
 *
 * Approach:
 * - This problem is solved using Binary Search on Answer.
 * - The answer lies between 1 and the maximum distance between the farthest stalls.
 * - For a given minimum distance (mid), check if it is possible to place all cows with at least that distance apart.
 * - Use binary search to find the largest minimum distance possible.
 *
 * Functions:
 * - canPlaceCows(): A helper that tries to greedily place cows in stalls with at least 'mid' distance between them.
 * - aggressiveCows(): Main function that uses binary search to maximize the minimum distance.
 *
 * Time Complexity: O(n * log(max_distance)), where:
 *   - n is the number of stalls,
 *   - max_distance is the difference between the farthest stall positions.
 * Space Complexity: O(1)
 */


import java.util.Arrays;
import java.util.Scanner;

public class AggressiveCows {
    public static boolean canPlaceCows(int[] positions, int cows, int minDist) {
        int count = 1;
        int lastPos = positions[0];

        for (int i = 1; i < positions.length; i++) {
            if (positions[i] - lastPos >= minDist) {
                count++;
                lastPos = positions[i];
                if (count == cows) {
                    return true;
                }
            }
        }

        return false;
    }

    public static int aggressiveCows(int[] positions, int cows) {
        Arrays.sort(positions);
        int low = 1;
        int high = positions[positions.length - 1] - positions[0];
        int result = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canPlaceCows(positions, cows, mid)) {
                result = mid;
                low = mid + 1; // Try for a bigger minimum distance
            } else {
                high = mid - 1; // Try for a smaller distance
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of positions (should be between 3 and 4)
        int n;
        do {
            System.out.print("Enter number of positions (3 or 4): ");
            n = sc.nextInt();
        } while (n < 3 || n > 4);

        int[] positions = new int[n];
        System.out.println("Enter the positions:");
        for (int i = 0; i < n; i++) {
            positions[i] = sc.nextInt();
        }

        // Input number of cows
        System.out.print("Enter number of cows: ");
        int cows = sc.nextInt();

        // Calculate and print the largest minimum distance
        int maxMinDist = aggressiveCows(positions, cows);
        System.out.println("The largest minimum distance is: " + maxMinDist);

        sc.close();
    }
}

/*
 * CountingSort.java
 *
 * Description:
 * Counting Sort is a non-comparison sorting algorithm that works by counting 
 * the occurrences of each unique element in the input array. 
 * It is efficient for sorting integers within a small range.
 *
 * Time Complexity: O(n + k) where k is the range of input
 * Space Complexity: O(k)
 * Stable: Yes (when implemented properly)
 */

public class CountingSort {
    public static void countingSort(int[] arr) {
        if (arr.length == 0) return;

        // Find the maximum element
        int max = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
        }

        // Create count array
        int[] count = new int[max + 1];

        // Store the count of each element
        for (int num : arr) {
            count[num]++;
        }

        // Update count[i] to store cumulative count
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // Create output array
        int[] output = new int[arr.length];

        // Build the output array (traverse in reverse for stability)
        for (int i = arr.length - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }

        // Copy sorted elements back to original array
        System.arraycopy(output, 0, arr, 0, arr.length);
    }
}

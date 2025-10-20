/*
 * BubbleSort.java
 * 
 * Description:
 * Bubble Sort repeatedly steps through the list, compares adjacent elements,
 * and swaps them if they are in the wrong order. This process is repeated
 * until the list is sorted.
 * 
 * Time Complexity: O(n^2)
 * Space Complexity: O(1)
 * Stable: Yes
 */

public class BubbleSort {
    // Function to perform Bubble Sort
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        // Outer loop runs n-1 times because the last element will already be in place
        for (int i = 0; i < n - 1; i++) {
            // Inner loop compares adjacent elements
            for (int j = 0; j < n - i - 1; j++) {
                // If the current element is greater than the next, swap them
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
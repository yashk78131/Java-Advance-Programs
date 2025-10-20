/*
 * InsertionSort.java
 * 
 * Description:
 * Insertion Sort builds the sorted array one item at a time by comparing
 * each new element to those already sorted and inserting it in the correct position.
 * 
 * Time Complexity: O(n^2)
 * Space Complexity: O(1)
 * Stable: Yes
 */

public class InsertionSort {
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        // Start from the second element as the first is already "sorted"
        for (int i = 1; i < n; i++) {
            int key = arr[i]; // Element to be inserted
            int j = i - 1;

            // Move elements of arr[0..i-1] that are greater than key to one position ahead
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key; // Insert the key at the correct position
        }
    }
}
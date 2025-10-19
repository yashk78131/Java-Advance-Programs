/*
 * QuickSort.java
 * 
 * Description:
 * Quick Sort is a Divide and Conquer algorithm. It picks an element as a pivot
 * and partitions the array such that elements smaller than the pivot are on the left,
 * and greater are on the right. It then recursively sorts the subarrays.
 * 
 * Time Complexity: O(n log n) on average
 * Space Complexity: O(log n)
 * Stable: No
 */

public class QuickSort {
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Partition the array and get the pivot index
            int pi = partition(arr, low, high);

            // Recursively sort elements before and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // Pivot element (chosen as the last element)
        int i = (low - 1);

        // Rearrange elements based on the pivot
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                // Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // Place the pivot in its correct position
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1; // Return pivot index
    }
}
/*
 * RadixSort.java
 * 
 * Description:
 * Radix Sort sorts numbers digit by digit starting from the least significant digit (LSD)
 * to the most significant digit (MSD). It uses Counting Sort as a subroutine.
 * 
 * Time Complexity: O(d * (n + k)) where d = number of digits, k = 10 (for base 10)
 * Space Complexity: O(n + k)
 * Stable: Yes
 */

import java.util.Arrays;

public class RadixSort {
    public static void radixSort(int[] arr) {
        int max = getMax(arr);

        // Perform counting sort for each digit
        for (int exp = 1; max / exp > 0; exp *= 10)
            countingSort(arr, exp);
    }

    // Function to get maximum value in arr[]
    static int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++)
            if (arr[i] > max)
                max = arr[i];
        return max;
    }

    // A function to do counting sort based on the digit represented by exp
    static void countingSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n]; // output array
        int[] count = new int[10]; // digits 0–9

        // Count occurrences of each digit
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }

        // Change count[i] so that it contains the actual position
        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // Build the output array (stable sort)
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        // Copy the output array to arr[]
        for (int i = 0; i < n; i++)
            arr[i] = output[i];
    }

    // Optional main method for testing
    public static void main(String[] args) {
        int[] arr = {170, 45, 75, 90, 802, 24, 2, 66};
        System.out.println("Original array:");
        System.out.println(Arrays.toString(arr));

        radixSort(arr);

        System.out.println("Sorted array:");
        System.out.println(Arrays.toString(arr));
    }
}

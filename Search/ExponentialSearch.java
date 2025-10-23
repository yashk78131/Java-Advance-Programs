import java.util.Scanner;

/**
 * Implements the Exponential Search algorithm (iterative).
 *
 * Works efficiently on sorted arrays by finding a range where the element might
 * exist using exponential steps, and then performing a binary search within that range.
 *
 * Time Complexity:
 *  - O(log i) where i is the index of the element to be searched.
 *  - Worst case: O(log n)
 * Space Complexity: O(1)
 */
public class ExponentialSearch {

    /**
     * Performs binary search on a subarray.
     *
     * @param a     The sorted array
     * @param left  Left boundary index
     * @param right Right boundary index
     * @param key   The element to search
     * @return The index of the key if found, otherwise -1
     */
    public static int binarySearch(int[] a, int left, int right, int key) {
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (a[mid] == key)
                return mid;
            else if (a[mid] < key)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1; // Not found
    }

    /**
     * Performs an exponential search on a sorted integer array.
     *
     * @param a   The sorted array (ascending order)
     * @param n   The size of the array
     * @param key The element to search
     * @return The index of the key if found, otherwise -1
     */
    public static int exponentialSearch(int[] a, int n, int key) {
        // Handle empty array case
        if (n == 0)
            return -1;

        // Check if the first element is the key
        if (a[0] == key)
            return 0;

        // Find range for binary search by repeated doubling
        int i = 1;
        while (i < n && a[i] <= key)
            i = i * 2;

        // Call binary search for the found range
        return binarySearch(a, i / 2, Math.min(i, n - 1), key);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the size of the array:");
        int n = sc.nextInt();

        int[] a = new int[n];
        System.out.println("Enter sorted values into the array:");
        for (int i = 0; i < n; ++i) {
            System.out.printf("Enter element at index %d: ", i);
            a[i] = sc.nextInt();
        }

        System.out.print("Enter an element to search: ");
        int key = sc.nextInt();

        int position = exponentialSearch(a, n, key);

        if (position == -1) {
            System.out.println("Key not found :(");
        } else {
            System.out.println("Key found at " + (position + 1) + " position");
        }

        sc.close();
    }
}

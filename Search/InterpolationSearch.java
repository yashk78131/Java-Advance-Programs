import java.util.Scanner;

/**
 * Implements the Interpolation Search algorithm (iterative)
 * Note: The input array must be sorted in non-decreasing order for this to
 * work.
 *
 * Best on uniformly distributed data. In the average case it can outperform
 * binary search.
 *
 * Time Complexity (average): O(log log n) on uniformly distributed arrays
 * Time Complexity (worst): O(n) when distribution is highly skewed or all
 * values equal
 * Space Complexity: O(1)
 */
public class InterpolationSearch {

    /**
     * Performs an interpolation search on a sorted integer array.
     *
     * @param a   The sorted array to search (ascending order)
     * @param n   The size of the array
     * @param key The element to search
     * @return The index of the key if found, otherwise -1
     */
    public static int interpolationSearch(int[] a, int n, int key) {
        int low = 0;
        int high = n - 1;

        // Guard against empty arrays
        if (n == 0)
            return -1;

        while (low <= high && key >= a[low] && key <= a[high]) {
            // If all elements between low and high are the same, avoid division by zero
            if (a[high] == a[low]) {
                return (a[low] == key) ? low : -1;
            }

            // Estimate position using the interpolation formula
            // Use long to avoid integer overflow during multiplication
            long numerator = (long) (key - a[low]) * (high - low);
            int pos = low + (int) (numerator / (a[high] - a[low]));

            // Safety guard in case of rounding anomalies
            if (pos < low || pos > high) {
                break;
            }

            if (a[pos] == key) {
                return pos; // Found
            } else if (a[pos] < key) {
                low = pos + 1; // Search upper part
            } else {
                high = pos - 1; // Search lower part
            }
        }

        return -1; // Not found
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

        int position = interpolationSearch(a, a.length, key);

        if (position == -1) {
            System.out.println("Key not found :(");
        } else {
            System.out.println("Key found at " + (position + 1) + " position");
        }

        // Close the scanner (optional; avoids resource leak warnings)
        sc.close();
    }
}

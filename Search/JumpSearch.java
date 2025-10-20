import java.util.Scanner;

/**
 * Implements the Jump Search algorithm for sorted arrays.
 * Note: The input array must be sorted in non-decreasing order.
 *
 * Time Complexity: O(\sqrt{n}) in the average/worst case.
 * Space Complexity: O(1).
 */

class JumpSearch {

    /**
     * Performs jump search on a sorted integer array.
     *
     * @param arr The sorted array to search in
     * @param key The target value to search for
     * @return The index of the key if found, otherwise -1
     */
    public static int jumpSearch(int[] arr, int key) {
        int n = arr.length;
        if (n == 0)
            return -1;

        int step = (int) Math.floor(Math.sqrt(n));
        int prev = 0;

        // Jump ahead to find the block where the element may be present
        while (prev < n && arr[Math.min(step, n) - 1] < key) {
            prev = step;
            step += (int) Math.floor(Math.sqrt(n));
            if (prev >= n) {
                return -1; // Not found
            }
        }

        // Linear search within the identified block
        while (prev < Math.min(step, n) && arr[prev] < key) {
            prev++;
        }

        // Check if the element is found
        if (prev < n && arr[prev] == key) {
            return prev;
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

        int position = jumpSearch(a, key);

        if (position == -1) {
            System.out.println("Key not found :(");
        } else {
            System.out.println("Key found at " + (position + 1) + " position");
        }

        sc.close();
    }
}

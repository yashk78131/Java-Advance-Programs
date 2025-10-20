import java.util.Scanner;

/**
 * Implements the Ternary Search algorithm (iterative)
 * Note: The input array must be sorted for this to work
 */
public class TernarySearch {

    /**
     * Searches for target in sorted array arr using ternary search
     * Time Complexity: O(log_3 n)
     * Space Complexity: O(1)
     * 
     * @param arr    sorted array
     * @param target value to find
     * @return index of target if found else -1
     */
    public static int ternarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int third = (right - left) / 3;
            int mid1 = left + third;
            int mid2 = right - third;

            if (arr[mid1] == target)
                return mid1;
            if (arr[mid2] == target)
                return mid2;

            if (target < arr[mid1]) {
                right = mid1 - 1; // search in the left part
            } else if (target > arr[mid2]) {
                left = mid2 + 1; // search in the right part
            } else {
                left = mid1 + 1; // search in the middle part
                right = mid2 - 1;
            }
        }

        return -1; // not found
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the size of the array:");
        int n = sc.nextInt();

        int[] arr = new int[n];
        System.out.println("Enter sorted values into the array:");
        for (int i = 0; i < n; ++i) {
            System.out.printf("Enter element at index %d: ", i);
            arr[i] = sc.nextInt();
        }

        System.out.print("Enter an element to search: ");
        int target = sc.nextInt();

        int index = ternarySearch(arr, target);

        if (index != -1)
            System.out.println("Element found at index: " + index);
        else
            System.out.println("Element not found.");
    }
}

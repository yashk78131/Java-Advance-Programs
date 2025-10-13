import java.util.Arrays;
import java.util.Scanner;

public class BinarySearch {

    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target)
                return mid;
            if (arr[mid] < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();

        int[] arr = new int[n];
        System.out.println("Enter " + n + " integers (sorted order):");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        System.out.print("Enter target number to search: ");
        int target = sc.nextInt();

        int index = binarySearch(arr, target);

        System.out.println("\nArray: " + Arrays.toString(arr));
        if (index != -1)
            System.out.println("✅ Element found at index " + index);
        else
            System.out.println("❌ Element not found in array.");

        sc.close();
    }
}

import java.util.*;

class Stats {
    int comparisons = 0;
    int moves = 0;
}

class Solution {
    private static void merge(int[] arr, int low, int mid, int high, Stats stats) {
        ArrayList<Integer> temp = new ArrayList<>();
        int left = low, right = mid + 1;

        while (left <= mid && right <= high) {
            stats.comparisons++;
            if (arr[left] <= arr[right]) {
                temp.add(arr[left++]);
                stats.moves++;
            } else {
                temp.add(arr[right++]);
                stats.moves++;
            }
        }

        while (left <= mid) {
            temp.add(arr[left++]);
            stats.moves++;
        }
        while (right <= high) {
            temp.add(arr[right++]);
            stats.moves++;
        }

        for (int i = low; i <= high; i++) {
            arr[i] = temp.get(i - low);
            stats.moves++;
        }
    }

    public static void mergeSort(int[] arr, int low, int high, Stats stats) {
        if (low >= high) return;
        int mid = low + (high - low) / 2;
        mergeSort(arr, low, mid, stats);
        mergeSort(arr, mid + 1, high, stats);
        merge(arr, low, mid, high, stats);
    }
}

public class MergeSort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = 0;

        while (true) {
            System.out.print("Enter number of elements (positive integer): ");
            try {
                n = sc.nextInt();
                if (n > 0) break;
                else System.out.println("Size must be positive!");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter an integer.");
                sc.next();
            }
        }

        int[] arr = new int[n];
        System.out.println("Enter " + n + " integer elements:");
        for (int i = 0; i < n;) {
            try {
                arr[i] = sc.nextInt();
                i++;
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer.");
                sc.next();
            }
        }

        System.out.println("Before sorting:");
        for (int value : arr) System.out.print(value + " ");
        System.out.println();

        Stats stats = new Stats();
        Solution.mergeSort(arr, 0, n - 1, stats);

        System.out.println("After sorting:");
        for (int value : arr) System.out.print(value + " ");
        System.out.println();

        System.out.println("Number of comparisons: " + stats.comparisons);
        System.out.println("Number of moves: " + stats.moves);

        sc.close();
    }
}

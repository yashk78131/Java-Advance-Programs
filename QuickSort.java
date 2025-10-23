import java.util.*;

// Custom exception for invalid array size
class InvalidArraySizeException extends Exception {
    public InvalidArraySizeException(String message) {
        super(message);
    }
}

class Stats {
    int comparisons = 0;
    int swaps = 0;
}

class QuickSort {

    static int partition(List<Integer> arr, int low, int high, Stats stats) {
        int pivot = arr.get(low);  // Pivot = first element
        int i = low, j = high;

        while (i < j) {
            while (i <= high - 1 && arr.get(i) <= pivot) {
                stats.comparisons++;
                i++;
            }
            stats.comparisons++; // last checked comparison when breaking loop

            while (j >= low + 1 && arr.get(j) > pivot) {
                stats.comparisons++;
                j--;
            }
            stats.comparisons++; 

            if (i < j) {
                // Swap arr[i] and arr[j]
                int temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
                stats.swaps++;
            }
        }

        // Swap pivot with arr[j]
        int temp = arr.get(low);
        arr.set(low, arr.get(j));
        arr.set(j, temp);
        stats.swaps++;

        return j; // Return pivot index
    }

    static void qs(List<Integer> arr, int low, int high, Stats stats) {
        if (low < high) {
            int pIndex = partition(arr, low, high, stats);
            System.out.println("Pivot placed at index " + pIndex + ": " + arr);
            qs(arr, low, pIndex - 1, stats);
            qs(arr, pIndex + 1, high, stats);
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        int n = 0;
        while (true) {
            System.out.print("Enter number of elements: ");
            try {
                n = sc.nextInt();
                if (n <= 0) {
                    throw new InvalidArraySizeException("Invalid array size. Size must be positive.");
                }
                break;
            } catch (InvalidArraySizeException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter an integer.");
                sc.next();
            }
        }

        List<Integer> arr = new ArrayList<>();
        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n;) {
            try {
                arr.add(sc.nextInt());
                i++;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter an integer.");
                sc.next();
            }
        }

        System.out.println("Original Array: " + arr);

        Stats stats = new Stats();
        qs(arr, 0, n - 1, stats);

        System.out.println("Sorted Array: " + arr);
        System.out.println("Total comparisons: " + stats.comparisons);
        System.out.println("Total swaps: " + stats.swaps);

        sc.close();
    }
}
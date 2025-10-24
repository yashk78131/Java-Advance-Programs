public class PerformanceBenchmark {

    public static void main(String[] args) {
        long start = System.nanoTime();
        bubbleSort();
        long end = System.nanoTime();
        System.out.println("Bubble Sort Time: " + (end - start) / 1e6 + " ms");

        start = System.nanoTime();
        quickSort(new int[]{5, 3, 2, 8, 1}, 0, 4);
        end = System.nanoTime();
        System.out.println("Quick Sort Time: " + (end - start) / 1e6 + " ms");
    }

    static void bubbleSort() {
        int[] arr = {5, 1, 4, 2, 8};
        for (int i = 0; i < arr.length - 1; i++)
            for (int j = 0; j < arr.length - i - 1; j++)
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
    }

    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}


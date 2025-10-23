public class SentinelSearch {

    // Sentinel Search algorithm
    public static int sentinelSearch(int[] arr, int x) {
        int last = arr[arr.length - 1];
        arr[arr.length - 1] = x;

        int i = 0;
        while (arr[i] != x) {
            i++;
        }

        arr[arr.length - 1] = last;

        if (i < arr.length - 1 || arr[arr.length - 1] == x)
            return i;

        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 40, 50};
        int x = 40;
        int result = sentinelSearch(arr, x);
        System.out.println(result == -1 ? "Element not found" : "Found at index " + result);
    }
}

public class FibonacciSearch {

    // Function to perform Fibonacci Search on sorted array
    public static int fibonacciSearch(int[] arr, int x) {
        int n = arr.length;

        // Initialize fibonacci numbers
        int fibMMm2 = 0;  // (m-2)'th Fibonacci
        int fibMMm1 = 1;  // (m-1)'th Fibonacci
        int fibM = fibMMm2 + fibMMm1;  // m'th Fibonacci

        // fibM stores the smallest Fibonacci number greater than or equal to n
        while (fibM < n) {
            fibMMm2 = fibMMm1;
            fibMMm1 = fibM;
            fibM = fibMMm2 + fibMMm1;
        }

        int offset = -1;

        while (fibM > 1) {
            int i = Math.min(offset + fibMMm2, n - 1);

            if (arr[i] < x) {
                fibM = fibMMm1;
                fibMMm1 = fibMMm2;
                fibMMm2 = fibM - fibMMm1;
                offset = i;
            } else if (arr[i] > x) {
                fibM = fibMMm2;
                fibMMm1 = fibMMm1 - fibMMm2;
                fibMMm2 = fibM - fibMMm1;
            } else {
                return i;
            }
        }

        if (fibMMm1 == 1 && offset + 1 < n && arr[offset + 1] == x)
            return offset + 1;

        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {10, 22, 35, 40, 45, 50, 80, 82, 85};
        int x = 85;
        int result = fibonacciSearch(arr, x);
        System.out.println(result == -1 ? "Element not found" : "Found at index " + result);
    }
}

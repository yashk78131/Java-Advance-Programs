import java.util.*;

class AggressiveCows {
    public int aggressiveCows(int[] arr, int k) {
        int n = arr.length;
        Arrays.sort(arr);
        int low = 0;
        int high = arr[n - 1] - arr[0];
        int ans = low;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int count = 1;
            int prev = arr[0];

            for (int i = 1; i < n; i++) {
                if (arr[i] - prev >= mid) {
                    count++;
                    prev = arr[i];
                }
            }

            if (count >= k) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return ans;
    }

    // Example usage
    public static void main(String[] args) {
        AggressiveCows sol = new AggressiveCows();
        int[] arr = {1, 2, 8, 4, 9};
        int k = 3;
        System.out.println(sol.aggressiveCows(arr, k)); // Expected output: 3
    }
}

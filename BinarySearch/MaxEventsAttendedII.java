import java.util.*;

class MaxEventsAttendedII {
    public int maxValue(int[][] events, int k) {
        Arrays.sort(events, Comparator.comparingInt(e -> e[0])); 
        int n = events.length;
        int[][] dp = new int[n + 1][k + 1]; 

        int[] starts = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = events[i][0];
        }

        for (int i = n - 1; i >= 0; i--) {
            int next = findNext(events, i, starts); 
            for (int j = 1; j <= k; j++) {
                int skip = dp[i + 1][j];
                int take = events[i][2] + dp[next][j - 1];
                dp[i][j] = Math.max(skip, take);
            }
        }

        return dp[0][k];
    }

    private int findNext(int[][] events, int i, int[] starts) {
        int lo = i + 1, hi = events.length;
        int target = events[i][1] + 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (starts[mid] >= target) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }
}

public class Koko_eating_bananas {

    // Function to check if Koko can finish with speed 'k'
    private static boolean canFinish(int[] piles, int k, int H) {
        int hours = 0;
        for (int pile : piles) {
            hours += (pile + k - 1) / k; // ceil division
        }
        return hours <= H;
    }

    // Function to find minimum eating speed
    public static int minEatingSpeed(int[] piles, int H) {
        int left = 1; // minimum speed
        int right = 0; // maximum pile size
        for (int pile : piles) right = Math.max(right, pile);

        int result = right;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canFinish(piles, mid, H)) {
                result = mid;      // possible solution, try smaller
                right = mid - 1;
            } else {
                left = mid + 1;    // too slow, increase speed
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] piles = {3, 6, 7, 11};
        int H = 8;
        int minSpeed = minEatingSpeed(piles, H);
        System.out.println("Minimum eating speed: " + minSpeed);
    }
}

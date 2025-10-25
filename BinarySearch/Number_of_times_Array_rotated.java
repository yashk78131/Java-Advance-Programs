public class Number_of_times_Array_rotated {

    // Function to find the index of minimum element
    public static int findKRotation(int[] arr) {
        int low = 0, high = arr.length - 1;

        while (low <= high) {
            // If the subarray is already sorted
            if (arr[low] <= arr[high]) {
                return low; // minimum element index
            }

            int mid = low + (high - low) / 2;
            int next = (mid + 1) % arr.length;
            int prev = (mid - 1 + arr.length) % arr.length;

            // Check if mid is minimum
            if (arr[mid] <= arr[next] && arr[mid] <= arr[prev]) {
                return mid;
            }

            // Decide which half to choose
            if (arr[mid] >= arr[low]) { // left part is sorted
                low = mid + 1;
            } else { // right part is sorted
                high = mid - 1;
            }
        }

        return 0; // Default case (array not rotated)
    }

    public static void main(String[] args) {
        int[] arr = {4, 5, 6, 7, 0, 1, 2, 3};
        int rotations = findKRotation(arr);
        System.out.println("The array is rotated " + rotations + " times.");
    }
}

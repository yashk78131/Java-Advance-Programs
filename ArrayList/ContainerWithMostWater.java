import java.util.*;

public class ContainerWithMostWater {

    public static void main(String args[]) {
        // Taking input from user for flexibility
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> height = new ArrayList<>();

        System.out.print("Enter number of lines: ");
        int n = sc.nextInt();

        System.out.println("Enter heights of each line:");
        for (int i = 0; i < n; i++) {
            height.add(sc.nextInt());
        }

        // Initialize maxArea with the minimum possible value
        int maxArea = Integer.MIN_VALUE;

        // Calculate maximum water area
        System.out.println("Maximum water area: " + mostWater(height, maxArea));
    }

    // Function to calculate maximum area between two lines
    public static int mostWater(ArrayList<Integer> height, int maxArea) {
        for (int i = 0; i < height.size(); i++) {
            for (int j = i + 1; j < height.size(); j++) {
                int h = Math.min(height.get(i), height.get(j)); // height = min of two lines
                int w = j - i; // width = distance between lines
                int area = h * w; // calculate area
                maxArea = Math.max(area, maxArea); // update max area
            }
        }
        return maxArea;
    }
}
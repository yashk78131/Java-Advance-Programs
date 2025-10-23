import java.util.PriorityQueue;
/* Given an array of points in 2D space, find the k closest points to the origin (0,0).
Requirements:
1.Use a max heap of size k to keep track of closest points during iteration.
2.Use custom comparator based on Euclidean distance squared to avoid floating point operations. */ 
public class KClosestPoints {
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(distanceSquared(b), distanceSquared(a))
        );

        for (int[] point : points) {
            maxHeap.offer(point);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll();
        }
        return result;
    }

    private int distanceSquared(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }

    public static void main(String[] args) {
        KClosestPoints solver = new KClosestPoints();
        int[][] points = {{1, 3}, {3, 4}, {2, -1}};
        int k = 2;
        int[][] closest = solver.kClosest(points, k);
        for (int[] p : closest) {
            System.out.println(p[0] + ", " + p[1]);
        }
    }
}

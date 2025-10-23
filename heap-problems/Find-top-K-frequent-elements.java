import java.util.*;
/* Given a non-empty array of integers, return the k most frequent elements.
Requirements:
1. Use a hashmap to count frequencies.
2. Use a min heap of size k to track top frequent elements. */
public class TopKFrequent {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
            Comparator.comparingInt(frequencyMap::get)
        );

        for (int num : frequencyMap.keySet()) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = minHeap.poll();
        }
        return result;
    }

    public static void main(String[] args) {
        TopKFrequent solver = new TopKFrequent();
        int[] nums = {1,1,1,2,2,3};
        int k = 2;
        int[] topK = solver.topKFrequent(nums, k);
        for(int num : topK) {
            System.out.print(num + " ");
        }
    }
}

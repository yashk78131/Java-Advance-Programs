/*
 * Minimize the Maximum Edge Weight of Graph
 *
 * Problem:
 * You are given a directed weighted graph of n nodes (0 to n-1), and an integer threshold.
 * You can remove some edges (possibly none) so that:
 *   1️⃣ Node 0 is reachable from all other nodes.
 *   2️⃣ The maximum edge weight in the remaining graph is minimized.
 *   3️⃣ Each node has at most `threshold` outgoing edges.
 *
 * You must return the minimum possible maximum edge weight after removals.
 * If it’s impossible, return -1.
 *
 * Example:
 * n = 5, edges = [[1,0,1],[2,0,2],[3,0,1],[4,3,1],[2,1,1]], threshold = 2
 * Output: 1
 *
 * Explanation: 
 * Remove edge 2->0 (weight 2). The remaining edges have max weight = 1 and all nodes can reach 0.
 *
 * --------------------------------------------------------
 * Approach:
 * ✅ Binary Search on the Answer (similar to Book Allocation)
 * 
 * - The answer (minimized max edge weight) lies between [minWeight, maxWeight].
 * - For a mid value, check if we can satisfy all conditions keeping only edges with weight ≤ mid.
 * 
 * Check function steps:
 *   1. Build a reversed adjacency list for edges with weight ≤ mid.
 *   2. Perform DFS/BFS from node 0 on the reversed graph.
 *   3. Ensure every node can reach 0.
 *   4. Also verify each node's outgoing edges ≤ threshold.
 *
 * --------------------------------------------------------
 * Time Complexity: O(E * log(maxWeight))
 * Space Complexity: O(V + E)
 */

import java.util.*;

class Solution {
    // Check if graph can satisfy all conditions with max edge weight = mid
    private boolean isPossible(int n, int[][] edges, int threshold, int mid) {
        Map<Integer, List<int[]>> adj = new HashMap<>();
        int[] outDegree = new int[n];

        // Build adjacency list (only edges with weight ≤ mid)
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            if (w <= mid) {
                adj.computeIfAbsent(v, k -> new ArrayList<>()).add(new int[]{u, w});
                outDegree[u]++;
                if (outDegree[u] > threshold) return false; // exceeds threshold
            }
        }

        // BFS or DFS to check reachability (reverse direction)
        boolean[] visited = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        visited[0] = true;

        while (!q.isEmpty()) {
            int node = q.poll();
            if (!adj.containsKey(node)) continue;

            for (int[] p : adj.get(node)) {
                int next = p[0];
                if (!visited[next]) {
                    visited[next] = true;
                    q.offer(next);
                }
            }
        }

        // All nodes must reach 0
        for (int i = 0; i < n; i++) {
            if (!visited[i]) return false;
        }

        return true;
    }

    public int minimizeMaxEdgeWeight(int n, int[][] edges, int threshold) {
        int maxWeight = 0;
        for (int[] e : edges) maxWeight = Math.max(maxWeight, e[2]);

        int left = 1, right = maxWeight;
        int result = -1;

        // Binary Search on answer
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (isPossible(n, edges, threshold, mid)) {
                result = mid;
                right = mid - 1; // try smaller max weight
            } else {
                left = mid + 1; // need to allow larger weights
            }
        }

        return result;
    }
}

public class MinimizeMaxEdgeWeight {
    public static void main(String[] args) {
        Solution sol = new Solution();

        // Test Case 1
        System.out.println("Test Case 1:");
        int[][] edges1 = {{1,0,1},{2,0,2},{3,0,1},{4,3,1},{2,1,1}};
        int n1 = 5, threshold1 = 2;
        System.out.println("Output: " + sol.minimizeMaxEdgeWeight(n1, edges1, threshold1)); // 1
        System.out.println();

        // Test Case 2
        System.out.println("Test Case 2:");
        int[][] edges2 = {{0,1,1},{0,2,2},{0,3,1},{0,4,1},{1,2,1},{1,4,1}};
        int n2 = 5, threshold2 = 1;
        System.out.println("Output: " + sol.minimizeMaxEdgeWeight(n2, edges2, threshold2)); // -1
        System.out.println();

        // Test Case 3
        System.out.println("Test Case 3:");
        int[][] edges3 = {{1,2,1},{1,3,3},{1,4,5},{2,3,2},{3,4,2},{4,0,1}};
        int n3 = 5, threshold3 = 1;
        System.out.println("Output: " + sol.minimizeMaxEdgeWeight(n3, edges3, threshold3)); // 2
        System.out.println();

        // Test Case 4
        System.out.println("Test Case 4:");
        int[][] edges4 = {{1,2,1},{1,3,3},{1,4,5},{2,3,2},{4,0,1}};
        int n4 = 5, threshold4 = 1;
        System.out.println("Output: " + sol.minimizeMaxEdgeWeight(n4, edges4, threshold4)); // -1
        System.out.println();
    }
}

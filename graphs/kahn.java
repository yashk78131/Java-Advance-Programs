/**
 * Topological Sort using Kahn’s Algorithm
 * --------------------------------------
 * For Directed Acyclic Graphs (DAG)
 * Orders tasks such that every directed edge u -> v
 * means u comes before v in the order.
 */

import java.util.*;

public class TopologicalSortKahn {
    public static List<Integer> topologicalSort(int V, List<List<Integer>> adj) {
        int[] indegree = new int[V];
        for (int i = 0; i < V; i++)
            for (int nbr : adj.get(i))
                indegree[nbr]++;

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < V; i++)
            if (indegree[i] == 0) q.offer(i);

        List<Integer> topo = new ArrayList<>();
        while (!q.isEmpty()) {
            int node = q.poll();
            topo.add(node);
            for (int nbr : adj.get(node)) {
                indegree[nbr]--;
                if (indegree[nbr] == 0) q.offer(nbr);
            }
        }

        return topo;
    }

    public static void main(String[] args) {
        int V = 6;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        adj.get(5).add(2);
        adj.get(5).add(0);
        adj.get(4).add(0);
        adj.get(4).add(1);
        adj.get(2).add(3);
        adj.get(3).add(1);

        System.out.println("Topological Order: " + topologicalSort(V, adj));
    }
}

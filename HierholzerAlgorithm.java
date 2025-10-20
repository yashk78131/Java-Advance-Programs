/**
 * Hierholzer's Algorithm
 * ----------------------
 * Finds Eulerian Circuit or Path in a Graph.
 * 
 * Requirements:
 * - For Eulerian Circuit: Every vertex has even degree.
 * - For Eulerian Path: Exactly two vertices have odd degree.
 * 
 * Time Complexity: O(E)
 */

import java.util.*;

class HierholzerAlgorithm {
    static class Graph {
        int V;
        List<LinkedList<Integer>> adj;

        Graph(int V) {
            this.V = V;
            adj = new ArrayList<>();
            for (int i = 0; i < V; i++)
                adj.add(new LinkedList<>());
        }

        void addEdge(int u, int v) {
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
    }

    static List<Integer> findEulerianPath(Graph g) {
        Stack<Integer> stack = new Stack<>();
        List<Integer> path = new ArrayList<>();

        int start = 0;
        for (int i = 0; i < g.V; i++) {
            if (g.adj.get(i).size() % 2 == 1) { // odd degree
                start = i;
                break;
            }
        }

        stack.push(start);

        while (!stack.isEmpty()) {
            int u = stack.peek();
            if (g.adj.get(u).size() == 0) {
                path.add(u);
                stack.pop();
            } else {
                int v = g.adj.get(u).poll();
                g.adj.get(v).remove((Integer) u); // remove reverse edge
                stack.push(v);
            }
        }

        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 0);

        List<Integer> path = findEulerianPath(g);
        System.out.println("Eulerian Path or Circuit:");
        System.out.println(path);
    }
}

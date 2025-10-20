/**
 * Johnson's Algorithm (All-Pairs Shortest Paths)
 * ----------------------------------------------
 * Works for sparse graphs with negative edge weights (no negative cycles).
 *
 * Algorithm Steps:
 * 1. Add a dummy node connected to all nodes with weight 0.
 * 2. Run Bellman-Ford to compute vertex potentials (h values).
 * 3. Reweight edges: w'(u, v) = w(u, v) + h[u] - h[v] (removes negatives).
 * 4. Run Dijkstra from each vertex using reweighted edges.
 * 5. Adjust distances back using h values.
 *
 * Time Complexity: O(V * (E log V))
 * Space Complexity: O(V^2)
 *
 * Example use-case: Routing in networks with variable or negative costs.
 */

import java.util.*;

class JohnsonAlgorithm {
    static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) { this.u = u; this.v = v; this.w = w; }
    }

    static class Graph {
        int V;
        List<Edge> edges;
        List<List<Edge>> adj;

        Graph(int V) {
            this.V = V;
            edges = new ArrayList<>();
            adj = new ArrayList<>();
            for (int i = 0; i < V; i++)
                adj.add(new ArrayList<>());
        }

        void addEdge(int u, int v, int w) {
            edges.add(new Edge(u, v, w));
            adj.get(u).add(new Edge(u, v, w));
        }
    }

    // Bellman-Ford Algorithm to detect negative cycles and compute potentials
    static int[] bellmanFord(Graph g, int src) {
        int V = g.V;
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE / 2);
        dist[src] = 0;

        // Relax edges |V|-1 times
        for (int i = 1; i < V; i++) {
            for (Edge e : g.edges) {
                if (dist[e.u] + e.w < dist[e.v])
                    dist[e.v] = dist[e.u] + e.w;
            }
        }

        // Check for negative weight cycle
        for (Edge e : g.edges) {
            if (dist[e.u] + e.w < dist[e.v]) {
                System.out.println("Graph contains a negative weight cycle!");
                return null;
            }
        }
        return dist;
    }

    // Dijkstra using Priority Queue
    static int[] dijkstra(List<List<Edge>> adj, int src) {
        int V = adj.size();
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE / 2);
        dist[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{src, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int u = cur[0];
            int d = cur[1];

            if (d > dist[u]) continue;

            for (Edge e : adj.get(u)) {
                if (dist[u] + e.w < dist[e.v]) {
                    dist[e.v] = dist[u] + e.w;
                    pq.offer(new int[]{e.v, dist[e.v]});
                }
            }
        }
        return dist;
    }

    static void johnson(Graph g) {
        int V = g.V;

        // Step 1: Create an extended graph with a dummy vertex
        Graph extended = new Graph(V + 1);
        for (Edge e : g.edges)
            extended.addEdge(e.u, e.v, e.w);
        for (int v = 0; v < V; v++)
            extended.addEdge(V, v, 0); // dummy node connected to all

        // Step 2: Run Bellman-Ford from dummy node to get vertex potentials
        int[] h = bellmanFord(extended, V);
        if (h == null) return; // Negative cycle detected

        // Step 3: Reweight edges in original graph
        List<List<Edge>> newAdj = new ArrayList<>();
        for (int i = 0; i < V; i++) newAdj.add(new ArrayList<>());

        for (Edge e : g.edges) {
            int newWeight = e.w + h[e.u] - h[e.v];
            newAdj.get(e.u).add(new Edge(e.u, e.v, newWeight));
        }

        // Step 4: Run Dijkstra for each vertex
        int[][] distMatrix = new int[V][V];
        for (int u = 0; u < V; u++) {
            int[] dist = dijkstra(newAdj, u);
            for (int v = 0; v < V; v++) {
                // Step 5: Convert back to original weights
                if (dist[v] < Integer.MAX_VALUE / 2)
                    distMatrix[u][v] = dist[v] - h[u] + h[v];
                else
                    distMatrix[u][v] = Integer.MAX_VALUE / 2;
            }
        }

        // Print result
        System.out.println("All-Pairs Shortest Path Matrix (Johnson's Algorithm):");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (distMatrix[i][j] >= Integer.MAX_VALUE / 4)
                    System.out.print("INF\t");
                else
                    System.out.print(distMatrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0, 1, -1);
        g.addEdge(0, 2, 4);
        g.addEdge(1, 2, 3);
        g.addEdge(1, 3, 2);
        g.addEdge(1, 4, 2);
        g.addEdge(3, 2, 5);
        g.addEdge(3, 1, 1);
        g.addEdge(4, 3, -3);

        johnson(g);
    }
}

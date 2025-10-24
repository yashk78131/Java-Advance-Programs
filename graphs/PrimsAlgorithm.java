import java.util.*;

class Edge {
    int dest, weight;

    Edge(int dest, int weight) {
        this.dest = dest;
        this.weight = weight;
    }
}

class Node implements Comparable<Node> {
    int vertex, weight;

    Node(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    // for PriorityQueue sorting by weight
    public int compareTo(Node other) {
        return this.weight - other.weight;
    }
}

public class PrimsAlgorithm {
    static void primsMST(List<List<Edge>> graph, int V) {
        boolean[] inMST = new boolean[V];
        int[] parent = new int[V];
        int[] key = new int[V];

        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        PriorityQueue<Node> pq = new PriorityQueue<>();

        // start from vertex 0
        key[0] = 0;
        pq.add(new Node(0, 0));

        while (!pq.isEmpty()) {
            int u = pq.poll().vertex;

            // if already included, skip
            if (inMST[u]) continue;
            inMST[u] = true;

            // explore all adjacent edges
            for (Edge e : graph.get(u)) {
                int v = e.dest;
                int w = e.weight;

                // if v not in MST and edge weight < current key[v]
                if (!inMST[v] && w < key[v]) {
                    key[v] = w;
                    parent[v] = u;
                    pq.add(new Node(v, key[v]));
                }
            }
        }

        // print MST
        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " - " + i + "\t" + key[i]);
        }
    }

    public static void main(String[] args) {
        int V = 5;
        List<List<Edge>> graph = new ArrayList<>();

        // initialize adjacency list
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
        }

        // undirected weighted edges
        graph.get(0).add(new Edge(1, 2));
        graph.get(0).add(new Edge(3, 6));
        graph.get(1).add(new Edge(0, 2));
        graph.get(1).add(new Edge(2, 3));
        graph.get(1).add(new Edge(3, 8));
        graph.get(1).add(new Edge(4, 5));
        graph.get(2).add(new Edge(1, 3));
        graph.get(2).add(new Edge(4, 7));
        graph.get(3).add(new Edge(0, 6));
        graph.get(3).add(new Edge(1, 8));
        graph.get(4).add(new Edge(1, 5));
        graph.get(4).add(new Edge(2, 7));

        primsMST(graph, V);
    }
}

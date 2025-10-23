import java.util.*;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    // Edge constructor: makes an edge between src and dest with a given weight
    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    // Compare edges by their weight (smallest first)
    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class KruskalsAlgorithmDynamic {

    // Finds the leader of the group for vertex v with shortcutting for speed
    static int find(int[] parent, int v) {
        if (parent[v] != v) {
            parent[v] = find(parent, parent[v]);
        }
        return parent[v];
    }

    // Links two groups together, using rank to keep things balanced
    static void union(int[] parent, int[] rank, int u, int v) {
        int rootU = find(parent, u);
        int rootV = find(parent, v);

        if (rootU == rootV)
            return;
        if (rank[rootU] < rank[rootV]) {
            parent[rootU] = rootV;
        } else if (rank[rootV] < rank[rootU]) {
            parent[rootV] = rootU;
        } else {
            parent[rootV] = rootU;
            rank[rootU]++;
        }
    }

    // Main function: builds the MST and prints result
    static void kruskalMST(int V, List<Edge> edges) {
        // Sort edges by weight (start from the cheapest)
        Collections.sort(edges);

        int[] parent = new int[V];
        int[] rank = new int[V];

        for (int i = 0; i < V; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        List<Edge> mst = new ArrayList<>();
        int mstWeight = 0;
        int edgesAdded = 0;

        for (Edge edge : edges) {
            // Ignore edges that start and end at same node (self-loops)
            if (edge.src == edge.dest)
                continue;

            int uSet = find(parent, edge.src);
            int vSet = find(parent, edge.dest);

            // Only add edge if it joins different groups (avoids cycles)
            if (uSet != vSet) {
                mst.add(edge);
                mstWeight += edge.weight;
                union(parent, rank, uSet, vSet);
                edgesAdded++;
            }

            // If we've added enough edges, we can stop
            if (edgesAdded == V - 1)
                break;
        }

        // Print results in a friendly way
        if (mst.size() < V - 1) {
            System.out.println("Sorry, the graph isn't connected – cannot make a spanning tree for all nodes!");
        } else {
            System.out.println("Here's your Minimum Spanning Tree (MST):");
            for (Edge e : mst) {
                System.out.println("Node " + e.src + " is linked to node " + e.dest + " with weight " + e.weight);
            }
            System.out.println("Total weight of MST: " + mstWeight);
        }
    }

    // Helper to read edges from user and remove duplicates
    static List<Edge> readEdges(int V, int E, Scanner sc) {
        Set<String> uniqueEdges = new HashSet<>();
        List<Edge> edges = new ArrayList<>();
        System.out.println("Enter edges in format: src dest weight (each between 0 and " + (V - 1) + "):");
        for (int i = 0; i < E; i++) {
            int src = sc.nextInt();
            int dest = sc.nextInt();
            int weight = sc.nextInt();

            if (src < 0 || src >= V || dest < 0 || dest >= V) {
                System.out.println("Bad input: node numbers must be between 0 and " + (V - 1));
                i--; // let user retry this edge
                continue;
            }

            String key = (src < dest ? src + ":" + dest : dest + ":" + src);
            if (uniqueEdges.contains(key)) {
                System.out.println("Edge already entered, skipping duplicate.");
                continue;
            }
            uniqueEdges.add(key);
            edges.add(new Edge(src, dest, weight));
        }
        return edges;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("How many nodes in your graph? ");
        int V = sc.nextInt();

        System.out.print("How many edges in your graph? ");
        int E = sc.nextInt();

        List<Edge> edges = readEdges(V, E, sc);
        kruskalMST(V, edges);
    }
}

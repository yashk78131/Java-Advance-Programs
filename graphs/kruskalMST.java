/**
 * Kruskal's Minimum Spanning Tree (MST)
 * -------------------------------------
 * Greedy algorithm to find MST using Disjoint Set (Union-Find).
 */

import java.util.*;

public class KruskalMST {
    static class Edge implements Comparable<Edge> {
        int u, v, weight;
        Edge(int u, int v, int w) { this.u = u; this.v = v; this.weight = w; }
        public int compareTo(Edge o) { return this.weight - o.weight; }
    }

    static int find(int[] parent, int x) {
        if (parent[x] != x)
            parent[x] = find(parent, parent[x]);
        return parent[x];
    }

    static void union(int[] parent, int[] rank, int a, int b) {
        int ra = find(parent, a), rb = find(parent, b);
        if (ra != rb) {
            if (rank[ra] < rank[rb]) parent[ra] = rb;
            else if (rank[rb] < rank[ra]) parent[rb] = ra;
            else { parent[rb] = ra; rank[ra]++; }
        }
    }

    public static void main(String[] args) {
        int V = 4;
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 10));
        edges.add(new Edge(0, 2, 6));
        edges.add(new Edge(0, 3, 5));
        edges.add(new Edge(1, 3, 15));
        edges.add(new Edge(2, 3, 4));

        Collections.sort(edges);
        int[] parent = new int[V];
        int[] rank = new int[V];
        for (int i = 0; i < V; i++) parent[i] = i;

        List<Edge> mst = new ArrayList<>();
        for (Edge e : edges) {
            int uRoot = find(parent, e.u);
            int vRoot = find(parent, e.v);
            if (uRoot != vRoot) {
                mst.add(e);
                union(parent, rank, uRoot, vRoot);
            }
        }

        System.out.println("Edges in MST:");
        for (Edge e : mst)
            System.out.println(e.u + " - " + e.v + " : " + e.weight);
    }
}

import java.util.*;

// Class to represent an edge in the graph
class Edge implements Comparable<Edge> {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    // Sort edges by weight
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

// Disjoint Set (Union-Find) to detect cycles
class DisjointSet {
    int[] parent, rank;

    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for(int i=0; i<n; i++) parent[i] = i;
    }

    public int find(int u) {
        if(parent[u] != u)
            parent[u] = find(parent[u]); // Path compression
        return parent[u];
    }

    public boolean union(int u, int v) {
        int pu = find(u);
        int pv = find(v);
        if(pu == pv) return false; // Cycle detected

        // Union by rank
        if(rank[pu] < rank[pv])
            parent[pu] = pv;
        else if(rank[pu] > rank[pv])
            parent[pv] = pu;
        else {
            parent[pv] = pu;
            rank[pu]++;
        }
        return true;
    }
}

public class KruskalMST {

    // Function to compute MST
    public static List<Edge> kruskalMST(List<Edge> edges, int n) {
        Collections.sort(edges); // Sort edges by weight
        DisjointSet ds=new DisjointSet(n);

        List<Edge> mst = new ArrayList<>();
        for(Edge edge:edges) {
            if(ds.union(edge.src, edge.dest)) {
                mst.add(edge);
            }
        }
        return mst;
    }

    public static void main(String[] args) {
        int n = 4; // Number of vertices
        List<Edge> edges=new ArrayList<>();

        // Add edges: (src, dest, weight)
        edges.add(new Edge(0, 1, 10));
        edges.add(new Edge(0, 2, 6));
        edges.add(new Edge(0, 3, 5));
        edges.add(new Edge(1, 3, 15));
        edges.add(new Edge(2, 3, 4));

        List<Edge> mst=kruskalMST(edges, n);

        System.out.println("Edges in Minimum Spanning Tree:");
        int totalWeight=0;
        for(Edge e:mst){
            System.out.println(e.src+" - "+ e.dest +" : "+ e.weight);
            totalWeight+=e.weight;
        }
        System.out.println("Total weight: "+totalWeight);
    }
}

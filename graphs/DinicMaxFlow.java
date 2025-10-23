import java.util.*;

/**
 * Dinic's Algorithm for Maximum Flow (O(E * V^2) worst-case, much faster in practice).
 * - Uses BFS level graph and DFS blocking flow.
 * - Supports multiple edges, directed graphs, and large capacities (long type).
 * - Includes an example in main().
 */
public class DinicMaxFlow {

    static class Edge {
        int to, rev;    // destination and index of reverse edge in adjacency list
        long cap;       // remaining capacity
        Edge(int to, int rev, long cap) { this.to = to; this.rev = rev; this.cap = cap; }
    }

    static class Dinic {
        private final List<Edge>[] g;
        private int[] level;
        private int[] it; // current iterator for DFS to avoid re-scanning

        @SuppressWarnings("unchecked")
        Dinic(int n) {
            g = new ArrayList[n];
            for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
            level = new int[n];
            it = new int[n];
        }

        /** Adds a directed edge u->v with capacity cap. Adds reverse edge with 0 capacity. */
        void addEdge(int u, int v, long cap) {
            if (cap < 0) throw new IllegalArgumentException("Negative capacity not allowed");
            Edge a = new Edge(v, g[v].size(), cap);
            Edge b = new Edge(u, g[u].size(), 0);
            g[u].add(a);
            g[v].add(b);
        }

        /** BFS builds level graph. Returns true if sink is reachable. */
        private boolean bfs(int s, int t) {
            java.util.Arrays.fill(level, -1);
            Deque<Integer> dq = new ArrayDeque<>();
            level[s] = 0;
            dq.add(s);
            while (!dq.isEmpty()) {
                int u = dq.removeFirst();
                for (Edge e : g[u]) {
                    if (e.cap > 0 && level[e.to] < 0) {
                        level[e.to] = level[u] + 1;
                        dq.addLast(e.to);
                    }
                }
            }
            return level[t] >= 0;
        }

        /** DFS sends blocking flow. */
        private long dfs(int u, int t, long f) {
            if (u == t) return f;
            for (int i = it[u]; i < g[u].size(); i++, it[u] = i) {
                Edge e = g[u].get(i);
                if (e.cap <= 0 || level[e.to] != level[u] + 1) continue;
                long ret = dfs(e.to, t, Math.min(f, e.cap));
                if (ret > 0) {
                    e.cap -= ret;
                    g[e.to].get(e.rev).cap += ret; // reverse edge
                    return ret;
                }
            }
            return 0;
        }

        /** Computes max flow from s to t. */
        long maxFlow(int s, int t) {
            long flow = 0;
            while (bfs(s, t)) {
                java.util.Arrays.fill(it, 0);
                long f;
                while ((f = dfs(s, t, Long.MAX_VALUE)) > 0) {
                    flow += f;
                }
            }
            return flow;
        }

        /** Access to adjacency list (for min-cut or residual inspection). */
        List<Edge>[] graph() { return g; }
    }

    // Demo: small network
    public static void main(String[] args) {
        // Example network (from CLRS style):
        // 0 -> 1 (10), 0 -> 2 (10)
        // 1 -> 2 (2), 1 -> 3 (4), 1 -> 4 (8)
        // 2 -> 4 (9)
        // 4 -> 3 (6), 3 -> 5 (10), 4 -> 5 (10)
        int n = 6; int s = 0; int t = 5;
        Dinic dinic = new Dinic(n);
        dinic.addEdge(0, 1, 10);
        dinic.addEdge(0, 2, 10);
        dinic.addEdge(1, 2, 2);
        dinic.addEdge(1, 3, 4);
        dinic.addEdge(1, 4, 8);
        dinic.addEdge(2, 4, 9);
        dinic.addEdge(4, 3, 6);
        dinic.addEdge(3, 5, 10);
        dinic.addEdge(4, 5, 10);

        long maxflow = dinic.maxFlow(s, t);
        System.out.println("Max Flow = " + maxflow); // Expected 19

        // Optional: show min-cut partition using BFS on residual graph from source
        boolean[] vis = new boolean[n];
        Deque<Integer> q = new ArrayDeque<>();
        q.add(s); vis[s] = true;
        while (!q.isEmpty()) {
            int u = q.removeFirst();
            for (Edge e : dinic.graph()[u]) {
                if (e.cap > 0 && !vis[e.to]) { vis[e.to] = true; q.addLast(e.to); }
            }
        }
        System.out.print("Min-cut S-side: ");
        for (int i = 0; i < n; i++) if (vis[i]) System.out.print(i + " ");
        System.out.println();
    }
}

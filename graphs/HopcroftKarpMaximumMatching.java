import java.util.*;

/**
 * Hopcroft–Karp Algorithm for Maximum Bipartite Matching.
 *
 * Graph model:
 * - Left partition U = {0..nLeft-1}
 * - Right partition V = {0..nRight-1}
 * - Edges are from U to V (undirected matching edges)
 *
 * Complexity: O(E * sqrt(V))
 * Implementation details:
 * - BFS builds layers (distance on U) from all free U-vertices.
 * - DFS finds vertex-disjoint shortest augmenting paths within the layered graph.
 * - Uses -1 for "NIL" pair to avoid extra node.
 * - Uses long-safe collections and avoids name clashes with user-defined Arrays class by fully qualifying java.util.Arrays when needed.
 */
public class HopcroftKarpMaximumMatching {

    public static class HopcroftKarp {
        private final int nLeft, nRight;
        private final List<Integer>[] adj; // adj[u] = list of v in [0..nRight)

        // pairU[u] = matched v or -1; pairV[v] = matched u or -1
        private final int[] pairU, pairV;
        private final int[] dist; // distance for BFS layering on U side

        @SuppressWarnings("unchecked")
        public HopcroftKarp(int nLeft, int nRight) {
            if (nLeft < 0 || nRight < 0) throw new IllegalArgumentException("Negative partition size");
            this.nLeft = nLeft;
            this.nRight = nRight;
            adj = new ArrayList[nLeft];
            for (int i = 0; i < nLeft; i++) adj[i] = new ArrayList<>();
            pairU = new int[nLeft];
            pairV = new int[nRight];
            dist = new int[nLeft];
            for (int i = 0; i < nLeft; i++) pairU[i] = -1;
            for (int j = 0; j < nRight; j++) pairV[j] = -1;
        }

        /** Add an edge u in [0..nLeft) to v in [0..nRight). */
        public void addEdge(int u, int v) {
            if (u < 0 || u >= nLeft || v < 0 || v >= nRight)
                throw new IndexOutOfBoundsException("u or v out of range");
            adj[u].add(v);
        }

        /** BFS builds layer graph; returns true if there is any free vertex at the last layer (potential augmenting path). */
        private boolean bfs() {
            ArrayDeque<Integer> q = new ArrayDeque<>();
            // Initialize distances: 0 for free U-vertices, INF for matched ones
            final int INF = Integer.MAX_VALUE;
            for (int u = 0; u < nLeft; u++) {
                if (pairU[u] == -1) {
                    dist[u] = 0;
                    q.addLast(u);
                } else {
                    dist[u] = INF;
                }
            }

            boolean reachableFreeV = false;

            while (!q.isEmpty()) {
                int u = q.removeFirst();
                for (int v : adj[u]) {
                    int u2 = pairV[v];
                    if (u2 == -1) {
                        // We found a free vertex on V; indicates there exists an augmenting path
                        reachableFreeV = true;
                    } else if (dist[u2] == INF) {
                        dist[u2] = dist[u] + 1;
                        q.addLast(u2);
                    }
                }
            }
            return reachableFreeV;
        }

        /** DFS searches for augmenting paths from u along the layered graph. */
        private boolean dfs(int u) {
            for (int v : adj[u]) {
                int u2 = pairV[v];
                if (u2 == -1 || (dist[u2] == dist[u] + 1 && dfs(u2))) {
                    pairU[u] = v;
                    pairV[v] = u;
                    return true;
                }
            }
            // Mark as visited dead-end to avoid revisits in current phase
            dist[u] = Integer.MAX_VALUE;
            return false;
        }

        /** Computes maximum matching size. */
        public int maxMatching() {
            int matching = 0;
            while (bfs()) {
                for (int u = 0; u < nLeft; u++) {
                    if (pairU[u] == -1 && dfs(u)) matching++;
                }
            }
            return matching;
        }

        public int[] leftMatches() { return pairU; }  // For each u, matched v or -1
        public int[] rightMatches() { return pairV; } // For each v, matched u or -1
    }

    // Demo
    public static void main(String[] args) {
        // Example bipartite graph (U size=4, V size=4)
        // U0: V0, V1
        // U1: V1, V2
        // U2: V2, V3
        // U3: V0
        HopcroftKarp hk = new HopcroftKarp(4, 4);
        hk.addEdge(0, 0); hk.addEdge(0, 1);
        hk.addEdge(1, 1); hk.addEdge(1, 2);
        hk.addEdge(2, 2); hk.addEdge(2, 3);
        hk.addEdge(3, 0);

        int m = hk.maxMatching();
        System.out.println("Maximum Matching = " + m);
        System.out.println("pairU (U->V): " + java.util.Arrays.toString(hk.leftMatches()));
        System.out.println("pairV (V->U): " + java.util.Arrays.toString(hk.rightMatches()));
    }
}

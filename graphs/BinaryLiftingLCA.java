import java.util.*;

/**
 * Lowest Common Ancestor (Binary Lifting)
 * --------------------------------------
 * Preprocesses a rooted tree to answer LCA queries in O(log N).
 * Also provides depth and distance queries.
 */
public class BinaryLiftingLCA {
    private final int n;                // number of nodes (0..n-1)
    private final int LOG;              // ceil(log2(n))
    private final List<Integer>[] g;    // adjacency list (undirected tree)
    private final int[][] up;           // up[k][v] = 2^k-th ancestor of v (or -1)
    private final int[] depth;          // depth from root

    @SuppressWarnings("unchecked")
    public BinaryLiftingLCA(int n) {
        this.n = n;
        int lg = 1;
        while ((1 << lg) <= Math.max(1, n)) lg++;
        this.LOG = lg;
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
        up = new int[LOG][n];
        depth = new int[n];
        for (int k = 0; k < LOG; k++) java.util.Arrays.fill(up[k], -1);
    }

    public void addEdge(int u, int v) {
        g[u].add(v); g[v].add(u);
    }

    /** Preprocess ancestors and depth; root can be any node in [0..n). */
    public void build(int root) {
        // BFS to set depth and immediate parent
        java.util.Arrays.fill(up[0], -1);
        java.util.Arrays.fill(depth, 0);
        Deque<Integer> dq = new ArrayDeque<>();
        dq.add(root);
        up[0][root] = -1; depth[root] = 0;
        boolean[] vis = new boolean[n];
        vis[root] = true;
        while (!dq.isEmpty()) {
            int u = dq.removeFirst();
            for (int v : g[u]) if (!vis[v]) {
                vis[v] = true;
                up[0][v] = u;
                depth[v] = depth[u] + 1;
                dq.addLast(v);
            }
        }
        // Binary lifting table
        for (int k = 1; k < LOG; k++) {
            for (int v = 0; v < n; v++) {
                int mid = up[k - 1][v];
                up[k][v] = (mid == -1) ? -1 : up[k - 1][mid];
            }
        }
    }

    private int lift(int v, int d) {
        for (int k = 0; k < LOG; k++) if (((d >> k) & 1) == 1) {
            v = (v == -1) ? -1 : up[k][v];
            if (v == -1) break;
        }
        return v;
    }

    /** Returns LCA(u,v) assuming build(root) has been called. */
    public int lca(int u, int v) {
        if (u == v) return u;
        if (depth[u] < depth[v]) { int t = u; u = v; v = t; }
        u = lift(u, depth[u] - depth[v]);
        if (u == v) return u;
        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }
        return up[0][u];
    }

    public int depth(int v) { return depth[v]; }

    /** Distance in edges between u and v. */
    public int dist(int u, int v) {
        int w = lca(u, v);
        return depth[u] + depth[v] - 2 * depth[w];
    }

    // Demo
    public static void main(String[] args) {
        // Example tree
        // 0-1, 1-2, 1-3, 3-4, 3-5
        BinaryLiftingLCA lca = new BinaryLiftingLCA(6);
        lca.addEdge(0,1); lca.addEdge(1,2); lca.addEdge(1,3); lca.addEdge(3,4); lca.addEdge(3,5);
        lca.build(0);

        System.out.println("LCA(2,4)=" + lca.lca(2,4)); // expected 1
        System.out.println("LCA(4,5)=" + lca.lca(4,5)); // expected 3
        System.out.println("dist(2,5)=" + lca.dist(2,5)); // expected 4
    }
}

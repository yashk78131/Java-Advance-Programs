import java.util.*;

/**
 * Heavy-Light Decomposition (HLD)
 * -------------------------------
 * Decomposes a tree into heavy paths to support path queries in O(log^2 N) with a segment tree.
 * This implementation provides:
 * - addEdge(u, v): undirected tree edges
 * - setValue(u, val): assign initial value at node u
 * - build(root): preprocess sizes, parents, head, and positions; builds segment tree
 * - pathSum(u, v): sum of node values along path u..v
 * - pointUpdate(u, delta): add delta to node u's value
 */
public class HeavyLightDecomposition {
    private final int n;
    private final List<Integer>[] g;
    private final int[] parent, depth, heavy, head, pos, size;
    private int curPos = 0;
    private final long[] base; // base array in Euler order for segtree

    // Segment tree for range sum + point update
    private final long[] seg;

    @SuppressWarnings("unchecked")
    public HeavyLightDecomposition(int n) {
        this.n = n;
        g = new ArrayList[n];
        for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
        parent = new int[n]; depth = new int[n]; heavy = new int[n]; head = new int[n]; pos = new int[n]; size = new int[n];
        java.util.Arrays.fill(heavy, -1);
        base = new long[n];
        seg = new long[4 * n];
    }

    public void addEdge(int u, int v) { g[u].add(v); g[v].add(u); }

    public void setValue(int u, long val) { base[u] = val; }

    private int dfs(int u, int p) {
        parent[u] = p;
        size[u] = 1;
        int maxSub = 0;
        for (int v : g[u]) if (v != p) {
            depth[v] = depth[u] + 1;
            int sub = dfs(v, u);
            if (sub > maxSub) { maxSub = sub; heavy[u] = v; }
            size[u] += sub;
        }
        return size[u];
    }

    private void decompose(int u, int h) {
        head[u] = h;
        pos[u] = curPos++;
        if (heavy[u] != -1) decompose(heavy[u], h);
        for (int v : g[u]) if (v != parent[u] && v != heavy[u]) decompose(v, v);
    }

    public void build(int root) {
        dfs(root, -1);
        curPos = 0;
        decompose(root, root);
        // Build segtree using reordered base by pos
        long[] arr = new long[n];
        for (int u = 0; u < n; u++) arr[pos[u]] = base[u];
        buildSeg(1, 0, n - 1, arr);
    }

    private void buildSeg(int idx, int l, int r, long[] arr) {
        if (l == r) { seg[idx] = arr[l]; return; }
        int m = (l + r) >>> 1;
        buildSeg(idx << 1, l, m, arr);
        buildSeg(idx << 1 | 1, m + 1, r, arr);
        seg[idx] = seg[idx << 1] + seg[idx << 1 | 1];
    }

    private void updateSeg(int idx, int l, int r, int p, long delta) {
        if (l == r) { seg[idx] += delta; return; }
        int m = (l + r) >>> 1;
        if (p <= m) updateSeg(idx << 1, l, m, p, delta);
        else updateSeg(idx << 1 | 1, m + 1, r, p, delta);
        seg[idx] = seg[idx << 1] + seg[idx << 1 | 1];
    }

    private long querySeg(int idx, int l, int r, int ql, int qr) {
        if (ql > r || qr < l) return 0;
        if (ql <= l && r <= qr) return seg[idx];
        int m = (l + r) >>> 1;
        return querySeg(idx << 1, l, m, ql, qr) + querySeg(idx << 1 | 1, m + 1, r, ql, qr);
    }

    public void pointUpdate(int u, long delta) {
        updateSeg(1, 0, n - 1, pos[u], delta);
    }

    public long pathSum(int a, int b) {
        long res = 0;
        while (head[a] != head[b]) {
            if (depth[head[a]] < depth[head[b]]) { int t = a; a = b; b = t; }
            res += querySeg(1, 0, n - 1, pos[head[a]], pos[a]);
            a = parent[head[a]];
        }
        if (depth[a] > depth[b]) { int t = a; a = b; b = t; }
        res += querySeg(1, 0, n - 1, pos[a], pos[b]);
        return res;
    }

    // Demo
    public static void main(String[] args) {
        // Tree: 0-1, 1-2, 1-3, 3-4, 3-5 with initial node values 1..6
        HeavyLightDecomposition hld = new HeavyLightDecomposition(6);
        hld.addEdge(0,1); hld.addEdge(1,2); hld.addEdge(1,3); hld.addEdge(3,4); hld.addEdge(3,5);
        for (int i = 0; i < 6; i++) hld.setValue(i, i + 1);
        hld.build(0);

        System.out.println("sum(2,4)=" + hld.pathSum(2,4)); // path 2-1-3-4: 3+2+4+5=14
        hld.pointUpdate(3, 10); // add 10 to node 3
        System.out.println("sum(2,4) after update=" + hld.pathSum(2,4)); // now +10 -> 24
    }
}

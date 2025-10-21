/**
 * Segment Tree with Lazy Propagation
 * -----------------------------------
 * Supports:
 * - Range sum query in O(log n)
 * - Range update (add delta to [l..r]) in O(log n) with lazy propagation
 * - Point query in O(log n)
 */
public class SegmentTreeLazy {
    private final int n;
    private final long[] tree, lazy;

    public SegmentTreeLazy(int n) {
        this.n = n;
        tree = new long[4 * n];
        lazy = new long[4 * n];
    }

    public SegmentTreeLazy(long[] arr) {
        this(arr.length);
        build(1, 0, n - 1, arr);
    }

    private void build(int idx, int l, int r, long[] arr) {
        if (l == r) { tree[idx] = arr[l]; return; }
        int m = (l + r) >>> 1;
        build(idx << 1, l, m, arr);
        build(idx << 1 | 1, m + 1, r, arr);
        tree[idx] = tree[idx << 1] + tree[idx << 1 | 1];
    }

    private void push(int idx, int l, int r) {
        if (lazy[idx] == 0) return;
        tree[idx] += (r - l + 1) * lazy[idx];
        if (l != r) {
            lazy[idx << 1] += lazy[idx];
            lazy[idx << 1 | 1] += lazy[idx];
        }
        lazy[idx] = 0;
    }

    /** Range update: add delta to all elements in [ql..qr] (0-indexed). */
    public void rangeUpdate(int ql, int qr, long delta) {
        rangeUpdate(1, 0, n - 1, ql, qr, delta);
    }

    private void rangeUpdate(int idx, int l, int r, int ql, int qr, long delta) {
        push(idx, l, r);
        if (ql > r || qr < l) return;
        if (ql <= l && r <= qr) {
            lazy[idx] += delta;
            push(idx, l, r);
            return;
        }
        int m = (l + r) >>> 1;
        rangeUpdate(idx << 1, l, m, ql, qr, delta);
        rangeUpdate(idx << 1 | 1, m + 1, r, ql, qr, delta);
        push(idx << 1, l, m);
        push(idx << 1 | 1, m + 1, r);
        tree[idx] = tree[idx << 1] + tree[idx << 1 | 1];
    }

    /** Range sum query for [ql..qr] (0-indexed). */
    public long rangeSum(int ql, int qr) {
        return rangeSum(1, 0, n - 1, ql, qr);
    }

    private long rangeSum(int idx, int l, int r, int ql, int qr) {
        push(idx, l, r);
        if (ql > r || qr < l) return 0;
        if (ql <= l && r <= qr) return tree[idx];
        int m = (l + r) >>> 1;
        return rangeSum(idx << 1, l, m, ql, qr) + rangeSum(idx << 1 | 1, m + 1, r, ql, qr);
    }

    /** Point query: get value at index i (0-indexed). */
    public long get(int i) { return rangeSum(i, i); }

    // Demo
    public static void main(String[] args) {
        long[] arr = {1, 3, 5, 7, 9, 11};
        SegmentTreeLazy st = new SegmentTreeLazy(arr);
        System.out.println("Initial sum[1..4]: " + st.rangeSum(1, 4)); // 3+5+7+9 = 24
        st.rangeUpdate(1, 3, 10); // add 10 to indices 1,2,3
        System.out.println("After update sum[1..4]: " + st.rangeSum(1, 4)); // 13+15+17+9 = 54
        System.out.println("Point query arr[2]: " + st.get(2)); // 15
    }
}

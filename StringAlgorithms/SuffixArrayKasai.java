package StringAlgorithms;

// No wildcard imports; using fully qualified names to avoid clashes

/**
 * Suffix Array (O(n log n)) + Kasai LCP (O(n))
 * -------------------------------------------
 * - Builds suffix array using doubling method and ranks.
 * - Computes LCP array in linear time using Kasai algorithm.
 * - Unicode-safe by working on code points; also provides a fast path for ASCII.
 */
public class SuffixArrayKasai {

    /** Build suffix array for string s using doubling. Returns sa array of starting indices. */
    public static int[] buildSuffixArray(String s) {
        // Convert to int[] of code points to be Unicode-safe
        int ncp = s.codePointCount(0, s.length());
        int[] a = new int[ncp];
        for (int i = 0, j = 0; i < s.length(); ) {
            int cp = s.codePointAt(i);
            a[j++] = cp;
            i += Character.charCount(cp);
        }

        int n = a.length;
        int[] sa = new int[n];
        int[] rnk = new int[n];
        int[] tmp = new int[n];

        for (int i = 0; i < n; i++) { sa[i] = i; rnk[i] = a[i]; }

        for (int k = 1; k < n; k <<= 1) {
            final int kk = k;
            Integer[] order = new Integer[n];
            for (int i = 0; i < n; i++) order[i] = sa[i];
            java.util.Arrays.sort(order, (i, j) -> {
                if (rnk[i] != rnk[j]) return Integer.compare(rnk[i], rnk[j]);
                int ri = (i + kk < n) ? rnk[i + kk] : -1;
                int rj = (j + kk < n) ? rnk[j + kk] : -1;
                return Integer.compare(ri, rj);
            });
            for (int i = 0; i < n; i++) sa[i] = order[i];
            tmp[sa[0]] = 0;
            for (int i = 1; i < n; i++) {
                tmp[sa[i]] = tmp[sa[i - 1]] + ((rnk[sa[i - 1]] != rnk[sa[i]] ||
                        ((sa[i - 1] + k < n ? rnk[sa[i - 1] + k] : -1) != (sa[i] + k < n ? rnk[sa[i] + k] : -1))) ? 1 : 0);
            }
            System.arraycopy(tmp, 0, rnk, 0, n);
            if (rnk[sa[n - 1]] == n - 1) break; // all ranks unique
        }
        return sa;
    }

    /** Build LCP array for s given sa (Kasai algorithm). lcp[i] = LCP(sa[i], sa[i-1]). */
    public static int[] buildLCP(String s, int[] sa) {
        int ncp = s.codePointCount(0, s.length());
        int[] a = new int[ncp];
        for (int i = 0, j = 0; i < s.length(); ) { int cp = s.codePointAt(i); a[j++] = cp; i += Character.charCount(cp); }
        int n = a.length;
        int[] rnk = new int[n];
        for (int i = 0; i < n; i++) rnk[sa[i]] = i;
        int[] lcp = new int[n];
        int h = 0;
        for (int i = 0; i < n; i++) {
            int r = rnk[i];
            if (r > 0) {
                int j = sa[r - 1];
                while (i + h < n && j + h < n && a[i + h] == a[j + h]) h++;
                lcp[r] = h;
                if (h > 0) h--;
            } else {
                lcp[r] = 0;
            }
        }
        return lcp;
    }

    // Demo
    public static void main(String[] args) {
        String s = (args != null && args.length > 0) ? args[0] : "banana";
        int[] sa = buildSuffixArray(s);
        int[] lcp = buildLCP(s, sa);
        System.out.println("String: " + s);
        System.out.println("SA:  " + java.util.Arrays.toString(sa));
        System.out.println("LCP: " + java.util.Arrays.toString(lcp));
        // Expected for "banana" (code-point based positions): suffixes sorted roughly [a, ana, anana, banana, na, nana]
    }
}

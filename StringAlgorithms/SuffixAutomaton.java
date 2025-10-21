package StringAlgorithms;

import java.util.*;

/**
 * Suffix Automaton (SAM)
 * ----------------------
 * - Compact automaton recognizing all substrings of a string S.
 * - Supports: substring existence queries, number of distinct substrings, and LCS between two strings.
 * - Construction in O(|S| * alphabetFactor) using sparse transitions (HashMap per state) for Unicode safety.
 */
public class SuffixAutomaton {

    static class State {
        int len;                 // length of the longest string in this state
        int link = -1;           // suffix link
        Map<Integer, Integer> next = new HashMap<>(); // transitions by code point
        long occ = 0;            // occurrence count (endpos size), can be propagated if needed
    }

    private final List<State> st = new ArrayList<>();
    private int last; // index of the state for the whole string

    public SuffixAutomaton() {
        st.add(new State());
        last = 0;
    }

    /** Extend SAM with one code point. */
    public void extend(int c) {
        int cur = st.size();
        State curState = new State();
        curState.len = st.get(last).len + 1;
        curState.occ = 1;
        st.add(curState);

        int p = last;
        while (p != -1 && !st.get(p).next.containsKey(c)) {
            st.get(p).next.put(c, cur);
            p = st.get(p).link;
        }
        if (p == -1) {
            curState.link = 0;
        } else {
            int q = st.get(p).next.get(c);
            if (st.get(p).len + 1 == st.get(q).len) {
                curState.link = q;
            } else {
                int clone = st.size();
                State cloneState = new State();
                cloneState.len = st.get(p).len + 1;
                cloneState.next.putAll(st.get(q).next);
                cloneState.link = st.get(q).link;
                cloneState.occ = 0; // cloned state doesn't represent an end occurrence initially
                st.add(cloneState);

                while (p != -1 && st.get(p).next.getOrDefault(c, -1) == q) {
                    st.get(p).next.put(c, clone);
                    p = st.get(p).link;
                }
                st.get(q).link = clone;
                curState.link = clone;
            }
        }
        last = cur;
    }

    /** Build SAM from a string (Unicode-safe via code points). */
    public static SuffixAutomaton build(String s) {
        SuffixAutomaton sam = new SuffixAutomaton();
        for (int i = 0; i < s.length(); ) {
            int cp = s.codePointAt(i);
            sam.extend(cp);
            i += Character.charCount(cp);
        }
        return sam;
    }

    /** Check if pattern is a substring of the built string. */
    public boolean containsSubstring(String pat) {
        int v = 0;
        for (int i = 0; i < pat.length(); ) {
            int cp = pat.codePointAt(i);
            Integer to = st.get(v).next.get(cp);
            if (to == null) return false;
            v = to;
            i += Character.charCount(cp);
        }
        return true;
    }

    /** Number of distinct substrings = sum over states (len[v] - len[link[v]]). */
    public long countDistinctSubstrings() {
        long ans = 0;
        for (int v = 1; v < st.size(); v++) { // skip root
            ans += st.get(v).len - st.get(st.get(v).link).len;
        }
        return ans;
    }

    /** Longest Common Substring length between the SAM's string and t. */
    public int longestCommonSubstringLength(String t) {
        int v = 0, l = 0, best = 0;
        for (int i = 0; i < t.length(); ) {
            int cp = t.codePointAt(i);
            while (v != 0 && !st.get(v).next.containsKey(cp)) {
                v = st.get(v).link;
                l = st.get(v).len;
            }
            if (st.get(v).next.containsKey(cp)) {
                v = st.get(v).next.get(cp);
                l++;
                best = Math.max(best, l);
            } else {
                // at root with no transition
                l = 0;
            }
            i += Character.charCount(cp);
        }
        return best;
    }

    // Demo
    public static void main(String[] args) {
        String s = args != null && args.length > 0 ? args[0] : "abracadabra";
        SuffixAutomaton sam = SuffixAutomaton.build(s);

        System.out.println("String: " + s);
        System.out.println("States: " + sam.st.size());
        System.out.println("Distinct substrings: " + sam.countDistinctSubstrings());

        String q = args != null && args.length > 1 ? args[1] : "cad";
        System.out.println("Contains '" + q + "'? " + sam.containsSubstring(q));

        String t = args != null && args.length > 2 ? args[2] : "bananarama";
        System.out.println("LCS length with '" + t + "': " + sam.longestCommonSubstringLength(t));
    }
}

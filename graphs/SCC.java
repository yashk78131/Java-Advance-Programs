/**
 * Tarjan’s Algorithm - Strongly Connected Components (SCC)
 * --------------------------------------------------------
 * Finds all SCCs in a directed graph using DFS.
 */

import java.util.*;

public class TarjanSCC {
    static int time = 0;

    public static void dfs(int u, List<List<Integer>> adj, int[] disc, int[] low, Stack<Integer> st, boolean[] inStack) {
        disc[u] = low[u] = ++time;
        st.push(u);
        inStack[u] = true;

        for (int v : adj.get(u)) {
            if (disc[v] == -1) {
                dfs(v, adj, disc, low, st, inStack);
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            System.out.print("SCC: ");
            while (true) {
                int v = st.pop();
                inStack[v] = false;
                System.out.print(v + " ");
                if (v == u) break;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int V = 5;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        adj.get(1).add(0);
        adj.get(0).add(2);
        adj.get(2).add(1);
        adj.get(0).add(3);
        adj.get(3).add(4);

        int[] disc = new int[V];
        int[] low = new int[V];
        boolean[] inStack = new boolean[V];
        Arrays.fill(disc, -1);
        Stack<Integer> st = new Stack<>();

        for (int i = 0; i < V; i++)
            if (disc[i] == -1)
                dfs(i, adj, disc, low, st, inStack);
    }
}

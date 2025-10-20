import java.util.*;

class Optimized_BellmanFord_Algorithm {

    static int[] bellman_ford(int V, ArrayList<ArrayList<Integer>> edges, int S) {
        int[] dist = new int[V];

        // Step 1: Start by assuming every vertex is unreachable (infinity)
        for (int i = 0; i < V; i++) dist[i] = (int)(1e8);
        dist[S] = 0; // but the distance to the source itself is obviously 0

        // Step 2: Relax edges up to V-1 times
        // (because the longest shortest path in a graph can have at most V-1 edges)
        for (int i = 0; i < V - 1; i++) {
            boolean updated = false; // flag to check if any distance changed in this round

            for (ArrayList<Integer> it : edges) {
                int u = it.get(0);
                int v = it.get(1);
                int wt = it.get(2);

                // If we already know a path to 'u' and going through 'u' gives a shorter route to 'v'...
                if (dist[u] != 1e8 && dist[u] + wt < dist[v]) {
                    dist[v] = dist[u] + wt;
                    updated = true; // yep, something got shorter here
                }
            }

            // If in this entire pass, nothing changed — that means all shortest paths are found
            if (!updated) {
                // No point in looping more, we’re done early 🙂
                break;
            }
        }

        // Step 3: One last check to catch negative weight cycles
        for (ArrayList<Integer> it : edges) {
            int u = it.get(0);
            int v = it.get(1);
            int wt = it.get(2);

            // If we can still make a distance smaller → that's a negative cycle
            if (dist[u] != 1e8 && dist[u] + wt < dist[v]) {
                return new int[]{-1};
            }
        }

        // Step 4: If we reach here, we’ve got our shortest distances
        return dist;
    }

    public static void main(String ar[]) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        int V = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int E = sc.nextInt();

        ArrayList<ArrayList<Integer>> edges = new ArrayList<>();

        System.out.println("Enter each edge as: <source> <destination> <weight>");
        for (int i = 0; i < E; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            edges.add(new ArrayList<>(Arrays.asList(u, v, w)));
        }

        System.out.print("Enter source vertex: ");
        int S = sc.nextInt();

        int[] dist = bellman_ford(V, edges, S);

        if (dist.length == 1 && dist[0] == -1) {
            System.out.println("Oops! The graph has a negative weight cycle.");
        } else {
            System.out.println("\nShortest distances from source vertex " + S + ":");
            for (int i = 0; i < V; i++) {
                if (dist[i] == (int)(1e8))
                    System.out.println("Vertex " + i + " : unreachable");
                else
                    System.out.println("Vertex " + i + " : " + dist[i]);
            }
        }

        sc.close();
    }
}

import java.util.*;

public class Dijkstra {

    public int[] dijkstra_implementation(ArrayList<ArrayList<int[]>> adj, int src, int n) {
        int[] result = new int[n];
        Arrays.fill(result, Integer.MAX_VALUE);
        result[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[] {0, src});

        while (!pq.isEmpty()) {
            int[] popped = pq.poll();
            int cost = popped[0];
            int node = popped[1];

            if (cost > result[node])
                continue;

            for (int[] neighbor : adj.get(node)) {
                int nextNode = neighbor[0];
                int edgeWeight = neighbor[1];
                int nextCost = cost + edgeWeight;

                if (nextCost < result[nextNode]) {
                    result[nextNode] = nextCost;
                    pq.offer(new int[] {nextCost, nextNode});
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Dynamic input
        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();
        System.out.print("Enter number of edges: ");
        int m = sc.nextInt();

        ArrayList<ArrayList<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        System.out.println("Enter edges in format: from to weight");
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int weight = sc.nextInt();
            adj.get(u).add(new int[]{v, weight});
            // If the graph is undirected, also add:
            adj.get(v).add(new int[]{u, weight});
        }

        System.out.print("Enter source node: ");
        int src = sc.nextInt();

        Dijkstra obj = new Dijkstra();
        int[] shortestPaths = obj.dijkstra_implementation(adj, src, n);

        System.out.println("Shortest distances from source " + src + ":");
        for (int i = 0; i < n; i++) {
            System.out.print("Node " + i + ": ");
            if (shortestPaths[i] == Integer.MAX_VALUE) {
                System.out.println("Unreachable");
            } else {
                System.out.println(shortestPaths[i]);
            }
        }
    }
}

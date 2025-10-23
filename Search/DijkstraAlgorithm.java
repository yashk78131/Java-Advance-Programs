import java.util.*;

public class DijkstraAlgorithm {

    static class Edge {
        int dest, weight;
        Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }

    private Map<Integer, List<Edge>> graph = new HashMap<>();

    public void addEdge(int src, int dest, int weight) {
        graph.computeIfAbsent(src, k -> new ArrayList<>()).add(new Edge(dest, weight));
        graph.computeIfAbsent(dest, k -> new ArrayList<>()).add(new Edge(src, weight));
    }

    public void dijkstra(int start) {
        Map<Integer, Integer> dist = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        for (int node : graph.keySet()) {
            dist.put(node, Integer.MAX_VALUE);
        }
        dist.put(start, 0);
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0];
            int currDist = curr[1];

            for (Edge edge : graph.getOrDefault(node, new ArrayList<>())) {
                int newDist = currDist + edge.weight;
                if (newDist < dist.get(edge.dest)) {
                    dist.put(edge.dest, newDist);
                    pq.offer(new int[]{edge.dest, newDist});
                }
            }
        }

        System.out.println("Shortest distances from node " + start + ":");
        for (var entry : dist.entrySet()) {
            System.out.println("To " + entry.getKey() + " = " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        DijkstraAlgorithm g = new DijkstraAlgorithm();
        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 1);
        g.addEdge(2, 1, 2);
        g.addEdge(1, 3, 1);
        g.addEdge(2, 3, 5);

        g.dijkstra(0);
    }
}

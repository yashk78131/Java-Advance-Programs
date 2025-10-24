import java.util.*;

public class DijkstraAlgorithm {
    private static final int INF = Integer.MAX_VALUE;

    public static void dijkstra(int[][] graph, int startVertex) {
        int vertexCount = graph.length;
        int[] distances = new int[vertexCount];
        boolean[] visited = new boolean[vertexCount];
        Arrays.fill(distances, INF);
        distances[startVertex] = 0;

        for (int i = 0; i < vertexCount - 1; i++) {
            int u = selectMinVertex(distances, visited);
            visited[u] = true;

            for (int v = 0; v < vertexCount; v++) {
                if (graph[u][v] != 0 && !visited[v] && distances[u] != INF) {
                    int newDist = distances[u] + graph[u][v];
                    if (newDist < distances[v]) {
                        distances[v] = newDist;
                    }
                }
            }
        }

        printSolution(distances);
    }

    private static int selectMinVertex(int[] distances, boolean[] visited) {
        int minVertex = -1;
        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && (minVertex == -1 || distances[i] < distances[minVertex])) {
                minVertex = i;
            }
        }
        return minVertex;
    }

    private static void printSolution(int[] distances) {
        System.out.println("Vertex \t Distance from Source");
        for (int i = 0; i < distances.length; i++) {
            System.out.println(i + " \t " + distances[i]);
        }
    }

    public static void main(String[] args) {
        int[][] graph = {
            {0, 7, 9, 0, 0, 0},
            {7, 0, 10, 15, 0, 0},
            {9, 10, 0, 11, 0, 0},
            {0, 15, 11, 0, 6, 0},
            {0, 0, 0, 6, 0, 9},
            {0, 0, 0, 0, 9, 0}
        };
        dijkstra(graph, 0);
    }
}

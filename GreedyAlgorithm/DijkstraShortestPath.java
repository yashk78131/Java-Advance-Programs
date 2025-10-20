// Dijkstra's Shortest Path Algorithm (Greedy Approach)
// Author: Ishwari Shinde
// Description: Implementation of Dijkstra's Algorithm using PriorityQueue
// for finding shortest paths from a source vertex to all other vertices in a graph.

import java.util.*;

class DijkstraShortestPath {

    // Inner class to represent edges in the graph
    static class Edge {
        int destination, weight;
        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    // Inner class to represent nodes in the priority queue
    static class Node implements Comparable<Node> {
        int vertex, distance;
        Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public int compareTo(Node other) {
            return this.distance - other.distance;
        }
    }

    // Function to perform Dijkstra's algorithm
    public static void dijkstra(List<List<Edge>> graph, int source) {
        int V = graph.size(); // number of vertices
        int[] dist = new int[V]; // stores shortest distance
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // Min-Heap Priority Queue
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;

            // Explore all adjacent edges
            for (Edge edge : graph.get(u)) {
                int v = edge.destination;
                int weight = edge.weight;

                // Relaxation step (Greedy choice)
                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }

        // Print final shortest distances
        printShortestDistances(dist, source);
    }

    // Helper function to print the result
    private static void printShortestDistances(int[] dist, int source) {
        System.out.println("Vertex\tShortest Distance from Source " + source);
        for (int i = 0; i < dist.length; i++) {
            System.out.println(i + "\t\t" + dist[i]);
        }
    }

    // Main function to test the algorithm
    public static void main(String[] args) {
        int V = 5; // Number of vertices
        List<List<Edge>> graph = new ArrayList<>();

        // Initialize adjacency list
        for (int i = 0; i < V; i++)
            graph.add(new ArrayList<>());

        // Add edges: graph.get(source).add(new Edge(destination, weight));
        graph.get(0).add(new Edge(1, 9));
        graph.get(0).add(new Edge(2, 6));
        graph.get(0).add(new Edge(3, 5));
        graph.get(0).add(new Edge(4, 3));
        graph.get(2).add(new Edge(1, 2));
        graph.get(2).add(new Edge(3, 4));

        int source = 0;
        dijkstra(graph, source);
    }
}

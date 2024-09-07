package org.example.transfersv6.delme;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

    private static List<Node> nodes = new ArrayList<>();
    public static void main(String[] args) {
        int dijkstra = dijkstra(0, 7);
//        System.out.println(dijkstra);
    }

    private static int dijkstra(int src, int target) {
        Node srcNode = nodes.get(src);
        srcNode.distance = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(srcNode);




        return -1;
    }

    public static class Node implements Comparable<Node> {
        private final int id;
        private final List<Edge> adjacentNodes = new ArrayList<>();
        private boolean visited;
        private int distance = Integer.MAX_VALUE;

        public Node(int id) {
            this.id = id;
        }

        public void visit() {
            this.visited = true;
        }

        public void addEdge(Edge edge) {
            this.adjacentNodes.add(edge);
        }

        @Override
        public int compareTo(Node o) {
            return this.distance - o.distance;
        }
    }

    public static class Edge {
        private final int d;
        private final Node node;

        public Edge(int d, Node node) {
            this.d = d;
            this.node = node;
        }
    }
}

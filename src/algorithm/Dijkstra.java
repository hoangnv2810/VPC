package algorithm;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dijkstra {
    private static int[] dist;
    private static int[] path;
    private static ArrayList<ArrayList<Node>> graph;

    public static void Dijkstra(int s) {
        int n = graph.size();
        dist = new int[n];
        path = new int[n];

        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            path[i] = -1;
        }

        dist[s] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(s, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int u = cur.to;
            for (int i = 0; i < graph.get(u).size(); i++) {
                Node edge = graph.get(u).get(i);
                int v = edge.to;
                int newDist = cur.dist + edge.dist;
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    pq.add(new Node(v, dist[v]));
                    path[v] = u;
                }
            }
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int s = 0, t = 4;

        graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            for (int j = 0; j < n; j++) {
                int d = sc.nextInt();
                if (d > 0) {
                    graph.get(i).add(new Node(j, d));
                }
            }
        }

        Dijkstra(s);

        System.out.println(dist[t]);
    }


    static class Node implements Comparable<Node> {
        public Integer to;
        public Integer dist;

        public Node(Integer to, Integer dist) {
            this.to = to;
            this.dist = dist;
        }


        @Override
        public int compareTo(Node o) {
            return this.dist.compareTo(o.dist);
        }
    }
}



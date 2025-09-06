package vpc_2025_sample;

import java.io.*;
import java.util.*;

public class C {

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { this.in = is; }
        private int read() throws IOException {
            if (ptr >= len) { len = in.read(buffer); ptr = 0; if (len <= 0) return -1; }
            return buffer[ptr++];
        }
        int nextInt() throws IOException {
            int c, sgn = 1, x = 0;
            do { c = read(); } while (c <= 32);
            if (c == '-') { sgn = -1; c = read(); }
            while (c > 32) { x = x * 10 + (c - '0'); c = read(); }
            return x * sgn;
        }
    }


    static class Node implements Comparable<Node> {
        int to;
        long noise;
        long latency;
        Node(int to, long noise, long latency) {
            this.to = to; this.noise = noise; this.latency = latency;
        }
        @Override
        public int compareTo(Node o) {
            if (this.noise != o.noise) return Long.compare(this.noise, o.noise);
            return Long.compare(this.latency, o.latency);
        }
    }

    static ArrayList<ArrayList<Node>> graph;
    static long[] bestNoise;     // max noise tốt nhất tới mỗi đỉnh
    static long[] bestLat;    // tổng latency tốt nhất tương ứng

    static void dijkstra(int s, int target) {
        int n = graph.size();

        bestNoise = new long[n + 1];
        bestLat = new long[n + 1];
        Arrays.fill(bestNoise, Long.MAX_VALUE);
        Arrays.fill(bestLat, Long.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        bestNoise[s] = 0;
        bestLat[s] = 0L;
        pq.add(new Node(s, 0, 0L));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int u = cur.to;
            if (cur.noise != bestNoise[u] || cur.latency != bestLat[u]) continue; // lazy skip
            if (u == target) break;

            for (int i = 0; i < graph.get(u).size(); i++) {
                Node edge = graph.get(u).get(i);
                int v = edge.to;
                long newNoise = Math.max(cur.noise, edge.noise);
                long newLat = cur.latency + edge.latency;

                if (newNoise < bestNoise[v] || (newNoise == bestNoise[v] && newLat < bestLat[v])) {
                    bestNoise[v] = newNoise;
                    bestLat[v] = newLat;
                    pq.add(new Node(v, newNoise, newLat));
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        graph = new ArrayList<>(n);
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            int a = sc.nextInt(); // noise
            int d = sc.nextInt(); // latency
            graph.get(u).add(new Node(v, a, d));
            graph.get(v).add(new Node(u, a, d));
        }

        dijkstra(1, n);
        System.out.println(bestNoise[n] + " " + bestLat[n]);
    }
}

package vpc_2025_sample;

import java.io.*;
import java.util.*;

public class C_OTM {
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            this.in = is;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c, sgn = 1, x = 0;
            do {
                c = read();
            } while (c <= 32);
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            while (c > 32) {
                x = x * 10 + (c - '0');
                c = read();
            }
            return x * sgn;
        }

        long nextLong() throws IOException {
            int c;
            long sgn = 1, x = 0;
            do {
                c = read();
            } while (c <= 32);
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            while (c > 32) {
                x = x * 10 + (c - '0');
                c = read();
            }
            return x * sgn;
        }
    }

    // -------- Edge + DSU ----------
    static class Edge {
        int u, v;
        long noise, latency;  // noise, latency
    }

    static class DSU {
        int[] p, r;

        DSU(int n) {
            p = new int[n + 1];
            r = new int[n + 1];
            for (int i = 0; i <= n; i++) p[i] = i;
        }

        int find(int x) {
            while (p[x] != x) {
                p[x] = p[p[x]];
                x = p[x];
            }
            return x;
        }

        boolean unite(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) return false;
            if (r[a] < r[b]) {
                int t = a;
                a = b;
                b = t;
            }
            p[b] = a;
            if (r[a] == r[b]) r[a]++;
            return true;
        }
    }

    static class Node implements Comparable<Node> {
        int v;
        long dist;

        Node(int v, long d) {
            this.v = v;
            this.dist = d;
        }

        public int compareTo(Node o) {
            return Long.compare(this.dist, o.dist);
        }
    }

    static final long INF = 1L << 62;

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        Edge[] E = new Edge[m];
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            long a = sc.nextLong(), d = sc.nextLong();
            Edge e = new Edge();
            e.u = u;
            e.v = v;
            e.noise = a;
            e.latency = d;
            E[i] = e;
        }

        // --- B1: tìm K = minimax noise bằng DSU (Kruskal dừng sớm) ---
        Edge[] S = E.clone();
        Arrays.sort(S, (x, y) -> Long.compare(x.noise, y.noise));
        DSU dsu = new DSU(n);
        long K = 0;
        for (Edge e : S) {
            dsu.unite(e.u, e.v);
            if (dsu.find(1) == dsu.find(n)) {
                K = e.noise;
                break;
            }
        }

        // --- B2: xây đồ thị con chỉ với a <= K bằng mảng liên kết ---
        int cnt = 0;
        for (Edge e : E) if (e.noise <= K) cnt++;
        int m2 = cnt * 2;

        int[] head = new int[n + 1];
        Arrays.fill(head, -1);
        int[] to = new int[m2];
        int[] next = new int[m2];
        long[] w = new long[m2];
        int idx = 0;

        for (Edge e : E)
            if (e.noise <= K) {
                to[idx] = e.v;
                w[idx] = e.latency;
                next[idx] = head[e.u];
                head[e.u] = idx++;
                to[idx] = e.u;
                w[idx] = e.latency;
                next[idx] = head[e.v];
                head[e.v] = idx++;
            }

        // --- Dijkstra theo latency ---
        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        PriorityQueue<Node> pq = new PriorityQueue<>();
        dist[1] = 0;
        pq.add(new Node(1, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int u = cur.v;
            if (cur.dist != dist[u]) continue;
            if (u == n) break;
            for (int i = head[u]; i != -1; i = next[i]) {
                int v = to[i];
                long nd = cur.dist + w[i];
                if (nd < dist[v]) {
                    dist[v] = nd;
                    pq.add(new Node(v, nd));
                }
            }
        }

        System.out.println(K + " " + dist[n]);
    }
}


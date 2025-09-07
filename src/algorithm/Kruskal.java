package algorithm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Kruskal {

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
            int c, sgn = 1;
            long x = 0;
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

    static class DSU {
        int[] parent, size;

        DSU(int n) {
            parent = new int[n + 1];
            size = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            while (parent[x] != x) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }

        boolean union(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) return false;
            if (size[a] < size[b]) {
                int temp = a;
                a = b;
                b = temp;
            }
            parent[b] = a;
            size[a] += size[b];
            return true;
        }
    }

    static void kruskal(Edge[] edges, DSU dsu, int n) {
        long d = 0;
        ArrayList<Edge> mst = new ArrayList<>();

        for (Edge e : edges) {
            if (mst.size() == n-1) break;
            if (dsu.union(e.u, e.v)) {
                mst.add(e);
                d += e.w;
            }
        }

        if (mst.size() != n - 1) {
            // không liên thông → không có MST
            System.out.println("NO MST");
        } else {
            System.out.println("MST: " + d);
            for (Edge e: mst) {
                System.out.println(e.u + " " + e.v + " " + e.w);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();
        int m = fs.nextInt();

        Edge[] edges = new Edge[m];
        for (int i = 0; i < m; i++) {
            int u = fs.nextInt(), v = fs.nextInt();
            long w = fs.nextLong();
            edges[i] = new Edge(u, v, w);
        }

        Arrays.sort(edges, Comparator.comparingLong(e -> e.w));

        DSU dsu = new DSU(n);

        kruskal(edges, dsu, n);

    }

    static class Edge {
        int u, v;
        long w;

        Edge(int u, int v, long w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }
}

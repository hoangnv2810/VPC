package vnoi.dsu;

import java.io.IOException;
import java.io.InputStream;

public class TroConBo {
    static final class FastScanner {
        private final InputStream in;
        private final byte[] buf = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) { len = in.read(buf); ptr = 0; if (len <= 0) return -1; }
            return buf[ptr++];
        }
        long nextLong() throws IOException {
            int c; do { c = read(); } while (c <= 32);
            long sgn = 1; if (c=='-') { sgn = -1; c = read(); }
            long x = 0;
            while (c > 32) { x = x*10 + (c - '0'); c = read(); }
            return x * sgn;
        }
        int nextInt() throws IOException { return (int) nextLong(); }
        String next() throws IOException {
            int c; do { c = read(); } while (c <= 32);
            StringBuilder sb = new StringBuilder();
            while (c > 32) { sb.append((char)c); c = read(); }
            return sb.toString();
        }
    }

    static int[] parent;
    static int[] size;
    static long[] add, diff;

    public static int findHalving(int x) {
        if (parent[x] == x) return x;
        int p = parent[x];
        int r = findHalving(p);
        diff[x] += diff[p];
        return parent[x] = r;
    }

    static void union(int u, int v) {
        int a = findHalving(u), b = findHalving(v);

        if (a != b) {
            if (size[a] < size[b]) {
                int temp = a;
                a = b;
                b = temp;
            }
            parent[b] = a;
            diff[b] = add[b] - add[a];
            size[a] += size[b];
        }
    }

    static void addTo(int u, long val) {
        add[findHalving(u)] += val;
    }

    static long get(int u) {
        return add[findHalving(u)] + diff[u];
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);

        int n = fs.nextInt();
        int k = fs.nextInt();

        parent = new int[n+1];
        size = new int[n+1];
        add = new long[n+1];
        diff = new long[n+1];

        for (int i = 1; i < n+1; i++) {
            parent[i] = i;
            size[i] = 1;
            add[i] = diff[i] = 0;
        }

        for (int i = 0; i < k; i++) {
            String cmd = fs.next();
            if (cmd.equals("join")) {
                int a = fs.nextInt();
                int b = fs.nextInt();
                union(a, b);
            } else if (cmd.equals("add")) {
                int a = fs.nextInt();
                int b = fs.nextInt();
                addTo(a, b);
            } else if (cmd.equals("get")) {
                int c = fs.nextInt();
                System.out.println(get(c));
            }
        }
    }
}

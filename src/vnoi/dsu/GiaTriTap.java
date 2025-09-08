package vnoi.dsu;

import java.io.*;

public class GiaTriTap {

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
    static int[] min;
    static int[] max;

    public static int findHalving(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x; // root
    }

    public  static void union(int a, int b) {
        a = findHalving(a);
        b = findHalving(b);

        if (a != b) {
            if (size[a] < size[b]) {
                int temp = a;
                a = b;
                b = temp;
            }
            parent[b] = a;
            size[a] += size[b];
            max[a] = Math.max(max[a], max[b]);
            min[a] = Math.min(min[a], min[b]);
        }
    }

    static int findMax(int a){
        a = findHalving(a);
        return max[a];
    }

    static int findMin(int a){
        a = findHalving(a);
        return min[a];
    }

    static int findLienThong(int a) {
        a = findHalving(a);
        return size[a];
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);

        int n = fs.nextInt();
        int m = fs.nextInt();

        parent = new int[n+1];
        size = new int[n+1];
        min = new int[n+1];
        max = new int[n+1];

        for (int i = 1; i < n+1; i++) {
            parent[i] = i;
            size[i] = 1;
            max[i] = min[i] = i;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < m; i++) {
            String cmd = fs.next();
            if (cmd.equals("union")) {
                int a = fs.nextInt();
                int b = fs.nextInt();
                union(a, b);
            } else if (cmd.equals("get")) {
                int c = fs.nextInt();
                System.out.println(findMin(c) + " " + findMax(c) + " " + findLienThong(c));
            }
        }

    }

}

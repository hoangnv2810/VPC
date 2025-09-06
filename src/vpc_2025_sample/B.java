package vpc_2025_sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class B {
    static int[] parent;

    static int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    // Fast IO
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
    }

    public static void main(String[] args) throws IOException {
        FastScanner sc = new FastScanner(System.in);

        int n = sc.nextInt();
        int[] f = new int[n+1];
        int[] l = new int[n+1];
        parent = new int[n+2];
        for (int i = 1; i <= n; i++) {
            f[i] = sc.nextInt();
        }

        for (int i = 1; i <= n; i++) {
            l[i] = sc.nextInt();
        }

        int q = sc.nextInt();

        for (int i = 1; i <= n+1; i++) {
            parent[i] = i;
        }

        for (int i = 1; i <= n; i++) if (f[i] >= l[i]) parent[i] = i + 1;

        for (int i = 0; i < q; i++) {
            int b = sc.nextInt();
            int a = sc.nextInt();

            int j = find(b);
            while (a > 0 && j <= n) {
                int free = l[j] - f[j];
                if (free > 0) {
                    int pour = Math.min(a, free);
                    f[j] += pour;
                    a -= pour;
                }
                if (f[j] == l[j]) parent[j] = find(j+1);

                j = find(j);
            }
        }

        for (int i = 1; i <= n; i++) {
            System.out.print(f[i] + " ");
        }
    }
}

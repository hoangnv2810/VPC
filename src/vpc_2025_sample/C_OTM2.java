package vpc_2025_sample;

import java.io.*;
import java.util.*;

public class C_OTM2 {

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
            return sgn * x;
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
            return sgn * x;
        }
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
        } // path halving

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

    static void sortByKey(int[] idx, long[] key) {
        int n = idx.length;
        int[] stL = new int[64], stR = new int[64];
        int top = 0;
        stL[top] = 0;
        stR[top] = n - 1;

        while (top >= 0) {
            int L = stL[top], R = stR[top--];
            while (L < R) {
                long pivot = key[idx[(L + R) >>> 1]];
                int i = L, j = R;
                while (i <= j) {
                    while (key[idx[i]] < pivot) i++;
                    while (key[idx[j]] > pivot) j--;
                    if (i <= j) {
                        int t = idx[i];
                        idx[i] = idx[j];
                        idx[j] = t;
                        i++;
                        j--;
                    }
                }
                // tail recursion elimination: process smaller first
                if (j - L < R - i) {
                    if (L < j) {
                        ++top;
                        stL[top] = L;
                        stR[top] = j;
                    }
                    L = i;
                } else {
                    if (i < R) {
                        ++top;
                        stL[top] = i;
                        stR[top] = R;
                    }
                    R = j;
                }
            }
        }
    }

    static class MinHeap {
        long[] key;
        int[] val;
        int sz = 0;

        MinHeap(int cap) {
            key = new long[cap + 5];
            val = new int[cap + 5];
        }

        boolean isEmpty() {
            return sz == 0;
        }

        void push(long k, int v) {
            int i = ++sz;
            while (i > 1) {
                int p = i >> 1;
                if (key[p] <= k) break;
                key[i] = key[p];
                val[i] = val[p];
                i = p;
            }
            key[i] = k;
            val[i] = v;
        }

        long topKey() {
            return key[1];
        }

        int topVal() {
            return val[1];
        }

        void pop() {
            long k = key[sz];
            int v = val[sz];
            sz--;
            int i = 1;
            while (true) {
                int l = i << 1, r = l + 1;
                if (l > sz) break;
                int c = (r <= sz && key[r] < key[l]) ? r : l;
                if (key[c] >= k) break;
                key[i] = key[c];
                val[i] = val[c];
                i = c;
            }
            key[i] = k;
            val[i] = v;
        }
    }

    static final long INF = 1L << 62;

    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        int[] Eu = new int[m];
        int[] Ev = new int[m];
        long[] Ea = new long[m];   // noise
        long[] Ed = new long[m];   // latency

        for (int i = 0; i < m; i++) {
            Eu[i] = sc.nextInt();
            Ev[i] = sc.nextInt();
            Ea[i] = sc.nextLong();
            Ed[i] = sc.nextLong();
        }

        int[] idx = new int[m];
        for (int i = 0; i < m; i++) idx[i] = i;
        sortByKey(idx, Ea); // sort indices by Ea[]

        DSU dsu = new DSU(n);
        long K = 0;
        for (int id : idx) {
            dsu.unite(Eu[id], Ev[id]);
            if (dsu.find(1) == dsu.find(n)) {
                K = Ea[id];
                break;
            }
        }

        int cnt = 0;
        for (int i = 0; i < m; i++) if (Ea[i] <= K) cnt++;
        int m2 = cnt << 1;

        int[] head = new int[n + 1];
        Arrays.fill(head, -1);
        int[] to = new int[m2];
        int[] next = new int[m2];
        long[] w = new long[m2];
        int ptr = 0;

        for (int i = 0; i < m; i++)
            if (Ea[i] <= K) {
                int u = Eu[i], v = Ev[i];
                long ww = Ed[i];

                to[ptr] = v;
                w[ptr] = ww;
                next[ptr] = head[u];
                head[u] = ptr++;
                to[ptr] = u;
                w[ptr] = ww;
                next[ptr] = head[v];
                head[v] = ptr++;
            }

        long[] dist = new long[n + 1];
        Arrays.fill(dist, INF);
        MinHeap pq = new MinHeap(m2 + n + 5);

        dist[1] = 0;
        pq.push(0L, 1);

        while (!pq.isEmpty()) {
            long du = pq.topKey();
            int u = pq.topVal();
            pq.pop();
            if (du != dist[u]) continue;
            if (u == n) break;

            for (int i = head[u]; i != -1; i = next[i]) {
                int v = to[i];
                long nd = du + w[i];
                if (nd < dist[v]) {
                    dist[v] = nd;
                    pq.push(nd, v);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(K).append(' ').append(dist[n]).append('\n');
        System.out.print(sb.toString());
    }
}


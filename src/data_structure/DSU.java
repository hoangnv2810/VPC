package data_structure;

public class DSU {
    static int[] parent = new int[1001];
    static int[] size = new int[1001];
    public static void makeSet( int n) {
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public static int findCompression(int v) {
        if (v == parent[v]) return v;
        return parent[v] = findCompression(parent[v]); // nén đường
    }

    public static int findHalving(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];  // gán cha của x = ông (grandparent)
            x = parent[x];                  // nhảy lên ông -> độ dài đường đi giảm ~ một nửa
        }
        return x; // root
    }

    public static void union(int a, int b) {
        a = findCompression(a);
        b = findCompression(b);
        if (a != b) {
            if (size[a] < size[b]) {
                parent[a] = b;
                size[b] += size[a];
            } else {
                parent[b] = a;
                size[a] += size[b];
            }
        };
    }
}

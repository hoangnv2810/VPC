package vpc_2024;

import java.util.*;

public class B {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int len = n;
        Map<Integer, Integer> next = new HashMap<>();
        Map<Integer, Integer> mapAll = new HashMap<>();
        Integer[] res = new Integer[n];
        while (n-- > 0) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            next.put(a, b);
            if (a == 0) res[1] = b;

            List<Integer> list = new ArrayList<>();
            list.add(a);
            list.add(b);

            for (Integer i: list) {
                if (mapAll.containsKey(i)) {
                    Integer cnt = mapAll.get(i) + 1;
                    mapAll.put(i, cnt);
                } else {
                    mapAll.put(i, 1);
                }
            }
        }

        for (Integer key: mapAll.keySet()) {
            if (mapAll.get(key) == 1 && next.containsKey(key)) {
                res[0] = key;
            }
        }

        for (int i = 2; i < len; i++) {
            res[i] = next.get(res[i-2]);
        }

        for (Integer i: res) System.out.print(i + " ");
    }
}

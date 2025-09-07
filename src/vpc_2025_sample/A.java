package vpc_2025_sample;

import java.io.*;
import java.util.*;

public class A {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int v1 = sc.nextInt();
        int c1 = sc.nextInt();

        int v2 = sc.nextInt();
        int c2 = sc.nextInt();

        int n = sc.nextInt();
        int cntC1 = 0;

        for (int i = 0; i < n; i++) {
            int a = sc.nextInt();
            if (a > Math.max(v1, v2)) {
                System.out.println("-1");
                return;
            }
            if (a <= v2) cntC1++;
        }

        if (cntC1 % 2 == 1) cntC1 = cntC1 - 1;

        int gia2 = cntC1 * c2;
        int gia1 = (n - cntC1) * c1;

        System.out.println(gia1 + gia2);
    }
}


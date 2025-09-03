package vpc_2024;

import java.util.Scanner;

public class A {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int startX = 0, startY = 0, startT = 0;
        boolean result = true;
        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int t = sc.nextInt();

            int distance = Math.abs(x - startX) + Math.abs(y - startY);
            int time = t - startT;
            if (distance > time || distance % time != 0) {
                result = false;
            }

            startX = x;
            startY = y;
            startT = t;
        }
        if (result) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}

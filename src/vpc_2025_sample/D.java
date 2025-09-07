package vpc_2025_sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class D {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(in.readLine().trim());

        while (T-- > 0) {
            int L = 1, R = 100;
            while (L < R) {
                int mid = (L + R + 1)/2;
                System.out.println("? " + mid);
                System.out.flush();

                String ans = in.readLine();
                if (ans == null) return;

                if (ans.equals(">=")) {
                    L = mid;
                } else if (ans.equals("<")) {
                    R = mid - 1;
                } else {
                    return;
                }
            }
            System.out.println("! " + L);
            System.out.flush();
        }
    }
}

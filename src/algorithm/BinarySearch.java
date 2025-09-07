package algorithm;

import java.util.Scanner;

public class BinarySearch {

    public static int binarySearch(int a[], int left, int right, int x) {
        while (left <= right) {
            int mid = (left + right)/2;
            if (x == a[mid]) {
                return mid;
            } else if (x < a[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int x = sc.nextInt();
        int a[] = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        System.out.println(binarySearch(a, 0, n-1, x));
    }
}

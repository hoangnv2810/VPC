package algorithm;

import vpc_2024.A;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class DFS {
    private static ArrayList<ArrayList<Integer>> graph;
    private static int V;
    private static int E;
    private static ArrayList<Integer> path;
    private static ArrayList<Boolean> visited;

    private static void DFS(int s) {
        Stack<Integer> stack = new Stack<>();
        visited.set(s, true);
        stack.add(s);
        
        while (!stack.isEmpty()) {
            int u = stack.pop();
            for (int i = 0; i < graph.get(u).size(); i++) {
                int v = graph.get(u).get(i);
                if (!visited.get(v)) {
                    visited.set(v, true);
                    path.set(v, u);
                    stack.add(u);
                }
            }
        }
    }

    private static void printPath(int s, int u) {
        if (s == u) {
            System.out.println(s);
            return;
        }

        if (path.get(u) == -1) {
            System.out.println("No path");
            return;
        }

        ArrayList<Integer> b = new ArrayList<>();
        while (u != -1) {
            b.add(u);
            u = path.get(u);
        }

        for (int i = b.size()-1; i >= 0; i--) {
            System.out.printf("%d ", b.get(i));
        }


    }
    
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        V = sc.nextInt();
        E = sc.nextInt();

        graph = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        path = new ArrayList<>();
        visited = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            visited.add(false);
            path.add(-1);
        }

        int s = 0, f = 5;
        DFS(s);

        printPath(s, f);
    }
}

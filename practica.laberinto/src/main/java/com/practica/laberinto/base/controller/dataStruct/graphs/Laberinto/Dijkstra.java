package com.practica.laberinto.base.controller.dataStruct.graphs.Laberinto;

import com.practica.laberinto.base.controller.dataStruct.queue.Queue;

import com.practica.laberinto.base.controller.dataStruct.list.LinkedList;

public class Dijkstra {
    static final int INF = Integer.MAX_VALUE;

    public static LinkedList<Float> dijkstra(float[][] graph, int source) {
        int n = graph.length;
        LinkedList<Float> distance = new LinkedList<>();
        LinkedList<Boolean> visited = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            distance.add((float) INF);
            visited.add(false);
        }
        distance.update(0f, source);

        Queue<Integer> queue = new Queue<>(n);
        queue.queue(source);

        while (!queue.isFullQueue()) {
            int u = queue.dequeue();
            visited.update(true, u);

            for (int v = 0; v < n; v++) {
                if (!visited.get(v) && graph[u][v] != 0 && distance.get(u) != INF &&
                        distance.get(u) + graph[u][v] < distance.get(v)) {
                    distance.update(distance.get(u) + graph[u][v], v);
                }
            }
        }
        return distance;
        // printSolution(distance);
    }

    /*
     * private static void printSolution(LinkedList<Integer> distance) {
     * System.out.println("Shortest Distances from Source:");
     * for (int i = 0; i < distance.getLength(); i++) {
     * System.out.println("To " + i + ": " + distance.get(i));
     * }
     * }
     * 
     * public static void main(String[] args) {
     * int[][] graph = {
     * { 0, 7, 9, 0, 0, 14 },
     * { 7, 0, 10, 15, 0, 0 },
     * { 9, 10, 0, 11, 0, 2 },
     * { 0, 15, 11, 0, 6, 0 },
     * { 0, 0, 0, 6, 0, 9 },
     * { 14, 0, 2, 0, 9, 0 }
     * };
     * dijkstra(graph, 0);
     * }
     */
}

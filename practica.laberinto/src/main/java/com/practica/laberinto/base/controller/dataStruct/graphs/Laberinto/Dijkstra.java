package com.practica.laberinto.base.controller.dataStruct.graphs.Laberinto;

import com.practica.laberinto.base.controller.dataStruct.list.LinkedList;

public class Dijkstra {
    static final int INF = Integer.MAX_VALUE;

    public static class PathResult {
        public LinkedList<Float> distance;
        public LinkedList<Integer> predecessor;

        public PathResult(LinkedList<Float> distance, LinkedList<Integer> predecessor) {
            this.distance = distance;
            this.predecessor = predecessor;
        }
    }

    public static PathResult dijkstra(float[][] graph, int source) {
        int n = graph.length;
        LinkedList<Float> distance = new LinkedList<>();
        LinkedList<Boolean> visited = new LinkedList<>();
        LinkedList<Integer> predecessor = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            distance.add((float) INF);
            visited.add(false);
            predecessor.add(-1);
        }

        distance.update(0f, source);

        for (int count = 0; count < n - 1; count++) {
            int u = minDistance(distance, visited);
            if (u == -1)
                break;

            visited.update(true, u);

            for (int v = 0; v < n; v++) {
                if (!visited.get(v) && graph[u][v] != 0 &&
                        distance.get(u) != INF &&
                        distance.get(u) + graph[u][v] < distance.get(v)) {
                    distance.update(distance.get(u) + graph[u][v], v);
                    predecessor.update(u, v);
                }
            }
        }
        return new PathResult(distance, predecessor);
    }

    private static int minDistance(LinkedList<Float> distance, LinkedList<Boolean> visited) {
        float min = INF;
        int minIndex = -1;

        for (int v = 0; v < distance.getLength(); v++) {
            if (!visited.get(v) && distance.get(v) <= min) {
                min = distance.get(v);
                minIndex = v;
            }
        }
        return minIndex;
    }
}
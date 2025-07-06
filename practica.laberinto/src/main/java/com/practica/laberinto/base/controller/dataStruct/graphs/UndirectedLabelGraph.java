package com.practica.laberinto.base.controller.dataStruct.graphs;

import com.practica.laberinto.base.controller.dataStruct.list.LinkedList;

public class UndirectedLabelGraph<E> extends DirectLabelGraph<E> {

    public UndirectedLabelGraph(Integer nro_vertex, Class clazz) {
        super(nro_vertex, clazz);
    }

    @Override
    public void insert_label(E o, E d, Float weight) {
        if (isLabelsGraph()) {
            insert(getVertex(o), getVertex(d), weight);
            insert(getVertex(d), getVertex(o), weight);
        } else {
            throw new ArrayIndexOutOfBoundsException("Vertex origin o destiny index out ");
        }
    }

    public void matriz(int row, int column) {
        StringBuilder s = new StringBuilder(column);
        for (int i = 0; i < column; i++) {
            s.append('0');
        }
        char[][] maz = new char[row][column];
        for (int x = 0; x < row; x++) {
            maz[x] = s.toString().toCharArray();
        }
    }

    public static UndirectedLabelGraph<String> mazeToGraph(char[][] maze) {
        int rows = maze.length;
        int cols = maze[0].length;
        int count = 0;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (maze[r][c] != '0')
                    count++;

        UndirectedLabelGraph<String> graph = new UndirectedLabelGraph<>(count, String.class);
        String[] labels = new String[count + 1];
        int[][] index = new int[rows][cols];
        int idx = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] != '0') {
                    String label = i + "," + j;
                    graph.label_vertex(idx, label);
                    labels[idx] = label;
                    index[i][j] = idx;
                    idx++;
                }
            }
        }

        int[] dx = { 0, 1, 0, -1 };
        int[] dy = { 1, 0, -1, 0 };
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] != '0') {
                    int fromIdx = index[i][j];
                    String fromLabel = labels[fromIdx];
                    for (int d = 0; d < 4; d++) {
                        int ni = i + dx[d], nj = j + dy[d];
                        if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && maze[ni][nj] != '0') {
                            int toIdx = index[ni][nj];
                            String toLabel = labels[toIdx];
                            if (fromIdx < toIdx)
                                graph.insert_label(fromLabel, toLabel, 1.0f);
                        }
                    }
                }
            }
        }
        return graph;
    }

    public float[][] getMatrizAdyacencia() {
        int n = nro_vertex();
        float[][] matriz = new float[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                matriz[i][j] = (i == j) ? 0 : -1;
            }
        }
        for (int i = 1; i <= n; i++) {
            LinkedList<Adjacency> adjs = adjacencies(i);
            int len = adjs.getLength();
            for (int k = 0; k < len; k++) {
                Adjacency adj = adjs.get(k);
                int destino = adj.getDestiny();
                float peso = adj.getWeight();
                matriz[i][destino] = peso;
            }
        }
        return matriz;
    }

}

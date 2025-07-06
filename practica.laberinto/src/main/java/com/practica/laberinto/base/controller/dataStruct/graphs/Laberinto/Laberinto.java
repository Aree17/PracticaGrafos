package com.practica.laberinto.base.controller.dataStruct.graphs.Laberinto;

import java.util.ArrayList;

import com.practica.laberinto.base.controller.dataStruct.graphs.UndirectedLabelGraph;

public class Laberinto {
    public char[][] Laberinto(int r, int c) {
        r = 100;
        c = 100;

        StringBuilder s = new StringBuilder();
        for (int x = 0; x < c; x++) {
            s.append('0');
        }
        char[][] maz = new char[r][c];
        for (int x = 0; x < r; x++) {
            maz[x] = s.toString().toCharArray();
        }
        Point st = new Point((int) (Math.random() * r), (int) (Math.random() * c), null);
        maz[st.r][st.c] = 'S';

        ArrayList<Point> frontier = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0) {
                    continue;
                }
                try {
                    if (maz[st.r + x][st.c + y] == '1') {
                        continue;
                    }
                } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                    continue;
                }
                // add eligible points to frontier
                frontier.add(new Point(st.r + x, st.c + y, st));
            }
        }
        Point last = null;
        while (!frontier.isEmpty()) {

            // pick current node at random
            Point cu = frontier.remove((int) (Math.random() * frontier.size()));
            Point op = cu.opposite();
            try {
                // if both node and its opposite are walls
                if (maz[cu.r][cu.c] == '0') {
                    if (maz[op.r][op.c] == '0') {

                        // open path between the nodes
                        maz[cu.r][cu.c] = '1';
                        maz[op.r][op.c] = '1';

                        // store last node in order to mark it later
                        last = op;

                        // iterate through direct neighbors of node, same as earlier
                        for (int x = -1; x <= 1; x++) {
                            for (int y = -1; y <= 1; y++) {
                                if (x == 0 && y == 0 || x != 0 && y != 0) {
                                    continue;
                                }
                                try {
                                    if (maz[op.r + x][op.c + y] == '1') {
                                        continue;
                                    }
                                } catch (Exception e) {
                                    continue;
                                }
                                frontier.add(new Point(op.r + x, op.c + y, op));
                            }
                        }
                    }
                }
            } catch (Exception e) { // ignore NullPointer and ArrayIndexOutOfBounds
            }

            // if algorithm has resolved, mark end node
            if (frontier.isEmpty()) {
                maz[last.r][last.c] = 'E';
            }
        }

        s = new StringBuilder();
        for (int i = 0; i < r; i++) {
            String aux = "";
            for (int j = 0; j < c; j++) {
                // System.out.print(maz[i][j]);
                aux += maz[i][j] + ",";
            }
            aux = aux.substring(0, aux.length() - 1);
            s.append(aux).append("\n");
        }

        return maz;
    }

    static class Point {

        Integer r;
        Integer c;
        Point parent;

        public Point(int x, int y, Point p) {
            r = x;
            c = y;
            parent = p;
        }

        public Point opposite() {
            if (this.r.compareTo(parent.r) != 0) {
                return new Point(this.r + this.r.compareTo(parent.r), this.c, this);
            }
            if (this.c.compareTo(parent.c) != 0) {
                return new Point(this.r, this.c + this.c.compareTo(parent.c), this);
            }
            return null;
        }
    }

    public static void imprimirLaberinto(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    // corrida
    public static void main(String[] args) {
        Laberinto p = new Laberinto();
        char[][] matriz = p.Laberinto(20, 20);
        imprimirLaberinto(matriz);
        UndirectedLabelGraph<String> grafo = UndirectedLabelGraph.mazeToGraph(matriz);
        float[][] matrizAdy = grafo.getMatrizAdyacencia();
        Dijkstra.dijkstra(matrizAdy, 0);
        // O la etiqueta para el fin

        // System.out.println(grafo.toString());

        /*
         * System.out.println("matriz de adyacencia");
         * for (int i = 0; i < matrizAdy.length; i++) {
         * for (int j = 0; j < matrizAdy[0].length; j++) {
         * System.out.print(matrizAdy[i][j] + " ");
         * }
         * System.out.println();
         * }
         */

    }
}

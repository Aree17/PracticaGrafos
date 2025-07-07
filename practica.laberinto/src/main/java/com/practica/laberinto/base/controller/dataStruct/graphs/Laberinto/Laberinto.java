package com.practica.laberinto.base.controller.dataStruct.graphs.Laberinto;

import java.util.ArrayList;

import com.practica.laberinto.base.controller.dataStruct.graphs.UndirectedLabelGraph;

public class Laberinto {

    public String generar(int r, int c) {
        // dimensions of generated maze
        // int r = 100, c = 100;

        // build maze and initialize with only walls
        StringBuilder s = new StringBuilder(c);
        for (int x = 0; x < c; x++) {
            s.append('0');
        }
        char[][] maz = new char[r][c];
        for (int x = 0; x < r; x++) {
            maz[x] = s.toString().toCharArray();
        }

        // select random point and open as start node
        Point st = new Point((int) (Math.random() * r), (int) (Math.random() * c), null);
        maz[st.r][st.c] = 'S';

        // iterate through direct neighbors of node
        ArrayList<Point> frontier = new ArrayList<Point>();
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

        return s.toString();
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
        // compute opposite node given that it is in the other direction from the parent

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

    public String resolverLaberinto(String laberintoString) {
        String[] lineas = laberintoString.trim().split("\n");
        int r = lineas.length;
        int c = lineas[0].split(",").length;

        char[][] maz = new char[r][c];
        for (int i = 0; i < r; i++) {
            String[] celdas = lineas[i].split(",");
            for (int j = 0; j < c; j++) {
                maz[i][j] = celdas[j].charAt(0);
            }
        }

        int startR = -1, startC = -1, endR = -1, endC = -1;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (maz[i][j] == 'S') {
                    startR = i;
                    startC = j;
                }
                if (maz[i][j] == 'E') {
                    endR = i;
                    endC = j;
                }
            }
        }

        if (startR == -1 || endR == -1) {
            return "Error: No se encontraron puntos de inicio (S) o fin (E) en el laberinto";
        }

        UndirectedLabelGraph<String> graph = UndirectedLabelGraph.mazeToGraph(maz);

        String startLabel = startR + "," + startC;
        String endLabel = endR + "," + endC;
        Integer startIdx = graph.getVertex(startLabel);
        Integer endIdx = graph.getVertex(endLabel);

        if (startIdx == null || endIdx == null) {
            return "Error: No se pudieron encontrar los nodos de inicio o fin en el grafo";
        }

        float[][] matriz = graph.getMatrizAdyacencia();
        Dijkstra.PathResult resultado = Dijkstra.dijkstra(matriz, startIdx);

        if (resultado.distance.get(endIdx) == Float.POSITIVE_INFINITY) {
            return "Error: No existe camino entre el inicio y el fin";
        }

        java.util.ArrayList<Integer> camino = new java.util.ArrayList<>();
        int actual = endIdx;

        while (actual != -1) {
            camino.add(0, actual);
            if (actual == startIdx)
                break;
            actual = resultado.predecessor.get(actual);
        }

        for (Integer idx : camino) {
            String label = graph.getLabel(idx);
            if (label != null) {
                String[] partes = label.split(",");
                int i = Integer.parseInt(partes[0]);
                int j = Integer.parseInt(partes[1]);
                if (maz[i][j] != 'S' && maz[i][j] != 'E') {
                    maz[i][j] = '*';
                }
            }
        }

        StringBuilder resultado_final = new StringBuilder();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                resultado_final.append(maz[i][j]);
                if (j < c - 1) {
                    resultado_final.append(",");
                }
            }
            resultado_final.append("\n");
        }

        return resultado_final.toString();
    }

    // corrida
    public static void main(String[] args) {
        Laberinto p = new Laberinto();

        System.out.println("=== Generando laberinto ===");
        String laberintoGenerado = p.generar(20, 20);
        System.out.println(laberintoGenerado);

        System.out.println("=== Resolviendo laberinto con Dijkstra ===");
        String laberintoResuelto = p.resolverLaberinto(laberintoGenerado);
        System.out.println(laberintoResuelto);

        System.out.println("=================");
        System.out.println("S = Inicio");
        System.out.println("E = Fin");
        System.out.println("* = Camino más corto encontrado por Dijkstra");
        System.out.println("1 = Camino disponible");
        System.out.println("0 = Pared");

    }

}

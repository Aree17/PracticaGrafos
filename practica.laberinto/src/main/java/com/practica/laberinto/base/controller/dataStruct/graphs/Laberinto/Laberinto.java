package com.practica.laberinto.base.controller.dataStruct.graphs.Laberinto;

import com.practica.laberinto.base.controller.dataStruct.graphs.UndirectedLabelGraph;
import com.practica.laberinto.base.controller.dataStruct.list.LinkedList;

public class Laberinto {

    public String generar(int r, int c) throws Exception {

        // construir laberinto e inicializar con paredes
        StringBuilder s = new StringBuilder(c);
        for (int x = 0; x < c; x++) {
            s.append('0');
        }
        char[][] maz = new char[r][c];
        for (int x = 0; x < r; x++) {
            maz[x] = s.toString().toCharArray();
        }

        // seleccionar un punto de inicio aleatorio y escogerlo como nodo inicial
        Point st = new Point((int) (Math.random() * r), (int) (Math.random() * c), null);
        maz[st.r][st.c] = 'S';

        // iterar a los vecinos cercanos al nodo inicial
        LinkedList<Point> frontier = new LinkedList<Point>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0) {
                    continue;
                }
                int newR = st.r + x;
                int newC = st.c + y;
                if (newR >= 0 && newR < r && newC >= 0 && newC < c) { // Validación de límites
                    if (maz[newR][newC] == '1') {
                        continue;
                    }
                    frontier.add(new Point(newR, newC, st));
                }
            }
        }

        Point last = null;
        while (!frontier.isEmpty()) {

            // escoger un nodo aleatorio de la frontera
            Point cu = frontier.delete((int) (Math.random() * frontier.getLength()));
            Point op = cu.opposite();
            try {
                // si el nodo y su opuesto son paredes
                if (cu.r >= 0 && cu.r < r && cu.c >= 0 && cu.c < c && // Validación de límites
                    op.r >= 0 && op.r < r && op.c >= 0 && op.c < c && // Validación de límites
                    maz[cu.r][cu.c] == '0' && maz[op.r][op.c] == '0') {

                    // abrir camino entre ambos nodos
                    maz[cu.r][cu.c] = '1';
                    maz[op.r][op.c] = '1';

                    // almacenar el nodo como último visitado
                    last = op;

                    // volver a iterar con los vecinos del nodo actual
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            if (x == 0 && y == 0 || x != 0 && y != 0) {
                                continue;
                            }
                            int newR = op.r + x;
                            int newC = op.c + y;
                            if (newR >= 0 && newR < r && newC >= 0 && newC < c) { // Validación de límites
                                if (maz[newR][newC] == '1') {
                                    continue;
                                }
                                frontier.add(new Point(newR, newC, op));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error al generar el laberinto: " + e.getMessage());
            }

            // si el algoritmo se resuelve, se marca el último nodo como final
            if (frontier.isEmpty() && last != null) {
                maz[last.r][last.c] = 'E';
            }
        }

        s = new StringBuilder();
        for (int i = 0; i < r; i++) {
            String aux = "";
            for (int j = 0; j < c; j++) {
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

        // Calcula el nodo opuesto dado que está en la otra dirección del padre
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

    public static void main(String[] args) {
        try {
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

        } catch (Exception e) {
            System.out.println("Error durante la resoluciòn del laberinto: " + e.getMessage());
        }
        
    }

}

package com.practica.laberinto.base.controller.dataStruct.graphs.Laberinto;

import com.practica.laberinto.base.controller.dataStruct.graphs.UndirectedLabelGraph;
import com.practica.laberinto.base.controller.dataStruct.list.LinkedList;

public class Laberinto {

    public String generar(int r, int c) throws Exception {
        return generarInterno(r, c);
    }

    private String generarInterno(int r, int c) throws Exception {
        char[][] maz = new char[r][c];

        // Inicializar laberinto con paredes
        for (int x = 0; x < r; x++) {
            for (int y = 0; y < c; y++) {
                maz[x][y] = '0';
            }
        }

        // Seleccionar punto de inicio aleatorio
        Point st = new Point((int) (Math.random() * r), (int) (Math.random() * c), null);
        maz[st.r][st.c] = 'S';

        // Mantener una lista de todos los caminos generados
        LinkedList<Point> caminosGenerados = new LinkedList<>();
        caminosGenerados.add(st);

        // Iterar a los vecinos cercanos al nodo inicial
        LinkedList<Point> frontier = new LinkedList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0) {
                    continue;
                }
                int newR = st.r + x;
                int newC = st.c + y;
                if (newR >= 0 && newR < r && newC >= 0 && newC < c) {
                    if (maz[newR][newC] == '1') {
                        continue;
                    }
                    frontier.add(new Point(newR, newC, st));
                }
            }
        }

        while (!frontier.isEmpty()) {
            Point cu = frontier.delete((int) (Math.random() * frontier.getLength()));
            Point op = cu.opposite();
            try {
                if (cu.r >= 0 && cu.r < r && cu.c >= 0 && cu.c < c &&
                        op.r >= 0 && op.r < r && op.c >= 0 && op.c < c &&
                        maz[cu.r][cu.c] == '0' && maz[op.r][op.c] == '0') {

                    maz[cu.r][cu.c] = '1';
                    maz[op.r][op.c] = '1';

                    caminosGenerados.add(new Point(cu.r, cu.c, null));
                    caminosGenerados.add(new Point(op.r, op.c, null));

                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            if (x == 0 && y == 0 || x != 0 && y != 0) {
                                continue;
                            }
                            int newR = op.r + x;
                            int newC = op.c + y;
                            if (newR >= 0 && newR < r && newC >= 0 && newC < c) {
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
        }

        Point puntoFinal = null;
        int intentos = 0;
        while (puntoFinal == null && intentos < 10) {
            int indiceAleatorio = (int) (Math.random() * caminosGenerados.getLength());
            Point candidato = caminosGenerados.get(indiceAleatorio);

            // Asegurar que no sea el punto de inicio
            if (candidato.r != st.r || candidato.c != st.c) {
                puntoFinal = candidato;
            }
            intentos++;
        }


        if (puntoFinal == null) {
            int maxDistancia = 0;
            for (int i = 0; i < caminosGenerados.getLength(); i++) {
                Point p = caminosGenerados.get(i);
                if (p.r != st.r || p.c != st.c) {
                    int distancia = Math.abs(p.r - st.r) + Math.abs(p.c - st.c);
                    if (distancia > maxDistancia) {
                        maxDistancia = distancia;
                        puntoFinal = p;
                    }
                }
            }
        }


        maz[puntoFinal.r][puntoFinal.c] = 'E';

        // Construir el string resultado
        StringBuilder s = new StringBuilder();
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

    public String resolverLaberinto(String laberintoString) throws Exception {
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
            return "No se encontraron puntos de inicio o fin en el laberinto";
        }
    

        maz[startR][startC] = '1';
        maz[endR][endC] = '1';
    
        UndirectedLabelGraph<String> graph = UndirectedLabelGraph.mazeToGraph(maz);
    
        String startLabel = startR + "," + startC;
        String endLabel = endR + "," + endC;
        Integer startIdx = graph.getVertex(startLabel);
        Integer endIdx = graph.getVertex(endLabel);
    
        if (startIdx == null || endIdx == null) {
            return "No se pudieron encontrar los nodos de inicio o fin en el grafo";
        }
    
        float[][] matriz = graph.getMatrizAdyacencia();
        Dijkstra.PathResult resultado = Dijkstra.dijkstra(matriz, startIdx);
    
        if (resultado.distance.get(endIdx) == Float.POSITIVE_INFINITY) {
            return "No existe camino entre el inicio y el fin";
        }
    
        LinkedList<Integer> camino = new LinkedList<>();
        int actual = endIdx;
        while (actual != -1) {
            camino.add(actual);
            if (actual == startIdx)
                break;
            if (actual >= resultado.predecessor.getLength())
                break;
            actual = resultado.predecessor.get(actual);
        }
    
        LinkedList<Integer> caminoInvertido = new LinkedList<>();
        for (int i = camino.getLength() - 1; i >= 0; i--) {
            caminoInvertido.add(camino.get(i));
        }
        camino = caminoInvertido;
    
        for (int i = 0; i < camino.getLength(); i++) {
            Integer idx = camino.get(i);
            String label = graph.getLabel(idx);
            if (label != null) {
                String[] partes = label.split(",");
                int fila = Integer.parseInt(partes[0]);
                int columna = Integer.parseInt(partes[1]);
                if (maz[fila][columna] != 'S' && maz[fila][columna] != 'E') {
                    maz[fila][columna] = '*';
                }
            }
        }

        maz[startR][startC] = 'S';
        maz[endR][endC] = 'E';
    
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
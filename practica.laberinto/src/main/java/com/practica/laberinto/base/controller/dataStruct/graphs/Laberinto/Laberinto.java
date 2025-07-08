package com.practica.laberinto.base.controller.dataStruct.graphs.Laberinto;

import com.practica.laberinto.base.controller.dataStruct.graphs.UndirectedLabelGraph;
import com.practica.laberinto.base.controller.dataStruct.list.LinkedList;

public class Laberinto {

    public String generar(int r, int c) throws Exception {
<<<<<<< HEAD
        return generarInterno(r, c);
    }

    private String generarInterno(int r, int c) throws Exception {
        char[][] laberinto = new char[r][c];
=======
        char[][] maz = new char[r][c];
>>>>>>> origin/develop

        // Inicializar laberinto con paredes
        for (int x = 0; x < r; x++) {
            for (int y = 0; y < c; y++) {
<<<<<<< HEAD
                laberinto[x][y] = '0';
=======
                maz[x][y] = '0';
>>>>>>> origin/develop
            }
        }

        // Seleccionar punto de inicio aleatorio
        Point st = new Point((int) (Math.random() * r), (int) (Math.random() * c), null);
        laberinto[st.r][st.c] = 'S';

<<<<<<< HEAD

        LinkedList<Point> caminosGenerados = new LinkedList<>();
        caminosGenerados.add(st);


        LinkedList<Point> frontier = new LinkedList<>();
=======
        // Mantener una lista de todos los caminos generados
        LinkedList<Point> caminosGenerados = new LinkedList<>();
        caminosGenerados.add(st);

        // Iterar a los vecinos cercanos al nodo inicial
        LinkedList<Point> frontier = new LinkedList<Point>();
>>>>>>> origin/develop
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0) {
                    continue;
                }
                int newR = st.r + x;
                int newC = st.c + y;
                if (newR >= 0 && newR < r && newC >= 0 && newC < c) {
<<<<<<< HEAD
                    if (laberinto[newR][newC] == '1') {
=======
                    if (maz[newR][newC] == '1') {
>>>>>>> origin/develop
                        continue;
                    }
                    frontier.add(new Point(newR, newC, st));
                }
            }
        }

        while (!frontier.isEmpty()) {
            Point cu = frontier.delete((int) (Math.random() * frontier.getLength()));
            Point op = cu.opuesto();
            try {
                if (cu.r >= 0 && cu.r < r && cu.c >= 0 && cu.c < c &&
                        op.r >= 0 && op.r < r && op.c >= 0 && op.c < c &&
<<<<<<< HEAD
                        laberinto[cu.r][cu.c] == '0' && laberinto[op.r][op.c] == '0') {

                    laberinto[cu.r][cu.c] = '1';
                    laberinto[op.r][op.c] = '1';

=======
                        maz[cu.r][cu.c] == '0' && maz[op.r][op.c] == '0') {

                    maz[cu.r][cu.c] = '1';
                    maz[op.r][op.c] = '1';

                    // Agregar los puntos nuevos a la lista de caminos generados
>>>>>>> origin/develop
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
<<<<<<< HEAD
                                if (laberinto[newR][newC] == '1') {
=======
                                if (maz[newR][newC] == '1') {
>>>>>>> origin/develop
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

<<<<<<< HEAD
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
=======
        // Garantizar conectividad: Si no hay caminos generados suficientes, forzar
        // conexión
        if (caminosGenerados.getLength() < 2) {
            // Crear un camino simple desde S hacia abajo y derecha
            Point inicio = caminosGenerados.get(0);
            for (int i = inicio.r + 1; i < r && i < inicio.r + 3; i++) {
                if (i < r) {
                    maz[i][inicio.c] = '1';
                    caminosGenerados.add(new Point(i, inicio.c, null));
                }
            }
            for (int j = inicio.c + 1; j < c && j < inicio.c + 3; j++) {
                if (j < c) {
                    maz[inicio.r][j] = '1';
                    caminosGenerados.add(new Point(inicio.r, j, null));
>>>>>>> origin/develop
                }
            }
        }

<<<<<<< HEAD
        // Marcar el punto final
        laberinto[puntoFinal.r][puntoFinal.c] = 'E';
=======
        // Seleccionar punto final desde los caminos generados (excluyendo el inicio)
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

        // Si no se encontró un punto final válido, crear uno
        if (puntoFinal == null) {
            // Buscar el punto más alejado del inicio
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

        // Si aún no hay punto final, crear uno forzado
        if (puntoFinal == null) {
            // Crear un punto final en la esquina opuesta si es posible
            int finalR = r - 1;
            int finalC = c - 1;

            // Asegurar que haya un camino hasta el punto final
            maz[finalR][finalC] = '1';
            if (finalR > 0)
                maz[finalR - 1][finalC] = '1';
            if (finalC > 0)
                maz[finalR][finalC - 1] = '1';

            puntoFinal = new Point(finalR, finalC, null);
        }

        // Marcar el punto final
        maz[puntoFinal.r][puntoFinal.c] = 'E';
>>>>>>> origin/develop

        // Construir el string resultado
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < r; i++) {
            String aux = "";
            for (int j = 0; j < c; j++) {
                aux += laberinto[i][j] + ",";
            }
            aux = aux.substring(0, aux.length() - 1);
            s.append(aux).append("\n");
        }

        return s.toString();
    }

    static class Point {

        Integer r;
        Integer c;
        Point padre;

        public Point(int x, int y, Point p) {
            r = x;
            c = y;
            padre = p;
        }

<<<<<<< HEAD
        public Point opuesto() {
            if (this.r.compareTo(padre.r) != 0) {
                return new Point(this.r + this.r.compareTo(padre.r), this.c, this);
=======
        public Point opposite() {
            if (this.r.compareTo(parent.r) != 0) {
                return new Point(this.r + this.r.compareTo(parent.r), this.c, this);
>>>>>>> origin/develop
            }
            if (this.c.compareTo(padre.c) != 0) {
                return new Point(this.r, this.c + this.c.compareTo(padre.c), this);
            }
            return null;
        }
    }

    public String resolverLaberinto(String laberintoString) throws Exception {
        String[] lineas = laberintoString.trim().split("\n");
        int r = lineas.length;
        int c = lineas[0].split(",").length;
    
        char[][] laberinto = new char[r][c];
        for (int i = 0; i < r; i++) {
            String[] celdas = lineas[i].split(",");
            for (int j = 0; j < c; j++) {
                laberinto[i][j] = celdas[j].charAt(0);
            }
        }
    
        int startR = -1, startC = -1, endR = -1, endC = -1;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (laberinto[i][j] == 'S') {
                    startR = i;
                    startC = j;
                }
                if (laberinto[i][j] == 'E') {
                    endR = i;
                    endC = j;
                }
            }
        }
    
        if (startR == -1 || endR == -1) {
            return "No se encontraron puntos de inicio en el laberinto";
        }
    
        laberinto[startR][startC] = '1';
        laberinto[endR][endC] = '1';
    
        UndirectedLabelGraph<String> graph = UndirectedLabelGraph.mazeToGraph(laberinto);
    
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
            return "No existe camino posible entre el inicio y el fin";
        }
<<<<<<< HEAD
    
=======

>>>>>>> origin/develop
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
<<<<<<< HEAD
    
        LinkedList<Integer> caminoInvertido = new LinkedList<>();
        for (int i = camino.getLength() - 1; i >= 0; i--) {
            caminoInvertido.add(camino.get(i));
        }
        camino = caminoInvertido;
    
=======

>>>>>>> origin/develop
        for (int i = 0; i < camino.getLength(); i++) {
            Integer idx = camino.get(i);
            String label = graph.getLabel(idx);
            if (label != null) {
                String[] partes = label.split(",");
                int fila = Integer.parseInt(partes[0]);
                int columna = Integer.parseInt(partes[1]);
<<<<<<< HEAD
                if (laberinto[fila][columna] != 'S' && laberinto[fila][columna] != 'E') {
                    laberinto[fila][columna] = '*';
=======
                if (maz[fila][columna] != 'S' && maz[fila][columna] != 'E') {
                    maz[fila][columna] = '*';
>>>>>>> origin/develop
                }
            }
        }

        laberinto[startR][startC] = 'S';
        laberinto[endR][endC] = 'E';
    
        StringBuilder resultado_final = new StringBuilder();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                resultado_final.append(laberinto[i][j]);
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
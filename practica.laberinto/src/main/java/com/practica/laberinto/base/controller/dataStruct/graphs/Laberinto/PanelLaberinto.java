package com.practica.laberinto.base.controller.dataStruct.graphs.Laberinto;

import javax.swing.*;
import java.awt.*;

public class PanelLaberinto extends JPanel { 
    private final char[][] laberinto;

    public PanelLaberinto(String StringLaberinto) {
        String[] lines = StringLaberinto.trim().split("\n");
        int row = lines.length;
        int col = lines[0].split(",").length; 

        laberinto = new char[row][col];
        for (int i = 0; i < row; i++) {
            String[] celda = lines[i].split(",");
            for (int j = 0; j < col; j++) {
                laberinto[i][j] = celda[j].charAt(0);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int tamanioCelda = Math.min(getWidth() / laberinto[0].length, getHeight() / laberinto.length);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < laberinto.length; i++) {
            for (int j = 0; j < laberinto[i].length; j++) {
                switch (laberinto[i][j]) {
                    case '0': 
                        g2d.setColor(new Color(0, 0, 128)); 
                        break;
                    case '1': 
                        g2d.setColor(new Color(173, 216, 230)); 
                        break;
                    case 'E': 
                        g2d.setColor(new Color(0, 100, 0)); 
                        break;
                    case 'S': 
                        g2d.setColor(new Color(255, 215, 0)); 
                        break;
                    case '*': 
                        g2d.setColor(new Color(0, 255, 255)); 
                        break;
                    default:
                        g2d.setColor(new Color(240, 255, 240)); 
                        break;
                }

                // Dibujar celdas con bordes redondeados
                int tamanioBorde = tamanioCelda / 4; 
                g2d.fillRoundRect(j * tamanioCelda, i * tamanioCelda, tamanioCelda, tamanioCelda, tamanioBorde, tamanioBorde);

                g2d.setColor(new Color(0, 128, 128)); 
                g2d.drawRoundRect(j * tamanioCelda, i * tamanioCelda, tamanioCelda, tamanioCelda, tamanioBorde, tamanioBorde);

                // Dibujar texto en los nodos de inicio y fin
                if (laberinto[i][j] == 'E' || laberinto[i][j] == 'S') {
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.BOLD, tamanioCelda / 2)); 
                    String text = String.valueOf(laberinto[i][j]);
                    FontMetrics fm = g2d.getFontMetrics();
                    int anchoTexto = fm.stringWidth(text);
                    int alturaTexto = fm.getHeight();
                    g2d.drawString(text, j * tamanioCelda + (tamanioCelda - anchoTexto) / 2, i * tamanioCelda + (tamanioCelda + alturaTexto / 2) / 2);
                }
            }
        }
    }
}
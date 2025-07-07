package com.practica.laberinto.base.controller.dataStruct.graphs.Laberinto;

import javax.swing.*;
import java.awt.*;

public class PanelLaberinto extends JPanel {
    private final char[][] maze;

    public PanelLaberinto(String mazeString) {
        String[] lines = mazeString.trim().split("\n");
        int rows = lines.length;
        int cols = lines[0].split(",").length;

        maze = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            String[] cells = lines[i].split(",");
            for (int j = 0; j < cols; j++) {
                maze[i][j] = cells[j].charAt(0);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellSize = Math.min(getWidth() / maze[0].length, getHeight() / maze.length);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                switch (maze[i][j]) {
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
                int arcSize = cellSize / 4; 
                g2d.fillRoundRect(j * cellSize, i * cellSize, cellSize, cellSize, arcSize, arcSize);

                g2d.setColor(new Color(0, 128, 128)); 
                g2d.drawRoundRect(j * cellSize, i * cellSize, cellSize, cellSize, arcSize, arcSize);

                // Dibujar texto en los nodos de inicio y fin
                if (maze[i][j] == 'E' || maze[i][j] == 'S') {
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.BOLD, cellSize / 2)); 
                    String text = String.valueOf(maze[i][j]);
                    FontMetrics fm = g2d.getFontMetrics();
                    int textWidth = fm.stringWidth(text);
                    int textHeight = fm.getHeight();
                    g2d.drawString(text, j * cellSize + (cellSize - textWidth) / 2, i * cellSize + (cellSize + textHeight / 2) / 2);
                }
            }
        }
    }
}
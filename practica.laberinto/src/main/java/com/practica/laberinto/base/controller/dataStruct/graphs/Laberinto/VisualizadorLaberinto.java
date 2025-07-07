package com.practica.laberinto.base.controller.dataStruct.graphs.Laberinto;
import javax.swing.*;
import java.awt.*;

public class VisualizadorLaberinto {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Laberinto laberinto = new Laberinto();

                // Generar y resolver el laberinto
                String laberintoGenerado = laberinto.generar(20, 20);
                String laberintoResuelto = laberinto.resolverLaberinto(laberintoGenerado);
                System.out.println(laberintoResuelto);


                // Crear la ventana principal
                JFrame frame = new JFrame("Visualizador de Laberinto");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 800);

                // Agregar el panel del laberinto
                PanelLaberinto mazePanel = new PanelLaberinto(laberintoResuelto);
                frame.add(mazePanel, BorderLayout.CENTER);

                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al generar o resolver el laberinto: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}


package com.practica.laberinto.base.controller.dataStruct.graphs.Laberinto;

import javax.swing.*;
import java.awt.*;

public class VisualizadorLaberinto {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Crear ventana para seleccionar dimensiones
                JFrame inputFrame = new JFrame("Seleccionar Dimensiones del Laberinto");
                inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                inputFrame.setSize(300, 200);
                inputFrame.setLayout(new GridBagLayout()); 

                inputFrame.getContentPane().setBackground(new Color(173, 216, 230)); 
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JLabel rowsLabel = new JLabel("Filas:");
                rowsLabel.setForeground(new Color(0, 0, 128)); 
                gbc.gridx = 0;
                gbc.gridy = 0;
                inputFrame.add(rowsLabel, gbc);

                JTextField rowsField = new JTextField();
                rowsField.setBackground(new Color(240, 255, 240)); 
                rowsField.setForeground(Color.BLACK); 
                gbc.gridx = 1;
                gbc.gridy = 0;
                inputFrame.add(rowsField, gbc);

                JLabel colsLabel = new JLabel("Columnas:");
                colsLabel.setForeground(new Color(0, 0, 128)); 
                gbc.gridx = 0;
                gbc.gridy = 1;
                inputFrame.add(colsLabel, gbc);

                JTextField colsField = new JTextField();
                colsField.setBackground(new Color(240, 255, 240)); 
                colsField.setForeground(Color.BLACK); 
                gbc.gridx = 1;
                gbc.gridy = 1;
                inputFrame.add(colsField, gbc);

                JButton generateButton = new JButton("Generar Laberinto");
                generateButton.setBackground(new Color(0, 255, 255)); 
                generateButton.setForeground(Color.BLACK); 
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 2; 
                inputFrame.add(generateButton, gbc);

                inputFrame.setLocationRelativeTo(null);
                inputFrame.setVisible(true);

                generateButton.addActionListener(e -> {
                    try {
                        int rows = Integer.parseInt(rowsField.getText());
                        int cols = Integer.parseInt(colsField.getText());

                        if (rows < 30 || rows > 100 || cols < 30 || cols > 100) {
                            throw new NumberFormatException("Las dimensiones deben estar entre 30 y 100.");
                        }

                        // Generar y resolver el laberinto
                        Laberinto laberinto = new Laberinto();
                        String laberintoGenerado = laberinto.generar(rows, cols);
                        String laberintoResuelto = laberinto.resolverLaberinto(laberintoGenerado);
                        System.out.println(laberintoResuelto);


                        JFrame frame = new JFrame("Visualizador de Laberinto");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setSize(800, 800);


                        PanelLaberinto mazePanel = new PanelLaberinto(laberintoResuelto);
                        frame.add(mazePanel, BorderLayout.CENTER);

                        frame.setVisible(true);
                        inputFrame.dispose(); 
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(inputFrame, "Por favor ingrese dimensiones válidas.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(inputFrame, "Error al generar o resolver el laberinto: " + ex.getMessage());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
            }
        });
    }
}


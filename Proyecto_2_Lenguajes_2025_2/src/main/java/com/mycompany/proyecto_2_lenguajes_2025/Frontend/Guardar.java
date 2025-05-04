/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.Frontend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

/**
 *
 * @author herson
 */
public class Guardar {
    
    private File archivoGuardado;
    
    public void guardarArchivo(JTextPane textPane) {
        if (archivoGuardado != null) {
            escribirArchivo(archivoGuardado, textPane.getText());
        } else {
             guardarComoArchivo(textPane);
        }
    }
    
    public void guardarComoArchivo(JTextPane textPane) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Archivo Como");
        
        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            archivoGuardado = fileChooser.getSelectedFile();
            if (!archivoGuardado.getAbsolutePath().endsWith(".txt")) {
                archivoGuardado = new File(archivoGuardado.getAbsolutePath()+ ".txt");
            }
            escribirArchivo(archivoGuardado, textPane.getText());
        }
    }
    
    private void escribirArchivo(File archivo, String contenido) {
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(contenido);
            JOptionPane.showMessageDialog(null, "Archivo guardado correctamente en: " + archivo.getCanonicalPath(),
                    "Exito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

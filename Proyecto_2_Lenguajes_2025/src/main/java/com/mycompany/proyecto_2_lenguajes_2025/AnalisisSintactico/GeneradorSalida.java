/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico;

import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author herson
 */
public class GeneradorSalida {
    
    private final List<Token> tokens;
    private final Map<String, String> tablaSimbolos;
    
    public GeneradorSalida(List<Token> tokens, Map<String, String> tablaSimbolos) {
        this.tokens = tokens;
        this.tablaSimbolos = tablaSimbolos;
    }
    
    public void generarArchivo() {
        //Solicitar la ruta de salida
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione donde guardar el archivo de salida");
        
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            
            //archivo .txt
            if (!rutaArchivo.endsWith(".txt")) {
                rutaArchivo += ".txt";
            }
            
            //escribir el archivo
            try (FileWriter writer = new FileWriter(rutaArchivo)) {
                for (int i = 0; i < tokens.size(); i++) {
                    Token tokenActual = tokens.get(i);
                    
                    //verificar si es print
                    if (tokenActual.getValor().equals("PRINT") && (i + 1) < tokens.size()) {
                        Token contenidoPrint = tokens.get(i + 1);
                        
                        //extraer el contenido segun el tipo
                        String contenido = extraerContenidoPrint(contenidoPrint);
                        if (contenido != null) {
                            writer.write(contenido + "\n");
                        }
                    }
                }
                
                JOptionPane.showMessageDialog(null, "Archivo generado correctamente en: " + rutaArchivo,
                        "Exito" , JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al escribir el archivo: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private String extraerContenidoPrint(Token token) {
        switch (token.getTipo()) {
            case "LITERAL":
                return token.getValor().replace("\"", "");
            case "NUMERO_ENTERO":
                return token.getValor();
            case "IDENTIFICADOR":
                return tablaSimbolos.getOrDefault(token.getValor(), "0");
            default:
                return null;
        }
    }
}

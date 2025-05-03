/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico;

import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
                    
                    // **Manejo de PRINT**
                    if (tokenActual.getValor().equals("PRINT") && (i + 1) < tokens.size()) {
                        Token contenidoPrint = tokens.get(i + 1);
                        String contenido = extraerContenidoPrint(contenidoPrint);
                        if (contenido != null) {
                            writer.write(contenido + "\n");
                        }
                    }

                    // **Manejo de REPEAT**
                    if (tokenActual.getValor().equals("REPEAT") && (i + 1) < tokens.size()) {
                        Token numeroRepeticiones = tokens.get(i + 1);

                        // Verificar que el siguiente token sea un número válido
                        if (numeroRepeticiones.getTipo().equals("NUMERO_ENTERO")) {
                            int repeticiones = Integer.parseInt(numeroRepeticiones.getValor());

                            // Verificar que después del número venga INIT
                            if ((i + 2) < tokens.size() && tokens.get(i + 2).getValor().equals("INIT")) {
                                i += 3; // Avanzamos al primer token dentro de REPEAT

                                List<Token> bloqueRepeat = new ArrayList<>();

                                // Extraer el bloque de instrucciones hasta encontrar END
                                while (i < tokens.size() && !tokens.get(i).getValor().equals("END")) {
                                    bloqueRepeat.add(tokens.get(i));
                                    i++;
                                }

                                // Ejecutar el bloque de REPEAT las veces indicadas
                                for (int j = 0; j < repeticiones; j++) {
                                    for (Token token : bloqueRepeat) {
                                        if (token.getValor().equals("PRINT")) {
                                            Token contenidoPrint = bloqueRepeat.get(bloqueRepeat.indexOf(token) + 1);
                                            String contenido = extraerContenidoPrint(contenidoPrint);
                                            if (contenido != null) {
                                                writer.write(contenido + "\n");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // **Manejo de CONDICIONAL (IF)**
                    if (tokenActual.getValor().equals("IF") && (i + 1) < tokens.size()) {
                        Token condicion = tokens.get(i + 1);

                        // Verificar que la condición es TRUE o FALSE
                        if (condicion.getValor().equals("TRUE") || condicion.getValor().equals("FALSE")) {
                            boolean ejecutarBloque = condicion.getValor().equals("TRUE");

                            // Verificar que después de la condición venga THEN
                            if ((i + 2) < tokens.size() && tokens.get(i + 2).getValor().equals("THEN")) {
                                i += 3; // Avanzamos al primer token dentro de IF

                                List<Token> bloqueIf = new ArrayList<>();

                                // Extraer el bloque de instrucciones hasta encontrar END
                                while (i < tokens.size() && !tokens.get(i).getValor().equals("END")) {
                                    bloqueIf.add(tokens.get(i));
                                    i++;
                                }

                                // **Solo ejecutar el bloque si la condición es TRUE**
                                if (ejecutarBloque) {
                                    for (Token token : bloqueIf) {
                                        if (token.getValor().equals("PRINT")) {
                                            Token contenidoPrint = bloqueIf.get(bloqueIf.indexOf(token) + 1);
                                            String contenido = extraerContenidoPrint(contenidoPrint);
                                            if (contenido != null) {
                                                writer.write(contenido + "\n");
                                            }
                                        }
                                    }
                                }
                            }
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

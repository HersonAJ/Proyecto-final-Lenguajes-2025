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
public class GeneradorSalida3 {

    private final List<Token> tokens;
    private final Map<String, String> tablaSimbolos;

    public GeneradorSalida3(List<Token> tokens, Map<String, String> tablaSimbolos) {
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
                                i += 3; // Avanza al primer token dentro de REPEAT

                                List<List<Token>> instruccionesRepeat = new ArrayList<>();

                                // Extraer instrucciones completas hasta encontrar el token "END" que cierra el REPEAT
                                while (i + 2 < tokens.size() && !tokens.get(i).getValor().equals("END")) {
                                    // Suponiendo que cada instrucción es: PRINT, <argumento>, END
                                    if (tokens.get(i).getValor().equals("PRINT") && tokens.get(i + 2).getValor().equals("END")) {
                                        List<Token> instruccion = new ArrayList<>();
                                        instruccion.add(tokens.get(i));     // PRINT
                                        instruccion.add(tokens.get(i + 1));   // Argumento del PRINT
                                        instruccionesRepeat.add(instruccion);
                                        i += 3;
                                    } else {
                                        // Si el patrón no es el esperado, saltamos el token actual
                                        i++;
                                    }
                                }

                                // Ejecutar el bloque: cada iteración procesa todas las instrucciones extraídas
                                for (int j = 0; j < repeticiones; j++) {
                                    for (List<Token> instruccion : instruccionesRepeat) {
                                        Token argToken = instruccion.get(1);
                                        String contenido = extraerContenidoPrint(argToken);
                                        if (contenido != null) {
                                            writer.write(contenido + "\n");
                                        }
                                    }
                                }

                                // Consumir el END final que cierra el REPEAT
                                if (i < tokens.size() && tokens.get(i).getValor().equals("END")) {
                                    i++;
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
                                i += 3;

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
                        "Exito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al escribir el archivo: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String extraerContenidoPrint(Token token) {
        switch (token.getTipo()) {
            case "LITERAL":
                // Retorna el literal sin comillas
                return token.getValor().replace("\"", "");
            case "NUMERO_ENTERO":
                double numero = Double.parseDouble(token.getValor());
                int entero = (int) numero;
                return String.valueOf(entero);
            case "IDENTIFICADOR":
                String valor = tablaSimbolos.getOrDefault(token.getValor(), "0");
                try {
                    double numeroId = Double.parseDouble(valor);
                    int enteroId = (int) numeroId;
                    return String.valueOf(enteroId);
                } catch (NumberFormatException e) {
                    return valor;
                }
            default:
                return null;
        }
    }
}

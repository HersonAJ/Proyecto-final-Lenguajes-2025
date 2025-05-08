/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico;

import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author herson
 */
public class GeneradorSalida2 {

    private final List<Token> tokens;
    private final Map<String, String> tablaSimbolos;

    public GeneradorSalida2(List<Token> tokens, Map<String, String> tablaSimbolos) {
        this.tokens = tokens;
        this.tablaSimbolos = tablaSimbolos;
    }

    public void generarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione donde guardar el archivo de salida");

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();

            //generar el .txt
            if (!rutaArchivo.endsWith(".txt")) {
                rutaArchivo += ".txt";
            }

            try (FileWriter writer = new FileWriter(rutaArchivo)) {
                //metodos para el manejo
                manejarPrint(writer);
                manejarRepeat(writer);
                manejarCondicional(writer);
                JOptionPane.showMessageDialog(null, "Archivo generado Exitosamente en: " + rutaArchivo,
                        "Exito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al escribir el archivo: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void manejarPrint(FileWriter writer) throws IOException {
        Stack<String> contexto = new Stack<>();

        for (int i = 0; i < tokens.size(); i++) {
            Token tokenActual = tokens.get(i);
            String valor = tokenActual.getValor();

            // Manejar entrada a bloques
            if (valor.equals("IF") || valor.equals("REPEAT")) {
                contexto.push(valor);
            }

            // Manejar salida de bloques
            if (valor.equals("END")) {
                if (!contexto.isEmpty()) {
                    contexto.pop();
                }
            }

            // Verificar si esta dentro de un IF. no procesa PRINT
            if (valor.equals("PRINT") && (i + 1) < tokens.size()) {
                if (contexto.contains("IF")) {
                    i += 1;
                    continue;
                }

                // Esta fuera de un IF, procesa el PRINT
                Token contenidoPrint = tokens.get(i + 1);
                String contenido = extraerContenidoPrint(contenidoPrint);
                if (contenido != null) {
                    writer.write(contenido + "\n");
                }
                i++; // Saltar contenido del PRINT
            }
        }
    }

    private void manejarRepeat(FileWriter writer) throws IOException {
        for (int i = 0; i < tokens.size(); i++) {
            Token tokenActual = tokens.get(i);

            if (tokenActual.getValor().equals("REPEAT") && (i + 1) < tokens.size()) {
                Token numeroRepeticiones = tokens.get(i + 1);
                int repeticiones = 0;

                // Obtener número de repeticiones
                if (numeroRepeticiones.getTipo().equals("NUMERO_ENTERO")) {
                    repeticiones = Integer.parseInt(numeroRepeticiones.getValor());
                } else if (numeroRepeticiones.getTipo().equals("IDENTIFICADOR")
                        && tablaSimbolos.containsKey(numeroRepeticiones.getValor())) {
                    repeticiones = Integer.parseInt(tablaSimbolos.get(numeroRepeticiones.getValor()));
                }

                // Verificar que INIT siga después del numero
                if ((i + 2) < tokens.size() && tokens.get(i + 2).getValor().equals("INIT")) {
                    i += 3; 

                    List<Token> bloqueRepeat = new ArrayList<>();

                    while (i < tokens.size()) {
                        if (tokens.get(i).getValor().equals("END")) {
                            i++; // Se consume el END final del bloque REPEAT
                            break;
                        }

                        if (tokens.get(i).getValor().equals("PRINT") && (i + 2) < tokens.size()) {
                            // Agrega la instrucción completa al bloque
                            bloqueRepeat.add(tokens.get(i));       // token PRINT
                            bloqueRepeat.add(tokens.get(i + 1));     // contenido del PRINT
                            i += 3;
                        } else {
                            // En caso de que el patrón no concuerde, se agrega el token y se continúa
                            bloqueRepeat.add(tokens.get(i));
                            i++;
                        }
                    }

                    // Ejecutar el bloque REPEAT la cantidad de veces indicada
                    for (int j = 0; j < repeticiones; j++) {
                        StringWriter stringWriter = new StringWriter();
                        BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);

                        // Procesa cada instrucción en el bloque repeat
                        for (int k = 0; k < bloqueRepeat.size(); k++) {
                            Token token = bloqueRepeat.get(k);

                            if (token.getValor().equals("PRINT") && (k + 1) < bloqueRepeat.size()) {
                                Token contenidoToken = bloqueRepeat.get(k + 1);
                                String contenido = extraerContenidoPrint(contenidoToken);
                                if (contenido != null) {
                                    bufferedWriter.write(contenido + "\n");
                                }
                                k++; 
                            }
                        }

                        bufferedWriter.flush();
                        writer.write(stringWriter.toString());
                    }
                }
            }
        }
    }

    private void manejarCondicional(FileWriter writer) throws IOException {
        int i = 0;

        // Recorrer todos los tokens
        while (i < tokens.size()) {
            Token tokenActual = tokens.get(i);

            if (tokenActual.getValor().equals("IF")) {
                // Verificar que exista el siguiente token para la condición (TRUE o FALSE)
                if (i + 1 >= tokens.size()) {
                    break; 
                }
                Token tokenCondicion = tokens.get(i + 1);
                boolean condicion = false;

                // La condición debe ser TRUE o FALSE
                if (tokenCondicion.getValor().equals("TRUE")) {
                    condicion = true;
                } else if (tokenCondicion.getValor().equals("FALSE")) {
                    condicion = false;
                } else {
                    // Si no es válida la condición, se salta este token y se continúa
                    i++;
                    continue;
                }

                // Verificar que el siguiente token sea THEN
                if (i + 2 < tokens.size() && tokens.get(i + 2).getValor().equals("THEN")) {
                    i += 3; 
                } else {
                    i++;
                    continue; 
                }

                String contenidoPrint = null;

                // Verificar si existe una estructura PRINT anidada
                if (i < tokens.size() && tokens.get(i).getValor().equals("PRINT")) {
                    if (i + 1 < tokens.size()) {
                        Token tokenPrintContenido = tokens.get(i + 1);
                        contenidoPrint = extraerContenidoPrint(tokenPrintContenido);
                    }
                    if (i + 2 < tokens.size() && tokens.get(i + 2).getValor().equals("END")) {
                        i += 3;
                    } else {
                        i++;
                    }
                }

                // se espera el token END que cierra la estructura condicional IF
                if (i < tokens.size() && tokens.get(i).getValor().equals("END")) {
                    i++; 
                } else {
                    i++;
                }

                // Si la condición es TRUE y se encontró la parte PRINT, se escribe el contenido en el archivo.
                if (condicion && contenidoPrint != null) {
                    writer.write(contenidoPrint + "\n");
                }

                continue;
            }

            i++;
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico;

import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.util.List;

/**
 *
 * @author herson
 */
public class ParseRepeat {

    private final List<Token> tokens;
    private int posicion;

    public ParseRepeat(List<Token> tokens) {
        this.tokens = tokens;
        this.posicion = 0;
    }

    public boolean analizar() {
        // Pila simulada para manejar estados del autómata
        String estado = "INICIO";

        while (posicion < tokens.size()) {
            Token tokenActual = tokens.get(posicion);

            switch (estado) {
                case "INICIO":
                    if ("PALABRA_RESERVADA".equals(tokenActual.getTipo()) &&
                        "REPEAT".equals(tokenActual.getValor())) {
                        estado = "NUMERO_O_ID";
                        posicion++;
                    } else {
                        reportarError("Se esperaba la palabra reservada REPEAT.");
                        sincronizar();
                        return false;
                    }
                    break;

                case "NUMERO_O_ID":
                    if ("NUMERO_ENTERO".equals(tokenActual.getTipo()) ||
                        "IDENTIFICADOR".equals(tokenActual.getTipo())) {
                        estado = "INIT";
                        posicion++;
                    } else {
                        reportarError("Se esperaba un NUMERO_ENTERO o IDENTIFICADOR después de REPEAT.");
                        sincronizar();
                        return false;
                    }
                    break;

                case "INIT":
                    if ("PALABRA_RESERVADA".equals(tokenActual.getTipo()) &&
                        "INIT".equals(tokenActual.getValor())) {
                        estado = "PRINTS";
                        posicion++;
                    } else {
                        reportarError("Se esperaba la palabra reservada INIT después de NUMERO_ENTERO o IDENTIFICADOR.");
                        sincronizar();
                        return false;
                    }
                    break;

                case "PRINTS":
                    if ("PRINT".equals(tokenActual.getValor())) {
                        ParsePrint parserPrint = new ParsePrint(tokens.subList(posicion, tokens.size()));
                        if (!parserPrint.analizar()) {
                            reportarError("Error en una estructura PRINT dentro de REPEAT.");
                            sincronizar();
                            return false;
                        }
                        posicion += parserPrint.getPosicion();
                    } else if ("PALABRA_RESERVADA".equals(tokenActual.getTipo()) &&
                               "END".equals(tokenActual.getValor())) {
                        estado = "FINAL";
                        posicion++;
                    } else {
                        reportarError("Se esperaba una estructura PRINT o la palabra reservada END.");
                        sincronizar();
                        return false;
                    }
                    break;

                case "FINAL":
                    // Aunque se alcance el estado FINAL, salimos inmediatamente.
                    System.out.println("Estructura REPEAT reconocida correctamente.");
                    return true;

                default:
                    reportarError("Estado inválido en el autómata.");
                    return false;
            }
        }

        // Si se terminaron los tokens, verificamos si ya habíamos alcanzado el estado FINAL.
        if ("FINAL".equals(estado)) {
            return true;
        } else {
            reportarError("El análisis terminó sin encontrar la palabra reservada END para cerrar REPEAT.");
            return false;
        }
    }

    private void reportarError(String mensaje) {
        System.err.println("Error Sintáctico: " + mensaje);
    }

    private void sincronizar() {
        // Saltar hasta encontrar END o REPEAT para continuar el análisis
        while (posicion < tokens.size()) {
            String valorToken = tokens.get(posicion).getValor();
            if ("END".equals(valorToken) || "REPEAT".equals(valorToken)) {
                break;
            }
            posicion++;
        }
        // Si encuentra END, avanzar más allá de él para sincronizar completamente
        if (posicion < tokens.size() && "END".equals(tokens.get(posicion).getValor())) {
            posicion++;
        }
    }

    public int getPosicion() {
        return this.posicion;
    }
}


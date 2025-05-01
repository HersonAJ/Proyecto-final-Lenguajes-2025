/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico;

import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.util.List;
import java.util.Map;

/**
 *
 * @author herson
 */
public class AnalizadorSintactico {

    private final List<Token> tokens;
    private int posicion;
    private final StringBuilder erroresSintacticos; 

    public AnalizadorSintactico(List<Token> tokens) {
        this.tokens = tokens;
        this.posicion = 0;
        this.erroresSintacticos = new StringBuilder();
    }

    public String analizar() {
        while (posicion < tokens.size()) {
            Token tokenActual = tokens.get(posicion);
            System.out.println("Procesando token en posición: " + posicion + ", valor: '" + tokenActual.getValor() + "'.");

            if (tokenActual.getTipo().equals("IDENTIFICADOR")
                    && (posicion + 1) < tokens.size()
                    && tokens.get(posicion + 1).getValor().equals("=")) {
                System.out.println("Intentando reconocer estructura ASIGNACIÓN...");
                if (!parseAsignacion()) {
                    agregarError(tokenActual, "Estructura ASIGNACIÓN inválida. Se esperaba una EXPRESIÓN seguida de 'END'.");
                } else {
                    System.out.println("Estructura ASIGNACIÓN reconocida correctamente.");
                    mostrarTablaSimbolos(); 
                }
            } else if (tokenActual.getValor().equals("PRINT")) {
                System.out.println("Intentando reconocer estructura PRINT...");
                if (!parsePrint()) {
                    agregarError(tokenActual, "Estructura PRINT inválida. Se esperaba un literal, número o identificador seguido de 'END'.");
                } else {
                    System.out.println("Estructura PRINT reconocida correctamente.");
                }
            } else if (tokenActual.getValor().equals("REPEAT")) {
                System.out.println("Intentando reconocer estructura REPEAT...");
                if (!parseRepeat()) {
                    agregarError(tokenActual, "Estructura REPEAT inválida. Se esperaba un número entero positivo o identificador, seguido de INIT y PRINT.");
                } else {
                    System.out.println("Estructura REPEAT reconocida correctamente.");
                }
            } else if (tokenActual.getValor().equals("IF")) {
                System.out.println("Intentando reconocer estructura CONDICIONAL...");
                if (!parseCondicional()) {
                    agregarError(tokenActual, "Estructura CONDICIONAL inválida. Se esperaba TRUE/FALSE, THEN y opcionalmente PRINT seguido de END.");
                } else {
                    System.out.println("Estructura CONDICIONAL reconocida correctamente.");
                }
            } else if (tokenActual.getValor().equals("(")
                    || tokenActual.getTipo().equals("NUMERO_ENTERO")
                    || tokenActual.getTipo().equals("IDENTIFICADOR")) {
                System.out.println("Intentando reconocer estructura EXPRESIÓN...");
                if (!parseExpresion()) {
                    agregarError(tokenActual, "Estructura EXPRESIÓN inválida. Se esperaba operaciones entre números enteros e identificadores con paréntesis opcionales.");
                } else {
                    System.out.println("Estructura EXPRESIÓN reconocida correctamente.");
                }
            } else {
                agregarError(tokenActual, "Token inesperado. Se esperaba una estructura válida.");
                sincronizar();
                System.out.println("Sincronización realizada. Nueva posición: " + posicion + ".");
            }
        }

        return erroresSintacticos.length() > 0 ? erroresSintacticos.toString() : null;
    }

    private void agregarError(Token token, String mensaje) {
        erroresSintacticos.append("Error Sintáctico en línea ")
                .append(token.getLinea())
                .append(", columna ")
                .append(token.getColumna())
                .append(": ")
                .append(mensaje)
                .append(" (Token: '")
                .append(token.getValor())
                .append("')\n");
    }

    private boolean parsePrint() {
        ParsePrint parser = new ParsePrint(tokens.subList(posicion, tokens.size()));
        boolean exito = parser.analizar();
        posicion += parser.getPosicion();
        return exito;
    }

    private boolean parseRepeat() {
        ParseRepeat parser = new ParseRepeat(tokens.subList(posicion, tokens.size()));
        boolean exito = parser.analizar();
        posicion += parser.getPosicion();
        return exito;
    }

    private boolean parseCondicional() {
        ParseCondicional parser = new ParseCondicional(tokens.subList(posicion, tokens.size()));
        boolean exito = parser.analizar();
        posicion += parser.getPosicion();
        return exito;
    }

    private boolean parseExpresion() {
        ParseExpresion parser = new ParseExpresion(tokens.subList(posicion, tokens.size()));
        boolean exito = parser.analizar();
        posicion += parser.getPosicion();
        return exito;
    }

    private boolean parseAsignacion() {
        ParseAsignacion parser = new ParseAsignacion(tokens.subList(posicion, tokens.size()));
        boolean exito = parser.analizar();
        posicion += parser.getPosicion();
        return exito;
    }

    private void sincronizar() {
        while (posicion < tokens.size()) {
            String valorToken = tokens.get(posicion).getValor();
            if ("PRINT".equals(valorToken)
                    || "REPEAT".equals(valorToken)
                    || "IF".equals(valorToken)
                    || valorToken.equals("(")
                    || tokens.get(posicion).getTipo().equals("NUMERO_ENTERO")
                    || tokens.get(posicion).getTipo().equals("IDENTIFICADOR")) {
                break;
            }
            posicion++;
        }
        if (posicion < tokens.size()) {
            posicion++;
        }
    }

    private void mostrarTablaSimbolos() {
        System.out.println("\nTabla de Asignaciones (Variables y Valores):");
        for (Map.Entry<String, String> entry : ParseAsignacion.tablaSimbolos.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        System.out.println("--------------------------------------\n");
    }
}

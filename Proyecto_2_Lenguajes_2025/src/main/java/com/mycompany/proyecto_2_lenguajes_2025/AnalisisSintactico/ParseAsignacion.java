/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico;

import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author herson
 */
public class ParseAsignacion {
    private final List<Token> tokens;
    private int posicion;

    // Tabla de símbolos para almacenar el valor de las variables
    public static Map<String, String> tablaSimbolos = new HashMap<>();

    public ParseAsignacion(List<Token> tokens) {
        this.tokens = tokens;
        this.posicion = 0;
    }

public boolean analizar() {
    if (posicion >= tokens.size()) {
        reportError("No hay tokens para analizar.");
        return false;
    }

    // Verificar que inicia con un identificador
    Token tokenId = tokens.get(posicion);
    if (!"IDENTIFICADOR".equals(tokenId.getTipo())) {
        reportError("Se esperaba un identificador al inicio de la asignación.");
        return false;
    }

    String identificador = tokenId.getValor();
    posicion++;

    // Verificar que sigue el operador de asignación =
    if (posicion >= tokens.size() || !"OPERADOR_ASIGNACION".equals(tokens.get(posicion).getTipo())) {
        reportError("Se esperaba el operador '=' después del identificador.");
        return false;
    }
    posicion++;

    // Analizar la expresión (con pila predictiva)
    Stack<String> pila = new Stack<>();
    pila.push("EXPRESION");

    int inicioExpr = posicion;
    if (!analizarExpresion(pila)) {
        return false; // ya reportó el error
    }

    // Guardar la expresión como string (tokens desde inicioExpr hasta la nueva posición)
    StringBuilder expresionStr = new StringBuilder();
    for (int i = inicioExpr; i < posicion; i++) {
        expresionStr.append(tokens.get(i).getValor()).append(" ");
    }

    // 🔽🔽🔽 ESTA ES LA MODIFICACIÓN
    if (posicion >= tokens.size() || !"END".equals(tokens.get(posicion).getValor())) {
        reportError("Se esperaba 'END' al final de la asignación.");
        return false;
    }
    posicion++; // Consumir END
    // 🔼🔼🔼 HASTA AQUÍ

    tablaSimbolos.put(identificador, expresionStr.toString().trim());
    System.out.println("Asignación reconocida: " + identificador + " = " + expresionStr.toString().trim());
    return true;
}


    private boolean analizarExpresion(Stack<String> pila) {
        while (!pila.isEmpty()) {
            String simbolo = pila.peek();

            if (isNonTerminal(simbolo)) {
                pila.pop();
                switch (simbolo) {
                    case "EXPRESION":
                        pila.push("EXPRESION_PRIMA");
                        pila.push("TERM");
                        break;

                    case "EXPRESION_PRIMA":
                        if (posicion < tokens.size()) {
                            String val = tokens.get(posicion).getValor();
                            if (val.equals("+") || val.equals("-")) {
                                pila.push("EXPRESION_PRIMA");
                                pila.push("TERM");
                                pila.push(val);
                            }
                        }
                        break;

                    case "TERM":
                        pila.push("TERM_PRIMA");
                        pila.push("FACTOR");
                        break;

                    case "TERM_PRIMA":
                        if (posicion < tokens.size()) {
                            String val = tokens.get(posicion).getValor();
                            if (val.equals("*") || val.equals("/")) {
                                pila.push("TERM_PRIMA");
                                pila.push("FACTOR");
                                pila.push(val);
                            }
                        }
                        break;

                    case "FACTOR":
                        if (posicion < tokens.size()) {
                            Token tokenActual = tokens.get(posicion);
                            if (tokenActual.getValor().equals("(")) {
                                pila.push(")");
                                pila.push("EXPRESION");
                                pila.push("(");
                            } else if ("NUMERO_ENTERO".equals(tokenActual.getTipo())) {
                                pila.push("NUM");
                            } else if ("IDENTIFICADOR".equals(tokenActual.getTipo())) {
                                pila.push("ID");
                            } else {
                                reportError("Se esperaba un factor válido en la expresión.");
                                return false;
                            }
                        } else {
                            reportError("Faltan tokens para completar un factor.");
                            return false;
                        }
                        break;

                    default:
                        reportError("No se reconoce el no terminal: " + simbolo);
                        return false;
                }
            } else {
                String terminal = pila.peek();
                if (posicion < tokens.size()) {
                    Token tokenActual = tokens.get(posicion);
                    if (terminal.equals("NUM") && "NUMERO_ENTERO".equals(tokenActual.getTipo())) {
                        pila.pop();
                        posicion++;
                    } else if (terminal.equals("ID") && "IDENTIFICADOR".equals(tokenActual.getTipo())) {
                        pila.pop();
                        posicion++;
                    } else if (terminal.equals(tokenActual.getValor())) {
                        pila.pop();
                        posicion++;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isNonTerminal(String simbolo) {
        return simbolo.equals("EXPRESION") ||
               simbolo.equals("EXPRESION_PRIMA") ||
               simbolo.equals("TERM") ||
               simbolo.equals("TERM_PRIMA") ||
               simbolo.equals("FACTOR");
    }

    private void reportError(String mensaje) {
        System.err.println("Error Sintáctico: " + mensaje);
    }

    public int getPosicion() {
        return this.posicion;
    }
}
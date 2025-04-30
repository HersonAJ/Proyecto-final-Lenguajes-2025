/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico;

import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author herson
 */
public class ParseExpresion {

    private final List<Token> tokens;
    private int posicion;

    public ParseExpresion(List<Token> tokens) {
        this.tokens = tokens;
        this.posicion = 0;
    }

    public boolean analizar() {
        Stack<String> pila = new Stack<>();
        pila.push("EXPRESION"); 

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
                                pila.push(val); // el operador se trata como terminal
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
                                // Validar que el identificador cumpla la regla del lenguaje (debe comenzar con '$')
                                if (!tokenActual.getValor().startsWith("$")) {
                                    reportError("Identificador inválido: '" + tokenActual.getValor() +
                                                "'. Los identificadores deben comenzar con '$'.");
                                    return false;
                                }
                                pila.push("ID");
                            } else {
                                reportError("Se esperaba un factor (número, identificador o '(') en la expresión.");
                                return false;
                            }
                        } else {
                            reportError("No se encontró token para FACTOR en la expresión.");
                            return false;
                        }
                        break;

                    default:
                        reportError("No se reconoce el no terminal: " + simbolo);
                        return false;
                }
            } else { // El símbolo es terminal
                String terminal = pila.peek();
                if (posicion < tokens.size()) {
                    Token tokenActual = tokens.get(posicion);

                    if (terminal.equals("NUM")) {
                        if ("NUMERO_ENTERO".equals(tokenActual.getTipo())) {
                            pila.pop();
                            posicion++;
                        } else {
                            reportError("Se esperaba un número en la expresión en posición: " + posicion);
                            return false;
                        }
                    } else if (terminal.equals("ID")) {
                        if ("IDENTIFICADOR".equals(tokenActual.getTipo())
                            && tokenActual.getValor().startsWith("$")) {
                            pila.pop();
                            posicion++;
                        } else {
                            reportError("Se esperaba un identificador, que debe comenzar con '$', en la posición: " + posicion);
                            return false;
                        }
                    } else {
                        // Para los demás terminales, se compara el valor literal
                        if (terminal.equals(tokenActual.getValor())) {
                            pila.pop();
                            posicion++;
                        } else {
                            reportError("Se esperaba el token '" + terminal + "' pero se encontró '" 
                                        + tokenActual.getValor() + "' en posición: " + posicion);
                            return false;
                        }
                    }
                } else {
                    reportError("No hay más tokens para comparar con el terminal '" + terminal + "'.");
                    return false;
                }
            }
        } //

        if (posicion == tokens.size()) {
            System.out.println("Estructura EXPRESIÓN reconocida correctamente.");
            return true;
        } else {
            reportError("Quedaron tokens sin procesar en la expresión.");
            return false;
        }
    }

    private boolean isNonTerminal(String simbolo) {
        return simbolo.equals("EXPRESION")
                || simbolo.equals("EXPRESION_PRIMA")
                || simbolo.equals("TERM")
                || simbolo.equals("TERM_PRIMA")
                || simbolo.equals("FACTOR");
    }

    private void reportError(String mensaje) {
        System.err.println("Error Sintáctico: " + mensaje);
    }

    public int getPosicion() {
        return this.posicion;
    }
}


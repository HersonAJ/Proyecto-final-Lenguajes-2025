/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico;

import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author herson
 */
public class AnalizadorSintactico2 {

    private Token tokenActual;
    private List<Token> tokens;
    private int indiceToken;
    public Map<String, Double> tablaSimbolos;
    private List<String> errores = new ArrayList<>();

    public AnalizadorSintactico2(List<Token> tokens) {
        this.tokens = tokens;
        this.indiceToken = 0;
        this.tokenActual = tokens.get(indiceToken);
        this.tablaSimbolos = new HashMap<>();
    }

    public void parse() {
        while (!tokenActual.getTipo().equals("EOF")) {
            if (tokenActual.getTipo().equals("IDENTIFICADOR")) {
                asignacion();
            } else if (tokenActual.getValor().equals("PRINT")) {
                estructuraPrint();
            } else if (tokenActual.getTipo().equals("NUMERO_ENTERO") || tokenActual.getValor().equals("(")) {
                double resultado = expresion();
                System.out.println("Resultado de la expresión: " + resultado);
            } else {
                agregarError(tokenActual, "Entrada no válida. Se esperaba un número, identificador, expresión o PRINT.");
                consumirToken(); // Evitar bucle infinito
            }
        }
    }

    private void asignacion() {
        Token identificador = tokenActual;
        if (tokenActual.getTipo().equals("IDENTIFICADOR")) {
            consumirToken();
            if (tokenActual.getValor().equals("=")) {
                consumirToken();
                double valor = expresion();
                if (tokenActual.getValor().equalsIgnoreCase("END")) {
                    consumirToken();
                    tablaSimbolos.put(identificador.getValor(), valor);
                    System.out.println("Tabla de símbolos actualizada:");
                    mostrarTablaSimbolos();
                } else {
                    agregarError(tokenActual, "Se esperaba la palabra reservada END");
                }
            } else {
                agregarError(tokenActual, "Se esperaba el signo igual (=)");
            }
        } else {
            agregarError(tokenActual, "Se esperaba un identificador");
        }
    }

    private double expresion() {
        double resultado = termino();
        while (tokenActual.getValor().equals("+") || tokenActual.getValor().equals("-")) {
            String operador = tokenActual.getValor();
            consumirToken();
            double terminoValor = termino();
            resultado = operador.equals("+") ? resultado + terminoValor : resultado - terminoValor;
        }
        //System.out.println("Resultado de la expresión: " + resultado);
        return resultado;
    }

    private double termino() {
        double resultado = factor();
        while (tokenActual.getValor().equals("*") || tokenActual.getValor().equals("/")) {
            String operador = tokenActual.getValor();
            Token token = tokenActual;
            consumirToken();
            double factorValor = factor();
            if (operador.equals("*")) {
                resultado *= factorValor;
            } else {
                if (factorValor == 0) {
                    agregarError(token, "División por cero");
                    return resultado;
                }
                resultado /= factorValor;
            }
        }
        return resultado;
    }

    private double factor() {
        Token tokenError = tokenActual;

        if (tokenActual.getTipo().equals("NUMERO_ENTERO")) {
            double valor = Double.parseDouble(tokenActual.getValor());
            consumirToken();
            return valor;
        } else if (tokenActual.getTipo().equals("IDENTIFICADOR")) {
            if (tablaSimbolos.containsKey(tokenActual.getValor())) {
                double valor = tablaSimbolos.get(tokenActual.getValor());
                consumirToken();
                return valor;
            } else {
                agregarError(tokenActual, "Identificador no declarado. Se debe asignar antes de usar.");
                consumirToken(); // Avanzar el token para evitar bloqueos
                return 0;
            }
        } else if (tokenActual.getValor().equals("(")) {
            consumirToken();
            double valor = expresion();
            if (!tokenActual.getValor().equals(")")) {
                agregarError(tokenError, "Se esperaba un paréntesis de cierre");
            }
            consumirToken();
            return valor;
        } else {
            agregarError(tokenError, "Se esperaba un número, un identificador o un paréntesis de apertura");
            consumirToken(); // Evita bloqueo en bucle
            return 0;
        }
    }

    private void estructuraPrint() {
        consumirToken(); // Avanzar después de "PRINT"

        if (tokenActual.getTipo().equals("LITERAL")) {
            System.out.println(tokenActual.getValor().replace("\"", "")); // Mostrar sin comillas
            consumirToken();
        } else if (tokenActual.getTipo().equals("NUMERO_ENTERO")) {
            System.out.println(tokenActual.getValor()); // Mostrar el número
            consumirToken();
        } else if (tokenActual.getTipo().equals("IDENTIFICADOR")) {
            if (tablaSimbolos.containsKey(tokenActual.getValor())) {
                System.out.println(tablaSimbolos.get(tokenActual.getValor())); // Mostrar valor del identificador
                consumirToken();
            } else {
                agregarError(tokenActual, "Identificador no declarado en PRINT.");
                consumirToken();
            }
        } else {
            agregarError(tokenActual, "PRINT esperaba un literal, número o identificador.");
            consumirToken();
        }

        // Verificar si sigue END
        if (tokenActual.getValor().equalsIgnoreCase("END")) {
            consumirToken();
        } else {
            agregarError(tokenActual, "Se esperaba la palabra reservada END después de PRINT.");
        }
    }

    private void consumirToken() {
        if (indiceToken < tokens.size() - 1) {
            indiceToken++;
            tokenActual = tokens.get(indiceToken);
        } else {
            tokenActual = new Token("EOF", "", tokenActual.getLinea(), tokenActual.getColumna());
        }
    }

    public Token getTokenActual() {
        return tokenActual;
    }

    public Map<String, Double> getTablaSimbolos() {
        return tablaSimbolos;
    }

    private void agregarError(Token token, String mensaje) {
        errores.add("Error sintáctico en línea " + token.getLinea() + ", columna " + token.getColumna() + ": " + mensaje);
    }

    public List<String> mostrarErrores() {
        return errores;
    }

    // Método para imprimir la tabla de símbolos
    private void mostrarTablaSimbolos() {
        for (Map.Entry<String, Double> entrada : tablaSimbolos.entrySet()) {
            System.out.println(entrada.getKey() + " = " + entrada.getValue());
        }
    }

    // Clase interna para manejar errores sintácticos
    public class ErrorSintactico extends RuntimeException {

        private String mensaje;

        public ErrorSintactico(Token token, String mensaje) {
            this.mensaje = "Error sintáctico en línea " + token.getLinea() + ", columna " + token.getColumna() + ": " + mensaje;
        }

        @Override
        public String getMessage() {
            return mensaje;
        }
    }
}

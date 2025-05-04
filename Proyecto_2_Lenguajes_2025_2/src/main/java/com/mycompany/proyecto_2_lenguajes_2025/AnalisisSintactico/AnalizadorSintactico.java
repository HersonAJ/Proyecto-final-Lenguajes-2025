/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico;

import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author herson
 */
public class AnalizadorSintactico {
    private List<Token> tokens;
    private int indice;
    private Map<String, Double> tablaSimbolos = new HashMap<>();
    private boolean huboErrores = false; // Nueva variable para indicar si hubo errores

    public AnalizadorSintactico(List<Token> tokens) {
        this.tokens = tokens;
        this.indice = 0;
    }

    public void analizar() {
        while (indice < tokens.size()) {
            try {
                asignacion();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                huboErrores = true; // Se registra que hubo un error
                modoPanico();
            }
        }

        if (huboErrores) {
            System.out.println("Análisis finalizado con errores."); // Mensaje claro si hubo errores
        } else {
            System.out.println("Análisis sintáctico completado sin errores.");
        }
    }

    private void asignacion() throws Exception {
        if (!matchTipo("IDENTIFICADOR")) {
            throw new Exception("Error sintáctico: Se esperaba un identificador en " + obtenerPosicionActual() + ".");
        }
        String identificador = tokens.get(indice - 1).getValor();

        if (!matchOperador("=")) {
            throw new Exception("Error sintáctico: Se esperaba '=' después del identificador en " + obtenerPosicionActual() + ".");
        }

        if (matchTipo("PALABRA_RESERVADA") && tokens.get(indice - 1).getValor().equals("END")) {
            throw new Exception("Error sintáctico: Se esperaba una expresión antes de 'END' en " + obtenerPosicionActual() + ".");
        }

        double valor = expresion(); // Evaluar la expresión a la derecha del '='

        if (!matchTipo("PALABRA_RESERVADA") || !tokens.get(indice - 1).getValor().equals("END")) {
            throw new Exception("Error sintáctico: Se esperaba 'END' para finalizar la asignación en " + obtenerPosicionActual() + ".");
        }

        tablaSimbolos.put(identificador, valor); // Guardar el valor en la tabla de símbolos
        System.out.println("Asignación válida: " + identificador + " = " + valor);
    }

    private double expresion() throws Exception {
        double valor = termino();
        return expresionPrima(valor);
    }

    private double expresionPrima(double izquierda) throws Exception {
        if (matchOperador("+")) {
            double derecha = termino();
            return expresionPrima(izquierda + derecha);
        } else if (matchOperador("-")) {
            double derecha = termino();
            return expresionPrima(izquierda - derecha);
        }
        return izquierda;
    }

    private double termino() throws Exception {
        double valor = factor();
        return terminoPrima(valor);
    }

    private double terminoPrima(double izquierda) throws Exception {
        if (matchOperador("*")) {
            double derecha = factor();
            return terminoPrima(izquierda * derecha);
        } else if (matchOperador("/")) {
            double derecha = factor();
            if (derecha == 0) {
                throw new Exception("Error semántico: División por cero en " + obtenerPosicionActual() + ".");
            }
            return terminoPrima(izquierda / derecha);
        }
        return izquierda;
    }

    private double factor() throws Exception {
        if (matchTipo("NUMERO_ENTERO")) {
            return Double.parseDouble(tokens.get(indice - 1).getValor());
        } else if (matchTipo("IDENTIFICADOR")) {
            String identificador = tokens.get(indice - 1).getValor();
            if (!tablaSimbolos.containsKey(identificador)) {
                throw new Exception("Error semántico: Identificador '" + identificador + "' no definido en " + obtenerPosicionActual() + ".");
            }
            return tablaSimbolos.get(identificador);
        } else if (matchOperador("(")) {
            double valor = expresion();
            if (!matchOperador(")")) {
                throw new Exception("Error sintáctico: Se esperaba ')' en " + obtenerPosicionActual() + ".");
            }
            return valor;
        } else {
            Token errorToken = tokens.get(indice);
            throw new Exception("Error sintáctico: Factor inesperado '" + errorToken.getValor() + "' en " + obtenerPosicionActual() + ".");
        }
    }

    private boolean matchTipo(String tipoEsperado) {
        if (indice < tokens.size() && tokens.get(indice).getTipo().equals(tipoEsperado)) {
            indice++;
            return true;
        }
        return false;
    }

    private boolean matchOperador(String operadorEsperado) {
        if (indice < tokens.size()) {
            Token token = tokens.get(indice);
            if ((token.getTipo().equals("OPERADOR_ARITMETICO") || token.getTipo().equals("OPERADOR_ASIGNACION"))
                && token.getValor().equals(operadorEsperado)) {
                indice++;
                return true;
            }
        }
        return false;
    }

    private String obtenerPosicionActual() {
        if (indice < tokens.size()) {
            Token token = tokens.get(indice);
            return "línea " + token.getLinea() + ", columna " + token.getColumna();
        }
        return "posición desconocida";
    }

    private void modoPanico() {
        System.out.println("Entrando en modo pánico...");
        while (indice < tokens.size()) {
            Token token = tokens.get(indice);

            // Si encontramos END o un nuevo identificador, sincronizamos y seguimos analizando
            if (token.getTipo().equals("PALABRA_RESERVADA") && token.getValor().equals("END") ||
                token.getTipo().equals("IDENTIFICADOR")) {
                System.out.println("Modo pánico: Recuperado en " + obtenerPosicionActual());
                indice++; // Saltamos al siguiente token válido
                return;
            }

            indice++; // Descartar el token y seguir hasta encontrar uno válido
        }

        System.out.println("Modo pánico: Fin de la entrada alcanzado.");
    }
}
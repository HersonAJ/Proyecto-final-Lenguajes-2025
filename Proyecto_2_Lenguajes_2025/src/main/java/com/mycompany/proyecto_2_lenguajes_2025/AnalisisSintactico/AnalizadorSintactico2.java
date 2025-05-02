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
import java.util.Stack;

/**
 *
 * @author herson
 */
/*

este es un LL1 por lo que un no me sierve
public class AnalizadorSintactico2 {
    private Token tokenActual;
    private List<Token> tokens;
    private int indiceToken;
    public Map<String, Double> tablaSimbolos;

    public AnalizadorSintactico2(List<Token> tokens) {
        this.tokens = tokens;
        this.indiceToken = 0;
        this.tokenActual = tokens.get(indiceToken);
        this.tablaSimbolos = new HashMap<>();
    }

    public void parse() {
        while (!tokenActual.getTipo().equals("EOF")) {
            asignacion();
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
                    // Actualizar la tabla de símbolos con el valor del identificador
                    tablaSimbolos.put(identificador.getValor(), valor);
                } else {
                    throw new ErrorSintactico(tokenActual, "Se esperaba la palabra reservada END");
                }
            } else {
                throw new ErrorSintactico(tokenActual, "Se esperaba el signo igual (=)");
            }
        } else {
            throw new ErrorSintactico(tokenActual, "Se esperaba un identificador");
        }
    }

    private double expresion() {
        double resultado = termino();
        while (tokenActual.getValor().equals("+") || tokenActual.getValor().equals("-")) {
            String operador = tokenActual.getValor();
            Token tokenError = tokenActual; // Guardamos la referencia
            consumirToken();
            double terminoValor = termino();
            if (operador.equals("+")) {
                resultado += terminoValor;
            } else {
                resultado -= terminoValor;
            }
        }
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
                    throw new ErrorSintactico(token, "División por cero");
                }
                resultado /= factorValor;
            }
        }
        return resultado;
    }

    private double factor() {
        Token tokenError = tokenActual; // Guardamos el token antes de avanzar
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
                throw new ErrorSintactico(tokenActual, "Identificador no declarado");
            }
        } else if (tokenActual.getValor().equals("(")) {
            consumirToken();
            double valor = expresion();
            if (!tokenActual.getValor().equals(")")) {
                throw new ErrorSintactico(tokenError, "Se esperaba un paréntesis de cierre");
            }
            consumirToken();
            return valor;
        } else {
            throw new ErrorSintactico(tokenError, "Se esperaba un número, un identificador o un paréntesis de apertura");
        }
    }

    private void consumirToken() {
        if (indiceToken < tokens.size() - 1) {
            indiceToken++;
            tokenActual = tokens.get(indiceToken);
        } else {
            // Mantener la última posición válida antes de EOF
            tokenActual = new Token("EOF", "", tokenActual.getLinea(), tokenActual.getColumna());
        }
    }

    public Token getTokenActual() {
        return tokenActual;
    }

    public class ErrorSintactico extends RuntimeException {
        private String mensaje;

        public ErrorSintactico(Token token, String mensaje) {
           this.mensaje = "Error sintáctico en la línea " + token.getLinea() + ", columna " + token.getColumna() + ": " + mensaje;
        
        }

        @Override
        public String getMessage() {
            return mensaje;
        }
    }
    public Map<String, Double> getTablaSimbolos() {
    return tablaSimbolos;
}
}

*/

/* este sigue siendo un ll1 pero mas limitado
public class AnalizadorSintactico2 {
    
    private Token tokenActual;
    private List<Token> tokens;
    private int indiceToken;
    public Map<String, Double> tablaSimbolos;
    
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
        } else {
            double resultado = expresion();
            System.out.println("Resultado: " + resultado);
        }
    }
}

    private void asignacion() {
        Token identificador = tokenActual;
        consumirToken(); // IDENTIFICADOR
        consumirToken(); // =
        double valor = expresion();
        consumirToken(); // END
        tablaSimbolos.put(identificador.getValor(), valor);
    }

    private double expresion() {
        double resultado = termino();
        while (tokenActual.getValor().equals("+") || tokenActual.getValor().equals("-")) {
            String operador = tokenActual.getValor();
            consumirToken();
            double terminoValor = termino();
            if (operador.equals("+")) {
                resultado += terminoValor;
            } else {
                resultado -= terminoValor;
            }
        }
        return resultado;
    }

    private double termino() {
        double resultado = factor();
        while (tokenActual.getValor().equals("*") || tokenActual.getValor().equals("/")) {
            String operador = tokenActual.getValor();
            consumirToken();
            double factorValor = factor();
            if (operador.equals("*")) {
                resultado *= factorValor;
            } else {
                resultado /= factorValor;
            }
        }
        return resultado;
    }

    private double factor() {
        double valor;
        if (tokenActual.getTipo().equals("NUMERO_ENTERO")) {
            valor = Double.parseDouble(tokenActual.getValor());
            consumirToken();
        } else if (tokenActual.getTipo().equals("IDENTIFICADOR")) {
            valor = tablaSimbolos.get(tokenActual.getValor());
            consumirToken();
        } else if (tokenActual.getValor().equals("(")) {
            consumirToken();
            valor = expresion();
            consumirToken(); // )
        } else {
            valor = 0; // No se utiliza, pero es necesario para compilar
        }
        return valor;
    }

    private void consumirToken() {
        if (indiceToken < tokens.size() - 1) {
            indiceToken++;
            tokenActual = tokens.get(indiceToken);
        } else {
            // Mantener la última posición válida antes de EOF
            tokenActual = new Token("EOF", "", tokenActual.getLinea(), tokenActual.getColumna());
        }
    }
    
        public class ErrorSintactico extends RuntimeException {
        private String mensaje;

        public ErrorSintactico(Token token, String mensaje) {
           this.mensaje = "Error sintáctico en la línea " + token.getLinea() + ", columna " + token.getColumna() + ": " + mensaje;
        
        }

        @Override
        public String getMessage() {
            return mensaje;
        }
    }
    public Map<String, Double> getTablaSimbolos() {
    return tablaSimbolos;
}

}*/

import java.util.*;

public class AnalizadorSintactico2 {
    private Stack<Token> pila;
    private List<Token> tokens;
    private int indiceToken;
    public Map<String, Double> tablaSimbolos;

    public AnalizadorSintactico2(List<Token> tokens) {
        this.tokens = tokens;
        this.indiceToken = 0;
        this.pila = new Stack<>();
        this.tablaSimbolos = new HashMap<>();
    }

public void parse() {
    while (indiceToken < tokens.size()) {
        Token token = tokens.get(indiceToken);

        if (token.getTipo().equals("IDENTIFICADOR")) {
            procesarAsignacion();
        } else if (token.getTipo().equals("NUMERO_ENTERO") || token.getValor().equals("(")) {
            double resultado = procesarExpresion();
            System.out.println("Resultado: " + resultado);
        } else {
            throw new ErrorSintactico(token, "Entrada no válida, se esperaba una asignación o una expresión.");
        }
    }
}


private void procesarAsignacion() {
    Token identificador = tokens.get(indiceToken++);
    pila.push(identificador); // IDENTIFICADOR
    pila.push(tokens.get(indiceToken++)); // "="
    double valor = procesarExpresion(); // Procesa la expresión correctamente

    if (indiceToken >= tokens.size() || !tokens.get(indiceToken).getValor().equals("END")) {
        throw new ErrorSintactico(tokens.get(indiceToken), "Se esperaba END al final de la asignación.");
    }

    pila.push(tokens.get(indiceToken++)); // END
    tablaSimbolos.put(identificador.getValor(), valor);
}


    private double procesarExpresion() {
        Stack<Double> pilaNumerica = new Stack<>();
        Stack<String> pilaOperadores = new Stack<>();
        
        while (indiceToken < tokens.size()) {
            Token token = tokens.get(indiceToken);
            if (token.getTipo().equals("NUMERO_ENTERO")) {
                pilaNumerica.push(Double.parseDouble(token.getValor()));
                indiceToken++;
            } else if (token.getTipo().equals("IDENTIFICADOR")) {
                pilaNumerica.push(tablaSimbolos.getOrDefault(token.getValor(), 0.0));
                indiceToken++;
            } else if (token.getValor().matches("[+\\-*/]")) {
                pilaOperadores.push(token.getValor());
                indiceToken++;
            } else if (token.getValor().equals("(")) {
                indiceToken++;
                pilaNumerica.push(procesarExpresion());
            } else if (token.getValor().equals(")")) {
                indiceToken++;
                break;
            } else {
                break;
            }
        }

        while (!pilaOperadores.isEmpty()) {
            double b = pilaNumerica.pop();
            double a = pilaNumerica.pop();
            String op = pilaOperadores.pop();

            switch (op) {
                case "+": pilaNumerica.push(a + b); break;
                case "-": pilaNumerica.push(a - b); break;
                case "*": pilaNumerica.push(a * b); break;
                case "/": pilaNumerica.push(a / b); break;
            }
        }

        return pilaNumerica.isEmpty() ? 0 : pilaNumerica.pop();
    }

    public Map<String, Double> getTablaSimbolos() {
        return tablaSimbolos;
    }
            public class ErrorSintactico extends RuntimeException {
        private String mensaje;

        public ErrorSintactico(Token token, String mensaje) {
           this.mensaje = "Error sintáctico en la línea " + token.getLinea() + ", columna " + token.getColumna() + ": " + mensaje;
        
        }
            }
}
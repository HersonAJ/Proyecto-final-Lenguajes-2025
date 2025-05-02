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
        } else if (token.getValor().equals("PRINT")) {
            procesarPrint();
        } else if (token.getValor().equals("REPEAT")) {
            procesarRepeat();
        } else if (token.getValor().equals("IF")) {
            procesarCondicional();
        } else if (token.getTipo().equals("NUMERO_ENTERO") || token.getValor().equals("(")) {
            double resultado = procesarExpresion();
            System.out.println("Resultado: " + resultado);
        } else {
            throw new ErrorSintactico(token, "Entrada no válida, se esperaba una asignación, un PRINT, un REPEAT, un IF o una expresión.");
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
    
    private void procesarPrint() {
    Token printToken = tokens.get(indiceToken++);
    pila.push(printToken); // Apilar PRINT

    if (indiceToken >= tokens.size()) {
        throw new ErrorSintactico(printToken, "Se esperaba un valor después de PRINT.");
    }

    Token valorToken = tokens.get(indiceToken++);
    pila.push(valorToken); // Apilar el valor a imprimir

    if (indiceToken >= tokens.size() || !tokens.get(indiceToken).getValor().equals("END")) {
        throw new ErrorSintactico(tokens.get(indiceToken), "Se esperaba END después de PRINT.");
    }

    Token endToken = tokens.get(indiceToken++);
    pila.push(endToken); // Apilar END

    // Desempilar valores
    pila.pop(); // Remover END
    Token valorPrint = pila.pop();
    pila.pop(); // Remover PRINT

    String resultado = "";

    if (valorPrint.getTipo().equals("LITERAL")) {
        resultado = valorPrint.getValor().replace("\"", ""); // Quitar comillas
    } else if (valorPrint.getTipo().equals("NUMERO_ENTERO")) {
        resultado = valorPrint.getValor();
    } else if (valorPrint.getTipo().equals("IDENTIFICADOR")) {
        if (tablaSimbolos.containsKey(valorPrint.getValor())) {
            resultado = String.valueOf(tablaSimbolos.get(valorPrint.getValor()));
        } else {
            throw new ErrorSintactico(valorPrint, "Identificador no declarado.");
        }
    } else {
        throw new ErrorSintactico(valorPrint, "Se esperaba un literal, número o identificador después de PRINT.");
    }

    System.out.println("PRINT exitoso: " + resultado);
}
    
private void procesarRepeat() {
    Token repeatToken = tokens.get(indiceToken++);
    pila.push(repeatToken); // Apilar REPEAT

    if (indiceToken >= tokens.size()) {
        throw new ErrorSintactico(repeatToken, "Se esperaba un número entero positivo o un identificador después de REPEAT.");
    }

    Token iteracionesToken = tokens.get(indiceToken++);
    pila.push(iteracionesToken); // Apilar número o identificador

    if (indiceToken >= tokens.size() || !tokens.get(indiceToken).getValor().equals("INIT")) {
        throw new ErrorSintactico(iteracionesToken, "Se esperaba INIT después de la cantidad de repeticiones.");
    }

    Token initToken = tokens.get(indiceToken++);
    pila.push(initToken); // Apilar INIT

    // **Determinar número de repeticiones**
    int repeticiones;
    if (iteracionesToken.getTipo().equals("NUMERO_ENTERO")) {
        repeticiones = Integer.parseInt(iteracionesToken.getValor());
    } else if (iteracionesToken.getTipo().equals("IDENTIFICADOR") && tablaSimbolos.containsKey(iteracionesToken.getValor())) {
        repeticiones = tablaSimbolos.get(iteracionesToken.getValor()).intValue();
    } else {
        throw new ErrorSintactico(iteracionesToken, "Identificador no declarado o número inválido.");
    }

    // **Ejecutar el bloque repetitivo**
    int inicioBloque = indiceToken; // Guardamos el inicio del bloque

    for (int i = 0; i < repeticiones; i++) {
        System.out.println("Iteración " + (i + 1) + " de REPEAT.");
        indiceToken = inicioBloque; // Reiniciamos el índice para que PRINT se procese nuevamente

        while (indiceToken < tokens.size() && !tokens.get(indiceToken).getValor().equals("END")) {
            Token token = tokens.get(indiceToken);

            System.out.println("Procesando iteración " + (i + 1) + ", Token actual: " + token.getValor());

            if (token.getValor().equals("PRINT")) {
                procesarPrint();
            } else {
                throw new ErrorSintactico(token, "Entrada no válida dentro de REPEAT, solo se permiten PRINT.");
            }
        }
    }

    if (indiceToken >= tokens.size() || !tokens.get(indiceToken).getValor().equals("END")) {
        throw new ErrorSintactico(tokens.get(indiceToken), "Se esperaba END al finalizar REPEAT.");
    }

    Token endToken = tokens.get(indiceToken++);
    pila.push(endToken); // Apilar END

    // **Desempilar estructura completa**
    pila.pop(); // END
    pila.pop(); // INIT
    pila.pop(); // Número/Identificador
    pila.pop(); // REPEAT

    System.out.println("REPEAT exitoso.");
}


private void procesarCondicional() {
    Token ifToken = tokens.get(indiceToken++);
    pila.push(ifToken); // Apilar IF

    if (indiceToken >= tokens.size()) {
        throw new ErrorSintactico(ifToken, "Se esperaba TRUE o FALSE después de IF.");
    }

    Token condicionToken = tokens.get(indiceToken++);
    pila.push(condicionToken); // Apilar condición

    if (!condicionToken.getValor().equals("TRUE") && !condicionToken.getValor().equals("FALSE")) {
        throw new ErrorSintactico(condicionToken, "Condición inválida, debe ser TRUE o FALSE.");
    }

    if (indiceToken >= tokens.size() || !tokens.get(indiceToken).getValor().equals("THEN")) {
        throw new ErrorSintactico(tokens.get(indiceToken), "Se esperaba THEN después de la condición.");
    }

    Token thenToken = tokens.get(indiceToken++);
    pila.push(thenToken); // Apilar THEN

    boolean ejecutarBloque = condicionToken.getValor().equals("TRUE");

    // Procesar todos los tokens dentro del IF hasta encontrar el END final
    while (indiceToken < tokens.size()) {
        Token actual = tokens.get(indiceToken);

        if (actual.getValor().equals("END")) {
            indiceToken++; // Consumir END
            break; // Salir del IF
        }

        if (ejecutarBloque) {
            if (actual.getValor().equals("PRINT")) {
                procesarPrint();
            } else if (actual.getTipo().equals("IDENTIFICADOR")) {
                procesarAsignacion();
            } else if (actual.getTipo().equals("NUMERO_ENTERO") || actual.getValor().equals("(")) {
                double resultado = procesarExpresion();
                System.out.println("Resultado: " + resultado);
            } else {
                throw new ErrorSintactico(actual, "Instrucción no válida dentro de IF.");
            }
        } else {
            // Solo saltar tokens hasta llegar al END correspondiente
            if (actual.getValor().equals("PRINT")) {
                indiceToken++; // PRINT
                if (indiceToken < tokens.size()) indiceToken++; // Cadena
                if (indiceToken < tokens.size() && tokens.get(indiceToken).getValor().equals("END")) {
                    indiceToken++; // END
                } else {
                    throw new ErrorSintactico(tokens.get(indiceToken), "Se esperaba END después de PRINT.");
                }
            } else if (actual.getTipo().equals("IDENTIFICADOR")) {
                // Asignación falsa, saltar hasta END
                while (indiceToken < tokens.size() && !tokens.get(indiceToken).getValor().equals("END")) {
                    indiceToken++;
                }
                if (indiceToken < tokens.size()) indiceToken++; // Consumir END
            } else {
                indiceToken++; // Saltar cualquier otro token
            }
        }
    }
}

            public class ErrorSintactico extends RuntimeException {
        private String mensaje;

        public ErrorSintactico(Token token, String mensaje) {
           this.mensaje = "Error sintáctico en la línea " + token.getLinea() + ", columna " + token.getColumna() + ": " + mensaje;
        
        }
            }
}
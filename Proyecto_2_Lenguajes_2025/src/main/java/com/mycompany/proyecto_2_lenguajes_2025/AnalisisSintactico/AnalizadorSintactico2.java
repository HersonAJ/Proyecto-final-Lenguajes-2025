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
//ultima version valida que no maneja tantos errores 

public class AnalizadorSintactico2 {

    private Stack<Token> pila;
    private List<Token> tokens;
    private int indiceToken;
    public Map<String, Double> tablaSimbolos;
    private List<String> erroresSintacticos;

    public AnalizadorSintactico2(List<Token> tokens) {
        this.tokens = tokens;
        this.indiceToken = 0;
        this.pila = new Stack<>();
        this.tablaSimbolos = new HashMap<>();
        this.erroresSintacticos = new ArrayList<>();
    }

    public void parse() {
        while (indiceToken < tokens.size()) {
            Token token = tokens.get(indiceToken);

            // Lista blanca de tokens a IGNORAR (no son errores)
            if (token.getTipo().matches("COMENTARIO_LINEA|COMENTARIO_BLOQUE|SIMBOLO|ERROR")
                    || token.getValor().matches("[,.:;!?]")) {
                indiceToken++;
                continue;
            }

            try {
                // Lista blanca de estructuras RECONOCIDAS
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
                    // Cualquier otra cosa se ignora silenciosamente
                    indiceToken++;
                }
            } catch (ErrorSintactico e) {
                agregarError(e.getMessage());
                // Recuperación: saltar hasta próxima estructura válida
                while (indiceToken < tokens.size()
                        && !tokens.get(indiceToken).getValor().matches("PRINT|REPEAT|IF|END|\\$")) {
                    indiceToken++;
                }
            }
        }
    }

    private void procesarAsignacion() {
        try {
            Token identificador = tokens.get(indiceToken++);
            pila.push(identificador); // IDENTIFICADOR
            pila.push(tokens.get(indiceToken++)); // "="
            double valor = procesarExpresion(); // Procesa la expresión correctamente

            if (indiceToken >= tokens.size() || !tokens.get(indiceToken).getValor().equals("END")) {
                Token tokenError;
                if (indiceToken < tokens.size()) {
                    tokenError = tokens.get(indiceToken);
                } else if (!tokens.isEmpty()) {
                    tokenError = tokens.get(tokens.size() - 1); // último token válido
                } else {
                    tokenError = new Token("EOF", "", 0, 0); 
                }

                String mensaje = "Se esperaba END al final de la asignación.";
                agregarError(new ErrorSintactico(tokenError, mensaje).getMessage());
                return;
            }

            pila.push(tokens.get(indiceToken++)); // END
            tablaSimbolos.put(identificador.getValor(), valor);
        } catch (ErrorSintactico e) {
            agregarError(e.getMessage());
        } catch (Exception e) {
            agregarError("Error inesperado en asignación: " + e.getMessage());
        }
    }

    private double procesarExpresion() {
        try {
            Stack<Double> pilaNumerica = new Stack<>();
            Stack<String> pilaOperadores = new Stack<>();

            while (indiceToken < tokens.size()) {
                Token token = tokens.get(indiceToken);
                if (token.getTipo().equals("NUMERO_ENTERO")) {
                    pilaNumerica.push(Double.parseDouble(token.getValor()));
                    indiceToken++;
                } else if (token.getTipo().equals("IDENTIFICADOR")) {
                    if (!tablaSimbolos.containsKey(token.getValor())) {
                        throw new ErrorSintactico(token, "Variable no definida: '" + token.getValor() + "'");
                    }
                    pilaNumerica.push(tablaSimbolos.get(token.getValor()));
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
                    break; // Fin de expresión
                }
            }

            while (!pilaOperadores.isEmpty()) {
                if (pilaNumerica.size() < 2) {
                    throw new ErrorSintactico(
                            tokens.get(Math.min(indiceToken - 1, tokens.size() - 1)),
                            "Faltan operandos para el operador '" + pilaOperadores.peek() + "'"
                    );
                }
                double b = pilaNumerica.pop();
                double a = pilaNumerica.pop();
                String op = pilaOperadores.pop();

                switch (op) {
                    case "+":
                        pilaNumerica.push(a + b);
                        break;
                    case "-":
                        pilaNumerica.push(a - b);
                        break;
                    case "*":
                        pilaNumerica.push(a * b);
                        break;
                    case "/":
                        if (b == 0) {
                            throw new ErrorSintactico(
                                    tokens.get(indiceToken - 1),
                                    "División por cero"
                            );
                        }
                        pilaNumerica.push(a / b);
                        break;
                    default:
                        throw new ErrorSintactico(
                                tokens.get(indiceToken - 1),
                                "Operador inválido: '" + op + "'"
                        );
                }
            }

            if (pilaNumerica.isEmpty()) {
                throw new ErrorSintactico(
                        tokens.get(Math.min(indiceToken - 1, tokens.size() - 1)),
                        "Expresión vacía o inválida"
                );
            }

            return pilaNumerica.pop();
        } catch (ErrorSintactico e) {
            agregarError(e.getMessage());
            return 0; // Valor por defecto en caso de error
        } catch (Exception e) {
            agregarError("Error inesperado en expresión: " + e.getMessage());
            return 0;
        }
    }

    public Map<String, Double> getTablaSimbolos() {
        return tablaSimbolos;
    }

    private void procesarPrint() {
        try {
            // 1. Consumir token PRINT
            Token printToken = tokens.get(indiceToken++);

            // 2. Obtener token del valor a imprimir
            if (indiceToken >= tokens.size()) {
                throw new ErrorSintactico(printToken, "Se esperaba un valor después de PRINT.");
            }
            Token valorToken = tokens.get(indiceToken++);

            // 3. Determinar el valor a imprimir
            String valorImpresion;
            switch (valorToken.getTipo()) {
                case "LITERAL":
                    valorImpresion = valorToken.getValor().replace("\"", "");
                    break;

                case "NUMERO_ENTERO":
                    valorImpresion = valorToken.getValor();
                    break;

                case "IDENTIFICADOR":
                    if (!tablaSimbolos.containsKey(valorToken.getValor())) {
                        throw new ErrorSintactico(valorToken, "Identificador no declarado: " + valorToken.getValor());
                    }
                    valorImpresion = String.valueOf(tablaSimbolos.get(valorToken.getValor()));
                    break;

                default:
                    throw new ErrorSintactico(valorToken, "Tipo de dato no válido para PRINT.");
            }

            // 4. Mostrar en consola
            System.out.println("print: " + valorImpresion);  

            // 5. Validar END final
            if (indiceToken >= tokens.size() || !tokens.get(indiceToken).getValor().equals("END")) {
                throw new ErrorSintactico(tokens.get(indiceToken), "Se esperaba END después del valor.");
            }
            indiceToken++; // Consumir END

        } catch (ErrorSintactico e) {
            agregarError(e.getMessage());
        } catch (Exception e) {
            agregarError("Error inesperado en PRINT: " + e.getMessage());
        }
    }

    private void procesarRepeat() {
        try {
            // 1. Validar estructura básica: REPEAT <num/ID> INIT ... END
            Token repeatToken = tokens.get(indiceToken++);
            pila.push(repeatToken);

            // 2. Validar número de repeticiones (número entero o identificador)
            if (indiceToken >= tokens.size()) {
                throw new ErrorSintactico(repeatToken, "Se esperaba un número entero positivo o un identificador después de REPEAT.");
            }

            Token iteracionesToken = tokens.get(indiceToken++);
            pila.push(iteracionesToken);

            if (!iteracionesToken.getTipo().equals("NUMERO_ENTERO") && !iteracionesToken.getTipo().equals("IDENTIFICADOR")) {
                throw new ErrorSintactico(iteracionesToken, "Se esperaba un número entero o identificador después de REPEAT.");
            }

            // 3. Validar INIT
            if (indiceToken >= tokens.size() || !tokens.get(indiceToken).getValor().equals("INIT")) {
                throw new ErrorSintactico(iteracionesToken, "Se esperaba INIT después de la cantidad de repeticiones.");
            }

            Token initToken = tokens.get(indiceToken++);
            pila.push(initToken);

            // 4. Obtener número de repeticiones
            int repeticiones;
            if (iteracionesToken.getTipo().equals("NUMERO_ENTERO")) {
                repeticiones = Integer.parseInt(iteracionesToken.getValor());
                if (repeticiones <= 0) {
                    throw new ErrorSintactico(iteracionesToken, "El número de repeticiones debe ser positivo.");
                }
            } else { // IDENTIFICADOR
                if (!tablaSimbolos.containsKey(iteracionesToken.getValor())) {
                    throw new ErrorSintactico(iteracionesToken, "Identificador no declarado: '" + iteracionesToken.getValor() + "'");
                }
                repeticiones = tablaSimbolos.get(iteracionesToken.getValor()).intValue();
                if (repeticiones <= 0) {
                    throw new ErrorSintactico(iteracionesToken, "El valor del identificador debe ser positivo.");
                }
            }

            // 5. Procesar bloque (solo permite PRINT)
            int inicioBloque = indiceToken;
            for (int i = 0; i < repeticiones; i++) {
                indiceToken = inicioBloque; // Reiniciar para cada iteración
                while (indiceToken < tokens.size() && !tokens.get(indiceToken).getValor().equals("END")) {
                    Token token = tokens.get(indiceToken);
                    if (token.getValor().equals("PRINT")) {
                        procesarPrint();
                    } else {
                        throw new ErrorSintactico(token, "Instrucción no válida dentro de REPEAT. Solo se permite PRINT.");
                    }
                }
            }

            // 6. Validar END final
            if (indiceToken >= tokens.size() || !tokens.get(indiceToken).getValor().equals("END")) {
                throw new ErrorSintactico(tokens.get(Math.min(indiceToken, tokens.size() - 1)), "Se esperaba END al finalizar REPEAT.");
            }

            Token endToken = tokens.get(indiceToken++);
            pila.push(endToken);

            // 7. Limpiar pila
            pila.pop(); // END
            pila.pop(); // INIT
            pila.pop(); // Número/ID
            pila.pop(); // REPEAT

            System.out.println("REPEAT exitoso.");

        } catch (ErrorSintactico e) {
            agregarError(e.getMessage());
        } catch (NumberFormatException e) {
            agregarError("Número de repeticiones inválido: debe ser un entero positivo.");
        } catch (Exception e) {
            agregarError("Error inesperado en REPEAT: " + e.getMessage());
        }
    }

    private void procesarCondicional() {
        try {
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
                        if (indiceToken < tokens.size()) {
                            indiceToken++; // Cadena
                        }
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
                        if (indiceToken < tokens.size()) {
                            indiceToken++; // Consumir END
                        }
                    } else {
                        indiceToken++; // Saltar cualquier otro token
                    }
                }
            }

        } catch (ErrorSintactico e) {
            agregarError(e.getMessage());
        } catch (Exception e) {
            agregarError("Error inesperado en condicional IF: " + e.getMessage());
        }
    }

    public List<String> mostrarErrores() {
        return erroresSintacticos;
    }

    private void agregarError(String mensaje) {
        erroresSintacticos.add(mensaje);
    }

    public class ErrorSintactico extends RuntimeException {

        private final String mensaje;

        public ErrorSintactico(Token token, String mensaje) {
            if (token != null) {
                this.mensaje = "Error sintáctico en la línea " + token.getLinea() + ", columna " + token.getColumna() + ": " + mensaje;
            } else {
                this.mensaje = "Error sintáctico: " + mensaje;
            }
        }

        @Override
        public String getMessage() {
            return mensaje;
        }

        @Override
        public String toString() {
            return mensaje;
        }
    }
}

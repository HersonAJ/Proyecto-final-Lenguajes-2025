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
public class ParseCondicional {

    private final List<Token> tokens;
    private int posicion;
    private String conditionalValue = "";
    
    public ParseCondicional(List<Token> tokens) {
        this.tokens = tokens;
        this.posicion = 0;
    }
    
    public boolean analizar() {
        Stack<String> pila = new Stack<>();
        // Símbolo inicial
        pila.push("CONDICIONAL");
        
        while (!pila.empty()) {
            String simbolo = pila.peek();
            
            if (isNonTerminal(simbolo)) {
                pila.pop();
                switch (simbolo) {
                    case "CONDICIONAL":
                        pila.push("END");
                        pila.push("PRINT_OPTIONAL");
                        pila.push("THEN");
                        pila.push("BOOLEAN");
                        pila.push("IF");
                        break;
                    case "BOOLEAN":
                        if (posicion < tokens.size()) {
                            Token token = tokens.get(posicion);
                            if ("PALABRA_RESERVADA".equals(token.getTipo()) &&
                                (token.getValor().equals("TRUE") || token.getValor().equals("FALSE"))) {
                                // se empuja el terminal correspondiente y almacenamos el valor de la condición
                                pila.push(token.getValor());
                                conditionalValue = token.getValor();
                            } else {
                                reportError("Se esperaba TRUE o FALSE en la estructura CONDICIONAL.");
                                return false;
                            }
                        } else {
                            reportError("No se encontró token para BOOLEAN en la estructura CONDICIONAL.");
                            return false;
                        }
                        break;
                    case "PRINT_OPTIONAL":
                        if (posicion < tokens.size() && tokens.get(posicion).getValor().equals("PRINT")) {
                            // Si se detecta PRINT, se procesa la estructura PRINT
                            pila.pop();  
                            if (!parsePrint()) {
                                reportError("Error en la estructura PRINT dentro de CONDICIONAL.");
                                return false;
                            }
                        } else {
                            // Producción ε: se elimina PRINT_OPTIONAL sin consumir token
                            pila.pop();
                        }
                        break;
                    default:
                        reportError("No se reconoce el no terminal: " + simbolo);
                        return false;
                } // fin switch
            } else { // Símbolo terminal
                if (posicion < tokens.size() && tokens.get(posicion).getValor().equals(simbolo)) {
                    pila.pop();
                    posicion++;
                } else {
                    if (posicion < tokens.size()) {
                        reportError("Token inesperado. Se esperaba '" + simbolo + "' y se encontró '" 
                                    + tokens.get(posicion).getValor() + "'.");
                    } else {
                        reportError("Se esperaba '" + simbolo + "', pero no hay más tokens.");
                    }
                    return false;
                }
            }
        } // fin while
        
        // Si quedó un token "END" (cierre del condicional) pendiente, se consume aquí.
        if (posicion < tokens.size() && tokens.get(posicion).getValor().equals("END")) {
            posicion++;
        }
        
        System.out.println("Estructura CONDICIONAL reconocida correctamente.");
        return true;
    }
    

    private boolean isNonTerminal(String simbolo) {
        return simbolo.equals("CONDICIONAL") 
            || simbolo.equals("BOOLEAN") 
            || simbolo.equals("PRINT_OPTIONAL");
    }
    

    private void reportError(String mensaje) {
        System.err.println("Error Sintáctico: " + mensaje);
    }
    

    private boolean parsePrint() {
        ParsePrint parserPrint = new ParsePrint(tokens.subList(posicion, tokens.size()));
        boolean exito = parserPrint.analizar();
        posicion += parserPrint.getPosicion();
        if (exito && conditionalValue.equals("TRUE")) {
     
        }
        return exito;
    }
    

    public int getPosicion() {
        return this.posicion;
    }
}
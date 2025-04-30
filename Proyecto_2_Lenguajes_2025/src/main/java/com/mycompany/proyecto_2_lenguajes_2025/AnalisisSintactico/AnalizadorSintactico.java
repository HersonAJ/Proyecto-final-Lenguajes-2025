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
public class AnalizadorSintactico {

    private final List<Token> tokens;
    private int posicion;

    public AnalizadorSintactico(List<Token> tokens) {
        this.tokens = tokens;
        this.posicion = 0; // Inicializar la posición al inicio
    }

    public void analizar() {
        while (posicion < tokens.size()) {
            Token tokenActual = tokens.get(posicion);
            System.out.println("Procesando token en posición: " + posicion + ", valor: '" + tokenActual.getValor() + "'.");

            // Verificar si se trata de una asignación: debe comenzar con un identificador (que comience con '$')
            // y el siguiente token debe ser "=".
            if (tokenActual.getTipo().equals("IDENTIFICADOR") &&
                (posicion + 1) < tokens.size() &&
                tokens.get(posicion + 1).getValor().equals("=")) {
                System.out.println("Intentando reconocer estructura ASIGNACIÓN...");
                if (!parseAsignacion()) {
                    System.err.println("Error: Estructura ASIGNACIÓN inválida en posición: " + posicion + ".");
                } else {
                    System.out.println("Estructura ASIGNACIÓN reconocida correctamente.");
                }
            } else if (tokenActual.getValor().equals("PRINT")) {
                System.out.println("Intentando reconocer estructura PRINT...");
                if (!parsePrint()) {
                    System.err.println("Error: Estructura PRINT inválida en posición: " + posicion + ".");
                } else {
                    System.out.println("Estructura PRINT reconocida: PRINT seguido de un literal, número o identificador y END.");
                }
            } else if (tokenActual.getValor().equals("REPEAT")) {
                System.out.println("Intentando reconocer estructura REPEAT...");
                if (!parseRepeat()) {
                    System.err.println("Error: Estructura REPEAT inválida en posición: " + posicion + ".");
                } else {
                    System.out.println("Estructura REPEAT reconocida correctamente.");
                }
            } else if (tokenActual.getValor().equals("IF")) {
                System.out.println("Intentando reconocer estructura CONDICIONAL...");
                if (!parseCondicional()) {
                    System.err.println("Error: Estructura CONDICIONAL inválida en posición: " + posicion + ".");
                } else {
                    System.out.println("Estructura CONDICIONAL reconocida correctamente.");
                }
            } else if (tokenActual.getValor().equals("(") ||
                       tokenActual.getTipo().equals("NUMERO_ENTERO") ||
                       tokenActual.getTipo().equals("IDENTIFICADOR")) {
                // Si el token es '(' o un token de tipo NUMERO_ENTERO o IDENTIFICADOR, se asume el inicio de una EXPRESIÓN.
                System.out.println("Intentando reconocer estructura EXPRESIÓN...");
                if (!parseExpresion()) {
                    System.err.println("Error: Estructura EXPRESIÓN inválida en posición: " + posicion + ".");
                } else {
                    System.out.println("Estructura EXPRESIÓN reconocida correctamente.");
                }
            } else {
                System.err.println("Error Sintáctico: Token no esperado '" + tokenActual.getValor() + "' en posición: " + posicion + ".");
                sincronizar();
                System.out.println("Sincronización realizada. Nueva posición: " + posicion + ".");
            }
        }
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
        // Se considera relevante reanudar la lectura cuando se encuentre el inicio de alguna estructura.
        while (posicion < tokens.size()) {
            String valorToken = tokens.get(posicion).getValor();
            if ("PRINT".equals(valorToken) ||
                "REPEAT".equals(valorToken) ||
                "IF".equals(valorToken) ||
                valorToken.equals("(") ||
                tokens.get(posicion).getTipo().equals("NUMERO_ENTERO") ||
                tokens.get(posicion).getTipo().equals("IDENTIFICADOR")) {
                break;
            }
            posicion++; // Avanzar posición
        }
        if (posicion < tokens.size()) {
            posicion++;
        }
    }
}


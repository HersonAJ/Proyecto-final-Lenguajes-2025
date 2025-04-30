/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico;

import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.util.ArrayList;
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

            switch (tokenActual.getValor()) {
                case "PRINT":
                    System.out.println("Intentando reconocer estructura PRINT...");
                    if (!parsePrint()) {
                        System.err.println("Error: Estructura PRINT inválida en posición: " + posicion + ".");
                    } else {
                        System.out.println("Estructura PRINT reconocida: PRINT seguido de un literal, número o identificador y END.");
                    }
                    break;

                default:
                    System.err.println("Error Sintáctico: Token no esperado '" + tokenActual.getValor() + "' en posición: " + posicion + ".");
                    sincronizar();
                    System.out.println("Sincronización realizada. Nueva posición: " + posicion + ".");
                    break;
            }
        }
    }

    private boolean parsePrint() {
        ParsePrint parser = new ParsePrint(tokens.subList(posicion, tokens.size()));
        boolean exito = parser.analizar();
        posicion += parser.getPosicion();
        return exito;
    }

    private void sincronizar() {

        while (posicion < tokens.size()) {
            String valorToken = tokens.get(posicion).getValor();
            if ("PRINT".equals(valorToken) || "REPEAT".equals(valorToken) || "IF".equals(valorToken)) {
                break;
            }
            posicion++; // Avanzar posición
        }

        if (posicion < tokens.size()) {
            posicion++;
        }
    }

    // Método principal para pruebas
    public static void main(String[] args) {
        // Ejemplo de lista de tokens para el analizador
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token("PALABRA_RESERVADA", "PRINT", 1, 1));
        tokens.add(new Token("LITERAL", "\"Hola Mundo\"", 1, 7));
        tokens.add(new Token("PALABRA_RESERVADA", "END", 1, 19));
        tokens.add(new Token("PALABRA_RESERVADA", "PRINT", 2, 1));
        tokens.add(new Token("IDENTIFICADOR", "$variable", 2, 7));
        tokens.add(new Token("PALABRA_RESERVADA", "END", 2, 17));
        tokens.add(new Token("PALABRA_RESERVADA", "REPEAT", 3, 1)); // Token para probar sincronización

        // Crear instancia del analizador sintáctico
        AnalizadorSintactico analizador = new AnalizadorSintactico(tokens);

        // Realizar el análisis
        System.out.println("Iniciando análisis sintáctico...");
        analizador.analizar();
        System.out.println("Análisis completado.");
    }
}

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
public class ParsePrint {

    private final List<Token> tokens; 
    private int posicion; 

    public ParsePrint(List<Token> tokens) {
        this.tokens = tokens;
        this.posicion = 0; 
    }

    public boolean analizar() {
        // Comprobar si el primer token es "PRINT"
        if (posicion < tokens.size() && "PALABRA_RESERVADA".equals(tokens.get(posicion).getTipo()) &&
            "PRINT".equals(tokens.get(posicion).getValor())) {
            posicion++;

            if (posicion < tokens.size() &&
                ("LITERAL".equals(tokens.get(posicion).getTipo()) ||
                 "NUMERO_ENTERO".equals(tokens.get(posicion).getTipo()) ||
                 "IDENTIFICADOR".equals(tokens.get(posicion).getTipo()))) {
                posicion++; 

                if (posicion < tokens.size() &&
                    "PALABRA_RESERVADA".equals(tokens.get(posicion).getTipo()) &&
                    "END".equals(tokens.get(posicion).getValor())) {
                    posicion++; 
                    System.out.println("Estructura PRINT reconocida correctamente.");
                    return true; 
                } else {
                    reportarError("Se esperaba la palabra reservada END después del literal, número o identificador.");
                    sincronizar();
                    return false;
                }
            } else {
                reportarError("Se esperaba un Literal, Número o Identificador después de PRINT.");
                sincronizar();
                return false;
            }
        } else {
            reportarError("Se esperaba el token PRINT al inicio.");
            sincronizar();
            return false;
        }
    }


    public int getPosicion() {
        return this.posicion;
    }

    private void reportarError(String mensaje) {
        System.err.println("Error Sintáctico: " + mensaje);
    }

    private void sincronizar() {
        while (posicion < tokens.size() &&
               !esTokenValido(tokens.get(posicion).getValor())) {
            posicion++;
        }
    }

    private boolean esTokenValido(String valor) {
        return "PRINT".equals(valor) || "REPEAT".equals(valor) || "IF".equals(valor) || "ID".equals(valor);
    }
}

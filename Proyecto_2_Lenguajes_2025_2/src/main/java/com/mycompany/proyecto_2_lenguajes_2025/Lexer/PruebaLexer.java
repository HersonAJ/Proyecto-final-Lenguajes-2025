/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.Lexer;

import java.io.StringReader;

/**
 *
 * @author herson
 */
public class PruebaLexer {

    public static void main(String[] args) {
        String codigoPrueba = """
                # Comentario de prueba
                PRINT "Hola mundo" + 123 - 456
                $variable1 = 789
                /* Comentario de varias líneas
                que puede continuar aquí */
                TRUE THEN FALSE
                """;

        // Crear el lexer con el código de prueba
        Lexer lexer = new Lexer(new StringReader(codigoPrueba));

        Token token;
        try {
            System.out.println("Tokens generados:");
            while ((token = lexer.yylex()) != null) {
                System.out.println(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


package com.mycompany.proyecto_2_lenguajes_2025.Backend.AnalisisLexico;

import com.mycompany.proyecto_2_lenguajes_2025.Lexer.ErrorToken;
import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Lexer;
import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;



public class AnalizadorLexico {

    // Método para realizar el análisis léxico
    public List<Token> realizarAnalisis(String textoEntrada, List<ErrorToken> errores) {
        Lexer lexer = new Lexer(new StringReader(textoEntrada));
        List<Token> tokens = new ArrayList<>(); 

        try {
            Token token;
            while ((token = lexer.yylex()) != null) {
                if ("ERROR".equals(token.getTipo())) {
                    errores.add(new ErrorToken("Token no reconocido: " + token.getValor(), token.getLinea(), token.getColumna()));
                } else {
                    tokens.add(token);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            errores.add(new ErrorToken("Error durante el análisis léxico.", -1, -1));
        }

        return tokens;
    }
}


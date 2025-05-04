%%
%class Lexer
%unicode
%line
%column
%type Token
%public

%{
package com.mycompany.proyecto_2_lenguajes_2025.Lexer;
import com.mycompany.proyecto_2_lenguajes_2025.Token;
%}

%%

/* Espacios y separadores ignorados */
[\t\n\r\f ]+               { /* Ignorar separadores */ }

/* Comentarios de una sola línea */
"#"[^\n]*                  { return new Token("COMENTARIO_LINEA", yytext(), yyline + 1, yycolumn + 1); }

/* Comentarios en bloque */
"/*"([^*]|\*+[^*/])*\*+"/" { return new Token("COMENTARIO_BLOQUE", yytext(), yyline + 1, yycolumn + 1); }

/* Palabras reservadas */
"PRINT"|"END"|"REPEAT"|"INIT"|"IF"|"TRUE"|"FALSE"|"THEN"
                            { return new Token("PALABRA_RESERVADA", yytext(), yyline + 1, yycolumn + 1); }

/* Operadores aritméticos y paréntesis */
"+"|"-"|"*"|"/"|"("|")"     { return new Token("OPERADOR_ARITMETICO", yytext(), yyline + 1, yycolumn + 1); }

/* Operador de asignación */
"="                         { return new Token("OPERADOR_ASIGNACION", yytext(), yyline + 1, yycolumn + 1); }

/* Número entero con o sin signo */
[+-]?([1-9][0-9]*|0)        { return new Token("NUMERO_ENTERO", yytext(), yyline + 1, yycolumn + 1); }

/* Identificador que inicia con $ */
\$[a-zA-Z0-9_-]+            { return new Token("IDENTIFICADOR", yytext(), yyline + 1, yycolumn + 1); }

/* Literales (cadenas entre comillas, sin saltos de línea) */
\"([^\"\n])*\"             { return new Token("LITERAL", yytext(), yyline + 1, yycolumn + 1); }

/* Caracteres especiales simples (puedes ampliar según necesidad) */
[.,:;!?]                    { return new Token("SIMBOLO", yytext(), yyline + 1, yycolumn + 1); }

/* Manejo de errores */
.                           { return new Token("ERROR", yytext(), yyline + 1, yycolumn + 1); }

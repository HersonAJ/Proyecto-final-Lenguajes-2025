package com.mycompany.proyecto_2_lenguajes_2025.Lexer;

public class ErrorToken {

    private String mensaje;
    private int linea;
    private int columna;

    public ErrorToken(String mensaje, int linea, int columna) {
        this.mensaje = mensaje;
        this.linea = linea;
        this.columna = columna;
    }

    public String getMensaje() {
        return mensaje;
    }
    public int getLinea() {
        return linea;
    }
    public int getColumna() {
        return columna;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public void setLinea(int linea) {
        this.linea = linea;
    }
    public void setColumna(int columna) {
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "ErrorToken{" +
                "mensaje: Error lexico  = '" + mensaje + '\'' +
                ", linea = " + linea +
                ", columna = " + columna +
                '}';
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.Frontend;

import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

/**
 *
 * @author herson
 */
public class Nuevo {
    
    private JTextPane textPane;
    private JTextArea errorTextArea;
    private JScrollPane errorScrollPane;
    private JButton botonSintactico;
    private List<Token> tokens;
    
    public Nuevo(JTextPane textPane, JTextArea errorTextArea, JScrollPane errorScrollPane, JButton botonSintactico, List<Token> tokens) {
        this.textPane = textPane;
        this.errorTextArea = errorTextArea;
        this.errorScrollPane = errorScrollPane;
        this.botonSintactico = botonSintactico;
        this.tokens = tokens;
    }
    
    public void limpiarInterfaz() {
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea iniciar un nuevo archivo? Se perderán los cambios no guardados.",
                "Nuevo Archivo", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            //limpiar el area de texto
            textPane.setText("");
            
            //limpiar errores mostrados en la pantalla
            errorTextArea.setText("");
            errorScrollPane.setVisible(false);
            
            //limpiar la lista de tokens y reiniciar el boton de analisis
            if (tokens != null) {
                tokens.clear();
            }
            botonSintactico.setEnabled(false);
            
            JOptionPane.showMessageDialog(null, "Interfaz reiniciada correctamente.", "Nuevo Archivo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

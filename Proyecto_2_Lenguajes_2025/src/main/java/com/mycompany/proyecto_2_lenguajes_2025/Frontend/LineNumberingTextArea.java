/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.Frontend;

import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author herson
 */
public class LineNumberingTextArea extends JTextPane {
    private JTextPane textPane;

    public LineNumberingTextArea(JTextPane textPane) {
        this.textPane = textPane;
        textPane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLineNumbers();
            }
        });
        updateLineNumbers();
    }

    private void updateLineNumbers() {
        StringBuilder lineNumbers = new StringBuilder();
        int lines = textPane.getDocument().getDefaultRootElement().getElementCount();
        for (int i = 1; i <= lines; i++) {
            lineNumbers.append(i).append(System.lineSeparator());
        }
        setText(lineNumbers.toString());
    }
}

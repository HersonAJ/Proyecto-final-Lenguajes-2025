/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.Frontend;

import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author herson
 */
public class ReporteToken extends JFrame {

    private JTable tablaTokens;
    private DefaultTableModel modeloTabla;

    public ReporteToken(List<Token> tokens) {
        setTitle("Reporte de Tokens");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear modelo de tabla no editable
        modeloTabla = new DefaultTableModel(new Object[]{"Tipo de Token", "Lexema", "Fila", "Columna"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Crear tabla
        tablaTokens = new JTable(modeloTabla);
        tablaTokens.setRowHeight(25); 
        tablaTokens.setFillsViewportHeight(true); 

        JTableHeader header = tablaTokens.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(60, 63, 65));
        header.setForeground(Color.WHITE);

        TableColumnModel columnModel = tablaTokens.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150); // Tipo de Token
        columnModel.getColumn(1).setPreferredWidth(250); // Lexema
        columnModel.getColumn(2).setPreferredWidth(80);  // Fila
        columnModel.getColumn(3).setPreferredWidth(80);  // Columna

        // Agregar los tokens
        for (Token token : tokens) {
            modeloTabla.addRow(new Object[]{
                token.getTipo(),
                token.getValor(),
                token.getLinea(),
                token.getColumna()
            });
        }

        JScrollPane scrollPane = new JScrollPane(tablaTokens);
        add(scrollPane, BorderLayout.CENTER);
    }
}


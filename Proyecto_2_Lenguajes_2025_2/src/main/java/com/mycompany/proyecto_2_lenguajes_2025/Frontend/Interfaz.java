/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_2_lenguajes_2025.Frontend;

import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico.AnalizadorSintactico;
import com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico.AnalizadorSintactico2;
import com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico.GeneradorSalida;
import com.mycompany.proyecto_2_lenguajes_2025.AnalisisSintactico.GeneradorSalida2;
import com.mycompany.proyecto_2_lenguajes_2025.Backend.AnalisisLexico.AnalizadorLexico;
import com.mycompany.proyecto_2_lenguajes_2025.Lexer.ErrorToken;
import com.mycompany.proyecto_2_lenguajes_2025.Lexer.Token;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.undo.UndoManager;

/**
 *
 * @author herson
 */
//este es del proyecto version 2
public class Interfaz extends JFrame {

    private JTextPane textPane1;
    private JLabel posLabel;
    private JLabel errorLabel;
    private JButton boton;
    private JTextArea errorTextArea;
    private JScrollPane errorScrollPane;
    private List<Token> tokens;
    private JButton botonSintactico;
    private final UndoManager undoManager = new UndoManager();

    public Interfaz() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setTitle("Analizador Lexico Sintactico");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Menú Archivo
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem nuevoItem = new JMenuItem("Nuevo");
        JMenuItem abrirItem = new JMenuItem("Abrir");
        JMenuItem guardarItem = new JMenuItem("Guardar");
        JMenuItem guardarComoItem = new JMenuItem("Guardar como");
        archivoMenu.add(nuevoItem);
        archivoMenu.add(abrirItem);
        archivoMenu.add(guardarItem);
        archivoMenu.add(guardarComoItem);

        // Menú Editar
        JMenu editarMenu = new JMenu("Editar");
        JMenuItem deshacerItem = new JMenuItem("Deshacer");
        JMenuItem rehacerItem = new JMenuItem("Rehacer");
        editarMenu.add(deshacerItem);
        editarMenu.add(rehacerItem);

        // Menú Ayuda
        JMenu ayudaMenu = new JMenu("Ayuda");
        JMenuItem acercaDeItem = new JMenuItem("Acerca de");
        ayudaMenu.add(acercaDeItem);

        JMenu reportesMenu = new JMenu("Reportes");
        JMenuItem reporteTokensItem = new JMenuItem("Reporte de Tokens");
        reportesMenu.add(reporteTokensItem);

        // Añadir menús a la barra de menú
        menuBar.add(archivoMenu);
        menuBar.add(editarMenu);
        menuBar.add(ayudaMenu);
        menuBar.add(reportesMenu);
        setJMenuBar(menuBar);

        // Crear el JTextPane con números de línea
        textPane1 = new JTextPane();
        LineNumberingTextArea lineNumberingTextPane = new LineNumberingTextArea(textPane1);
        lineNumberingTextPane.setEditable(false);

        // Crear un JScrollPane que contenga ambos JTextPanes
        JScrollPane scrollPane = new JScrollPane(textPane1);
        scrollPane.setRowHeaderView(lineNumberingTextPane);
        textPane1.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit()));

        // Crear la etiqueta para la posición del cursor
        posLabel = new JLabel("Fila 1, Palabra 1");

        // Crear la etiqueta para el mensaje de error
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);

        // Crear el área para errores
        errorTextArea = new JTextArea();
        errorTextArea.setEditable(false);
        errorTextArea.setForeground(Color.RED);
        errorScrollPane = new JScrollPane(errorTextArea);
        errorScrollPane.setVisible(false); // Oculto inicialmente

        // Actualizar la posición del cursor
        textPane1.addCaretListener(e -> {
            int caretPosition = textPane1.getCaretPosition();
            int line = 0, column = 0;
            try {
                line = textPane1.getDocument().getDefaultRootElement().getElementIndex(caretPosition) + 1;
                int start = textPane1.getDocument().getDefaultRootElement().getElement(line - 1).getStartOffset();

                column = caretPosition - start + 1;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            posLabel.setText("Fila " + line + ", Columna " + column);
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Configurar scrollPane
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // Configurar botón "Analizar"
        boton = new JButton("Analisis Lexico");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(boton, gbc);

        botonSintactico = new JButton("Análisis Sintáctico");
        botonSintactico.setEnabled(false);
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(botonSintactico, gbc);

        // Añadir el JLabel para la posición del cursor
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(posLabel, gbc);

        // Añadir el JScrollPane del TextArea de errores
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        add(errorScrollPane, gbc);

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarAnalisisLexico();
            }
        });

        reporteTokensItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirReporteTokens();
            }
        });

        //analisis sintactico
        botonSintactico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarAnalisisSintactico();
            }
        });

        //cargar archivo
        abrirItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivo();
            }
        });

        Guardar gestorArchivo = new Guardar();

        guardarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestorArchivo.guardarArchivo(textPane1);
            }
        });

        guardarComoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestorArchivo.guardarComoArchivo(textPane1);
            }
        });

        acercaDeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Analizador Léxico Sintáctico\nCurso: Lenguajes Formales y De Programación\nDesarrollador: Herson Isaías Aguilar Juárez",
                        "Acerca de", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Definir UndoManager en Interfaz
        deshacerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay más acciones para deshacer.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        rehacerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo()) {
                    undoManager.redo();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay más acciones para rehacer.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        Nuevo gestorNuevo = new Nuevo(textPane1, errorTextArea, errorScrollPane, botonSintactico, tokens);
        nuevoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestorNuevo.limpiarInterfaz();
            }
        });

    }

    private void realizarAnalisisLexico() {
        String textoEntrada = textPane1.getText().trim();
        AnalizadorLexico analizador = new AnalizadorLexico();
        List<ErrorToken> errores = new ArrayList<>();
        errores.clear();
        tokens = analizador.realizarAnalisis(textoEntrada, errores);

        if (!errores.isEmpty()) {
            StringBuilder erroresTexto = new StringBuilder();
            for (ErrorToken error : errores) {
                erroresTexto.append(error.toString()).append("\n");
            }
            mostrarErrores(erroresTexto.toString());
        } else {
            mostrarErrores(null);
        }
        if (!errores.isEmpty()) {
            botonSintactico.setEnabled(false);
        } else {
            botonSintactico.setEnabled(true);
        }
    }

    // Método para mostrar errores
    public void mostrarErrores(String errores) {
        if (errores == null || errores.isEmpty()) {
            errorScrollPane.setVisible(false);
        } else {
            errorTextArea.setText(errores);
            errorScrollPane.setVisible(true);
        }
        this.revalidate();
        this.repaint();
    }

    private void abrirReporteTokens() {
        if (tokens != null && !tokens.isEmpty()) {
            if (errorScrollPane.isVisible()) {
                JOptionPane.showMessageDialog(this, "No se puede mostrar el reporte de tokens debido a errores léxicos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                SwingUtilities.invokeLater(() -> new ReporteToken(tokens).setVisible(true));
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay tokens disponibles para mostrar.", "Reporte de Tokens", JOptionPane.INFORMATION_MESSAGE);
        }
    }
/*
private void realizarAnalisisSintactico() {
    if (tokens != null && !tokens.isEmpty()) {
        AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(tokens);
        try {
            analizadorSintactico.analizar();

            // No se requiere una tabla de símbolos aún, pero podríamos integrarla en futuras fases
            System.out.println("Análisis sintáctico completado sin errores.");

            // Generar archivo solo si no hay errores sintácticos
            GeneradorSalida generador = new GeneradorSalida(tokens, new HashMap<>());
            generador.generarArchivo();

        } catch (Exception e) {
            mostrarErrores(e.getMessage()); // Captura errores sintácticos si ocurren
        }
    } else {
        JOptionPane.showMessageDialog(this, "No hay tokens disponibles para analizar.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}*/
private void realizarAnalisisSintactico() {
    if (tokens != null && !tokens.isEmpty()) {
        AnalizadorSintactico2 analizadorSintactico = new AnalizadorSintactico2(tokens);
        try {
            analizadorSintactico.parse();
            List<String> errores = analizadorSintactico.mostrarErrores();

            if (!errores.isEmpty()) {
                System.out.println("Errores encontrados:");
                System.out.println(String.join("\n", errores)); // Mostrar los errores en consola para depuración
                mostrarErrores(String.join("\n", errores));
                return;
            } else {
                System.out.println("No se encontraron errores sintácticos."); // Mensaje de depuración
                mostrarErrores(""); // Limpiar área de errores si no hay problemas
            }

            // Convertir tabla de símbolos a String
            Map<String, String> tablaSimbolosString = new HashMap<>();
            for (Map.Entry<String, Double> entry : analizadorSintactico.getTablaSimbolos().entrySet()) {
                tablaSimbolosString.put(entry.getKey(), String.valueOf(entry.getValue()));
            }

            System.out.println("Tabla de símbolos:");
            for (Map.Entry<String, String> entry : tablaSimbolosString.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            // **Solo lanzar GeneradorSalida2 si NO hay errores sintácticos**
            if (errores.isEmpty()) {
                System.out.println("GeneradorSalida2 se ejecutará."); // Mensaje para verificar ejecución
                GeneradorSalida2 generador = new GeneradorSalida2(tokens, tablaSimbolosString);
                generador.generarArchivo();
            } else {
                System.out.println("GeneradorSalida2 NO se ejecutará debido a errores sintácticos.");
            }

        } catch (AnalizadorSintactico2.ErrorSintactico e) {
            System.out.println("Se capturó un ErrorSintáctico: " + e.getMessage());
            mostrarErrores(e.getMessage());
        }
    } else {
        JOptionPane.showMessageDialog(this, "No hay tokens disponibles para analizar.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}



    private void abrirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione el archivo de entrada");

        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                textPane1.setText("");
                String linea;
                StringBuilder contenido = new StringBuilder();

                while ((linea = reader.readLine()) != null) {
                    contenido.append(linea).append("\n");
                }
                textPane1.setText(contenido.toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al abrir el archivo: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

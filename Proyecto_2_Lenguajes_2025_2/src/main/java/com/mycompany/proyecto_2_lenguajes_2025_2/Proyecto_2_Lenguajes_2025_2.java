/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyecto_2_lenguajes_2025_2;

import com.mycompany.proyecto_2_lenguajes_2025.Frontend.Interfaz;
import javax.swing.SwingUtilities;

/**
 *
 * @author herson
 */
public class Proyecto_2_Lenguajes_2025_2 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Interfaz interfaz = new Interfaz();
                interfaz.setVisible(true);
            }
        });
    }
}

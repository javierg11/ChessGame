package ConstantesComunes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;

public class Botones {
	// Método para añadir efecto hover a los botones
    public static void addHoverEffect(JButton button, Color normal, Color hover) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hover);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normal);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
    
    public static JButton crearBotonBasico(String texto, Color fondo, Color textoColor, int fontSize) {
        JButton boton = new JButton(texto);
        boton.setBackground(fondo);
        boton.setForeground(textoColor);
        boton.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        return boton;
    }
}

package ConstantesComunes;

import java.awt.Color;

import java.util.function.Consumer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Partida.FinPartida;

public class CreacionJOptionPanelDialog {

	public static void mensajeDeTextoConRetardo(
	        String texto, String titulo, String[] opciones, Consumer<Integer> callback) {
	    Icon icono = new ImageIcon(FinPartida.class.getResource("/imagesPiezas/wP.png"));

	    new javax.swing.Timer(500, e -> {
	        // Guarda el color original para restaurarlo después
	        Color oldBg = UIManager.getColor("Panel.background");
	        Color oldOptionPaneBg = UIManager.getColor("OptionPane.background");
	        Color oldButtonBg = UIManager.getColor("Button.background");

	        // Cambia el fondo del JOptionPane y de los botones
	        UIManager.put("Panel.background", Colores.CASILLAS_NEGRAS);
	        UIManager.put("OptionPane.background", Colores.CASILLAS_NEGRAS);
	        UIManager.put("Button.background", Colores.CASILLAS_BLANCAS);

	        int seleccion = JOptionPane.showOptionDialog(
	            null,
	            texto,
	            titulo,
	            JOptionPane.DEFAULT_OPTION,
	            JOptionPane.INFORMATION_MESSAGE,
	            icono,
	            opciones,
	            opciones.length > 0 ? opciones[0] : null
	        );

	        // Restaura los colores originales
	        UIManager.put("Panel.background", oldBg);
	        UIManager.put("OptionPane.background", oldOptionPaneBg);
	        UIManager.put("Button.background", oldButtonBg);

	        int resultado;
	        if (seleccion == -1) {
	            resultado = opciones.length - 1;
	        } else if (seleccion >= 0 && seleccion < opciones.length) {
	            resultado = seleccion;
	        } else {
	            resultado = opciones.length - 1;
	        }
	        callback.accept(resultado);
	        ((javax.swing.Timer) e.getSource()).stop();
	    }).start();
	}


}
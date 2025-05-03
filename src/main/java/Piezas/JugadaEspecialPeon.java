package Piezas;

import ConstantesComunes.Colores;
import Tablero.TableroAjedrez;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JugadaEspecialPeon {
	public static String coronarPeon(int fila, int columna, String ficha, JButton[][]casillas) {
		// Verifica si es una casilla de coronación
		JButton casilla=casillas[fila][columna];
		String color = ficha.substring(0, 1);
	    if ((fila == 0 && ficha.equals("wP")) || (fila == 7 && ficha.equals("bP"))) {
	    	 String[] tipos = {"D", "T", "A", "C"};

	    	    // Panel vertical para contener los botones con solo imagen
	    	    JPanel panel = new JPanel();
	    	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    	    panel.setBackground(Color.WHITE); // Opcional: fondo blanco para mejor contraste

	    	    final String[] seleccion = {null};

	    	    for (int i = 0; i < tipos.length; i++) {
	    	        String pieza = color + tipos[i];
	    	        ImageIcon iconoOriginal = new ImageIcon(TableroAjedrez.class.getResource("/imagesPiezas/" + pieza + ".png"));
	    	        Image imagen = iconoOriginal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	    	        JButton boton = new JButton(new ImageIcon(imagen));
	    	        boton.setFocusable(false);
	    	        boton.setBorderPainted(false);
	    	        boton.setContentAreaFilled(false);
	    	        boton.setOpaque(false);

	    	        boton.addActionListener(new ActionListener() {
	    	            @Override
	    	            public void actionPerformed(ActionEvent e) {
	    	                seleccion[0] = pieza;
	    	                Window window = SwingUtilities.getWindowAncestor(panel);
	    	                if (window != null) {
	    	                    window.dispose();
	    	                }
	    	            }
	    	        });

	    	        // Añade un poco de espacio entre botones para mejor visual
	    	        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    	        panel.add(boton);
	    	        panel.add(Box.createRigidArea(new Dimension(0, 5)));
	    	    }

	    	    // Crear diálogo modal
	    	    JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(casilla), "Coronación de peón", Dialog.ModalityType.APPLICATION_MODAL);
	    	    dialog.getContentPane().add(panel);
	    	    dialog.pack();
	    	    dialog.setLocationRelativeTo(casilla);
	    	    dialog.setResizable(false);
	    	    dialog.setVisible(true);

	    	    // Si el usuario cierra sin elegir, por defecto dama
	    	    if (seleccion[0] == null) {
	    	        return color + "D";
	    	    }
	    	    return seleccion[0];
	    }
	    else {
	        return ficha;
	    }

	}

	public static void comerAlPaso(int filaOrigen, int filaDestino, String ficha, JButton[][] casillas, int columna,
			HashMap<Integer, String> jugadas) {

		Integer claveMasNueva = jugadas.size();
		String valorMasNuevo = jugadas.get(claveMasNueva);

		// Regex para saber si blancas o negras han hecho un movimiento doble con el
		// peon
		String regexBlancas = "wP-6\\d-4\\d";
		String regexNegras = "bP-1\\d-3\\d";

		// Este comprueba si el regex es correcto, y si hay un peon de color contrario
		// al aldo del peon (esto se hace para ver si es posible ejecutar
		// el comer al paso)

		boolean puedeComerAlPaso = false;

		if (valorMasNuevo.matches(regexBlancas)) {
			// Peón blanco: busca peón negro a izquierda o derecha
			boolean izquierda = (columna - 1 >= 0) && casillas[filaDestino][columna - 1].getText().equals("bP");
			boolean derecha = (columna + 1 < 8) && casillas[filaDestino][columna + 1].getText().equals("bP");
			puedeComerAlPaso = izquierda || derecha;
		} else if (valorMasNuevo.matches(regexNegras)) {
			// Peón negro: busca peón blanco a derecha o izquierda
			boolean derecha = (columna + 1 < 8) && casillas[filaDestino][columna + 1].getText().equals("wP");
			boolean izquierda = (columna - 1 >= 0) && casillas[filaDestino][columna - 1].getText().equals("wP");
			puedeComerAlPaso = derecha || izquierda;
		}

		if (puedeComerAlPaso) {
			// Este if sirve para saber a qué color se le hace el comer al paso, creando un
			// peon temporal que si no se come en ese turno desaparece
			if (ficha.equals("wP")) {
				if (filaOrigen - 1 >= 0) { // Control de límites
					casillas[filaOrigen - 1][columna].setText("wJa");
					casillas[filaOrigen - 1][columna].setHorizontalTextPosition(JButton.CENTER);
					casillas[filaOrigen - 1][columna].setVerticalTextPosition(JButton.CENTER);
					casillas[filaOrigen - 1][columna].setForeground(Colores.SIN_COLOR);
				}
			} else {
				if (filaOrigen + 1 < 8) { // Control de límites
					casillas[filaOrigen + 1][columna].setText("bJa");
					casillas[filaOrigen + 1][columna].setHorizontalTextPosition(JButton.CENTER);
					casillas[filaOrigen + 1][columna].setVerticalTextPosition(JButton.CENTER);
					casillas[filaOrigen + 1][columna].setForeground(Colores.SIN_COLOR);
				}
			}
		} else {
			// Si no se puede comer al paso, limpia los peones temporales
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (casillas[i][j].getText().equals("wJa") || casillas[i][j].getText().equals("bJa"))
						casillas[i][j].setText("");
				}
			}
		}

	}
}

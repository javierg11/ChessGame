package Piezas;

import java.awt.Color;

import javax.swing.JButton;
public abstract class Piezas {
	protected static String jugadasTotales = "";
	protected static int filaActual = 0;
	protected static int columnaActual = 0;



	public abstract String calcularMovimientos(String posicion, JButton[][] casillas, String ficha);

	
	
    protected void inicializarPosicion(String posicion) {
        filaActual = Integer.parseInt(posicion.substring(0, 1));
        columnaActual = Integer.parseInt(posicion.substring(1, 2));
        jugadasTotales = ""; 
    }
	protected static boolean mismoColor(JButton[][] casillas, int fila, int columna, String fichaColor) {
		String contenidoCasilla = casillas[fila][columna].getText();

		// Mira si las fichas son del mismo color
		boolean esMismoColor = (contenidoCasilla.contains("b") && fichaColor.contains("b"))
				|| (contenidoCasilla.contains("w") && fichaColor.contains("w"));

		if (esMismoColor) {
			return true;
		} else {
			resaltarCasilla(fila, columna, casillas);
			return false;
		}
	}

	// Ver casillas para mover
	protected static void resaltarCasilla(int fila, int columna, JButton[][] casillas) {
		if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
			jugadasTotales += fila + "" + columna + " ";
			casillas[fila][columna].setBackground(new Color(144, 238, 144));
			casillas[fila][columna].setEnabled(true);
		}
	}

	// Volver a como estaba
	public static void resetColores(JButton[][] casillas) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0) {
				    casillas[i][j].setBackground(new Color(240, 217, 181)); // Beige cálido (marfil)
				} else {
					casillas[i][j].setBackground(new Color(181, 136, 99));  // Marrón terracota
				}
			}
		}
	}
}

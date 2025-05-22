package Tablero;

import javax.swing.JButton;
import ConstantesComunes.Colores;


public class FuncionesVisualesTablero {
	static boolean verCasillas=true;
	public static void setVerCasillas(boolean verCasillas) {
		FuncionesVisualesTablero.verCasillas = verCasillas;
	}

	public static boolean isVerCasillas() {
		return verCasillas;
	}

	// Ver casillas para mover
	public static void resaltarCasilla(int fila, int columna, JButton[][] casillas) {
		if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
			casillas[fila][columna].setBackground(Colores.RESALTAR_VERDE); // Verde
			casillas[fila][columna].setEnabled(true);
		}
	}

	// Volver a como estaba
	public static void resetColores(JButton[][] casillas) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// Si la casilla NO es roja, la resetea
				if (!casillas[i][j].getBackground().equals(Colores.JAQUE_ROJO)) { // No quitar la casilla de jaque
					if ((i + j) % 2 == 0) {
						casillas[i][j].setBackground(Colores.CASILLAS_BLANCAS);
					} else {
						casillas[i][j].setBackground(Colores.CASILLAS_NEGRAS);
					}
				}
			}
		}
	}

	public static void resetFullColores(JButton[][] casillas) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// Si la casilla NO es roja, la resetea
				if ((i + j) % 2 == 0) {
					casillas[i][j].setBackground(Colores.CASILLAS_BLANCAS);
				} else {
					casillas[i][j].setBackground(Colores.CASILLAS_NEGRAS);
				}

			}
		}
	}
}

import java.awt.Color;

import javax.swing.JButton;

public class Piezas {
	static String jugadasTotales="";
	public static String casillasTorre(String posicion, JButton[][] casillas, String ficha) {
		// El substring mira los valores que hay entre el primer valor (incluido) y el
		// segundo (sin incluir) del string que le pases
		int filaActual = Integer.parseInt(posicion.substring(0, 1));
		int columnaActual = Integer.parseInt(posicion.substring(1, 2));
		jugadasTotales="";

		// Movimientos horizontales
		for (int j = columnaActual + 1; j <= 8; j++) {
			if (casillas[filaActual][j].getText().isEmpty()) {
				resaltarCasilla(filaActual, j, casillas);
			} else {
				if (mismoColor(casillas, filaActual, j, ficha))
					break;
				else {
					resaltarCasilla(filaActual, j, casillas);
					break;
				}
			}
		}
		for (int j = columnaActual - 1; j >= 0; j--) {
			if (casillas[filaActual][j].getText().isEmpty()) {
				resaltarCasilla(filaActual, j, casillas);
			} else {
				if (mismoColor(casillas, filaActual, j, ficha))
					break;
				else {
					resaltarCasilla(filaActual, j, casillas);
					break;
				}
			}

		}

		// Movimientos verticales
		for (int j = filaActual + 1; j <= 8; j++) {
			if (casillas[j][columnaActual].getText().isEmpty()) {
				resaltarCasilla(j, columnaActual, casillas);
			} else {
				if (mismoColor(casillas, j, columnaActual, ficha))
					break;
				else {
					resaltarCasilla(j, columnaActual, casillas);
					break;
				}
			}

		}
		for (int j = filaActual - 1; j >= 0; j--) {
			if (casillas[j][columnaActual].getText().isEmpty()) {
				resaltarCasilla(j, columnaActual, casillas);
			} else {
				if (mismoColor(casillas, j, columnaActual, ficha))
					break;
				else {
					resaltarCasilla(j, columnaActual, casillas);
					break;
				}
			}

		}

		return jugadasTotales+"\n";

	}

	private static boolean mismoColor(JButton[][] casillas, int fila, int columna, String ficha) {
		String contenidoCasilla = casillas[fila][columna].getText();

		// Mira si las fichas son del mismo color
		boolean esMismoColor = (contenidoCasilla.contains("b") && ficha.contains("b"))
				|| (contenidoCasilla.contains("w") && ficha.contains("w"));

		if (esMismoColor) {
			return true;
		} else {
			resaltarCasilla(fila, columna, casillas);
			return false;
		}
	}

	// Ver casillas para mover
	private static void resaltarCasilla(int fila, int columna, JButton[][] casillas) {
		if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
			jugadasTotales+=fila+""+columna+" ";
			casillas[fila][columna].setBackground(new Color(144, 238, 144));
			casillas[fila][columna].setEnabled(true);
		}
	}

	// Volver a como estaba
	public static void resetColores(JButton[][] casillas) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0) {
					casillas[i][j].setBackground(Color.WHITE);
				} else {
					casillas[i][j].setBackground(Color.BLACK);
				}
			}
		}
	}
}

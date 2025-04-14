package Piezas;

import javax.swing.JButton;

public class Torre extends Piezas {

	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha) {
		resetColores(casillas);
		inicializarPosicion(posicion);
		boolean a = true;

		// Mira las casillas horozontales de la izquierda
		for (int j = columnaActual + 1; j <= 8 && a; j++) {
			if (casillas[filaActual][j].getText().isEmpty()) {
				resaltarCasilla(filaActual, j, casillas);
			} else {
				if (!mismoColor(casillas, filaActual, j, ficha))
					resaltarCasilla(filaActual, j, casillas);
				a = false;
			}
		}
		// Mira las casillas horozontales de la derecha
		for (int j = columnaActual - 1; j >= 0; j--) {
			if (casillas[filaActual][j].getText().isEmpty()) {
				resaltarCasilla(filaActual, j, casillas);
			} else {
				if (!mismoColor(casillas, filaActual, j, ficha))
					resaltarCasilla(filaActual, j, casillas);
				break;
			}
		}

		// Movimientos verticales de abajo
		a = true;
		for (int j = filaActual + 1; j <= 8 && a; j++) {
			if (casillas[j][columnaActual].getText().isEmpty()) {
				resaltarCasilla(j, columnaActual, casillas);
			} else {
				if (!mismoColor(casillas, j, columnaActual, ficha))
					resaltarCasilla(j, columnaActual, casillas);
				a = false;
			}
		}

		for (int j = filaActual - 1; j >= 0; j--) {
			if (casillas[j][columnaActual].getText().isEmpty()) {
				resaltarCasilla(j, columnaActual, casillas);
			} else {
				if (!mismoColor(casillas, j, columnaActual, ficha))
					resaltarCasilla(j, columnaActual, casillas);
				break; // Rompe el bucle al encontrar cualquier pieza
			}
		}

		return jugadasTotales + "\n";
	}
}

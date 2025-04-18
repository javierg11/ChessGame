package Piezas;

import javax.swing.JButton;

public class Peon extends Piezas {

	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha) {
		resetColores(casillas);
		inicializarPosicion(posicion);
		

		// Mover los peones de color negros
		if (ficha.contains("b")) {
			// System.out.println("fila: "+filaActual+" Columna: "+columnaActual);
			if (filaActual == 1) {
				for (int i = filaActual + 1; i < 4; i++) {
					pintarCasilla(i, casillas, ficha);
				}
			} else {
				pintarCasilla(filaActual + 1, casillas, ficha);
			}
		}

		if (ficha.contains("w")) {
			// System.out.println("fila: "+filaActual+" Columna: "+columnaActual);
			if (filaActual == 6) {
				for (int i = filaActual - 1; i > 3; i--) {
					pintarCasilla(i, casillas, ficha);
				}
			} else {
				pintarCasilla(filaActual - 1, casillas, ficha);
			}
		}

		return jugadasTotales + "\n";
	}

	private void pintarCasilla(int fila, JButton[][] casillas, String ficha) {
		if (casillas[fila][columnaActual].getText().isEmpty())
			resaltarCasilla(fila, columnaActual, casillas);
		// Estos if son para que el peon pueda comer en diagonal cuando la pieza sea del
		// color contrario al peon
		if (!(casillas[fila][columnaActual + 1].getText().isEmpty())
				&& mismoColor(casillas, fila, columnaActual + 1, ficha))
			;
		if (!(casillas[fila][columnaActual - 1].getText().isEmpty())
				&& mismoColor(casillas, fila, columnaActual - 1, ficha))
			;
	}

}

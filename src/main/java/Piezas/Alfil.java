package Piezas;

import javax.swing.JButton;

public class Alfil extends Piezas {

	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha) {
		resetColores(casillas);
		inicializarPosicion(posicion);

		calcularMovimientosAlfil(+1, +1, casillas, ficha);  // Diagonal abajo-derecha (sudeste)
		calcularMovimientosAlfil(-1, -1, casillas, ficha);  // Diagonal arriba-izquierda (noroeste)
		calcularMovimientosAlfil(+1, -1, casillas, ficha);  // Diagonal abajo-izquierda (sudoeste)
		calcularMovimientosAlfil(-1, +1, casillas, ficha);  // Diagonal arriba-derecha (noreste)

		return jugadasTotales + "\n";
	}

	protected static void calcularMovimientosAlfil(int direccionFila, int direccionColumna, JButton[][] casillas, String ficha) {
		int fila = filaActual + direccionFila;
		int columna = columnaActual + direccionColumna;
		while (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
			if (casillas[fila][columna].getText().isEmpty()) {
				resaltarCasilla(fila, columna, casillas);
			} else {
				if (!mismoColor(casillas, fila, columna, ficha))
					;
				break;
			}
			fila += direccionFila;
			columna += direccionColumna;
		}
	}

}

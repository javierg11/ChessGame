package Piezas;

import javax.swing.JButton;

public class Torre extends Piezas {

	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha) {
		resetColores(casillas);
		inicializarPosicion(posicion);

		calcularMovimientosTorre(0, -1, casillas, ficha);  // Izquierda (oeste)
		calcularMovimientosTorre(0, +1, casillas, ficha);  // Derecha (este)
		calcularMovimientosTorre(+1, 0, casillas, ficha);  // Abajo (sur)
		calcularMovimientosTorre(-1, 0, casillas, ficha);  // Arriba (norte)


		return jugadasTotales + "\n";
	}
	
	protected static void calcularMovimientosTorre(int direccionFila, int direccionColumna, JButton[][] casillas, String ficha) {
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

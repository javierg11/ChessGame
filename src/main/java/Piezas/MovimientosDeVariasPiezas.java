package Piezas;

import javax.swing.JButton;

public class MovimientosDeVariasPiezas extends Piezas{
	protected static void calcularMovimientos(int direccionFila, int direccionColumna, JButton[][] casillas, String ficha) {
		int fila = filaActual + direccionFila;
		int columna = columnaActual + direccionColumna;
		while (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
			if (casillas[fila][columna].getText().isEmpty() || !verPeonesAlPaso(casillas,fila,columna)) {
				conseguirJugadasLogicas(fila, columna);
			} else {
				if (!mismoColor(casillas, fila, columna, ficha)) {
					conseguirJugadasLogicas(fila, columna);
				}

				break;
			}
			fila += direccionFila;
			columna += direccionColumna;
		}
	}

	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha,
			boolean usarMovimientosEspeciales) {
		// TODO Auto-generated method stub
		return null;
	}
}

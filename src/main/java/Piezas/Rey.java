package Piezas;

import javax.swing.JButton;

import Tablero.CalculosEnPartida;

public class Rey extends Piezas {

	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha, boolean verMovimientos) {
		resetColores(casillas);
		inicializarPosicion(posicion,verMovimientos);
	    movimientosRey(casillas, ficha);
		return jugadasTotales + "\n";
	}

	private void movimientosRey(JButton[][] casillas, String ficha) {
		for (int i = filaActual + 1; i > filaActual - 2; i--) {
			for (int a = columnaActual + 1; a > columnaActual - 2; a--) {
				if (i >= 0 && i < 8 && a >= 0 && a < 8) {
					if (casillas[i][a].getText().isEmpty() || !verPeonesAlPaso(casillas,filaActual,columnaActual)) {
						conseguirJugadasLogicas(i, a);
					    resaltarCasilla(i, a, casillas,ficha);
					} else {
						if (!mismoColor(casillas, i, a, ficha)) {
							conseguirJugadasLogicas(i, a);
						    resaltarCasilla(i, a, casillas,ficha);
						}

					}
				}
			}
		}
		enroqueRey.enroque(ficha, CalculosEnPartida.getJugadas(),casillas);

	}
}

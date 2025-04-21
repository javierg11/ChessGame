package Piezas;

import javax.swing.JButton;

public class Rey extends Piezas {

	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha) {
		resetColores(casillas);
		inicializarPosicion(posicion);
		movimientosRey(casillas, ficha);
		return jugadasTotales + "\n";
	}

	private void movimientosRey(JButton[][] casillas, String ficha) {
		for (int i = filaActual + 1; i > filaActual - 2; i--) {
			for (int a = columnaActual + 1; a > columnaActual - 2; a--) {
				if (i >= 0 && i < 8 && a >= 0 && a < 8) {
					System.out.println(i + " " + a);
					if (casillas[i][a].getText().isEmpty()) {
						resaltarCasilla(i, a, casillas);
					} else {
						if (!mismoColor(casillas, i, a, ficha))
							;

					}
				}
			}
		}

	}
}

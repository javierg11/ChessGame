package Piezas;

import javax.swing.JButton;

public class Dama extends Piezas{

	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha) {
		resetColores(casillas);
		inicializarPosicion(posicion);
		Alfil.calcularMovimientosAlfil(+1, +1, casillas, ficha);  // Diagonal abajo-derecha (sudeste)
		Alfil.calcularMovimientosAlfil(-1, -1, casillas, ficha);  // Diagonal arriba-izquierda (noroeste)
		Alfil.calcularMovimientosAlfil(+1, -1, casillas, ficha);  // Diagonal abajo-izquierda (sudoeste)
		Alfil.calcularMovimientosAlfil(-1, +1, casillas, ficha);  // Diagonal arriba-derecha (noreste)
		
		Torre.calcularMovimientosTorre(0, -1, casillas, ficha);  // Izquierda (oeste)
		Torre.calcularMovimientosTorre(0, +1, casillas, ficha);  // Derecha (este)
		Torre.calcularMovimientosTorre(+1, 0, casillas, ficha);  // Abajo (sur)
		Torre.calcularMovimientosTorre(-1, 0, casillas, ficha);  // Arriba (norte)

		return jugadasTotales + "\n";
	}

	
	
}

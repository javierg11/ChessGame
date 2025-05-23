package Piezas;

import javax.swing.JButton;

public class Alfil extends Piezas {

	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha, boolean movEspecial) {
		inicializarPosicion(posicion);
		calcularMovimientosAlfil(casillas,ficha);
	    
		return jugadasTotales + "\n";
	}

	protected static void calcularMovimientosAlfil(JButton[][] casillas, String ficha) {
		MovimientosDeVariasPiezas.calcularMovimientos(+1, +1, casillas, ficha);  // Diagonal abajo-derecha (sudeste)
		MovimientosDeVariasPiezas.calcularMovimientos(-1, -1, casillas, ficha);  // Diagonal arriba-izquierda (noroeste)
		MovimientosDeVariasPiezas.calcularMovimientos(+1, -1, casillas, ficha);  // Diagonal abajo-izquierda (sudoeste)
		MovimientosDeVariasPiezas.calcularMovimientos(-1, +1, casillas, ficha);  // Diagonal arriba-derecha (noreste)
	
	}
}

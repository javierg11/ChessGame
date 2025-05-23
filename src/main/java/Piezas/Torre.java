package Piezas;

import javax.swing.JButton;

public class Torre extends Piezas {

	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha, boolean movEspecial) {
		inicializarPosicion(posicion);
		calcularMovimientosTorre(casillas,ficha);

		return jugadasTotales + "\n";
	}
	public static void calcularMovimientosTorre(JButton[][] casillas, String ficha) {
		MovimientosDeVariasPiezas.calcularMovimientos(0, -1, casillas, ficha);  // Izquierda (oeste)
		MovimientosDeVariasPiezas.calcularMovimientos(0, +1, casillas, ficha);  // Derecha (este)
		MovimientosDeVariasPiezas.calcularMovimientos(+1, 0, casillas, ficha);  // Abajo (sur)
		MovimientosDeVariasPiezas.calcularMovimientos(-1, 0, casillas, ficha);  // Arriba (norte)

	}
	
	
}

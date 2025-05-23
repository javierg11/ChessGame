package Piezas;

import javax.swing.JButton;

public class Dama extends Piezas{

	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha, boolean movEspecial) {
		inicializarPosicion(posicion);
		Alfil.calcularMovimientosAlfil(casillas,ficha);		//Movimientos del Alfil
		Torre.calcularMovimientosTorre(casillas, ficha);    //Movimientos de la Torre
	    
		return jugadasTotales + "\n";
	}

	
	
}

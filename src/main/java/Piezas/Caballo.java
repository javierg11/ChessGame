package Piezas;

import javax.swing.JButton;

public class Caballo extends Piezas{

	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha,boolean verMovimientos) {
	    resetColores(casillas);
	    inicializarPosicion(posicion, verMovimientos);

	 // Movimientos posibles para el caballo como una "L"
	    	movimientosCaballo(casillas,ficha);
	    


		return jugadasTotales + "\n";
	}
	
	public void movimientosCaballo(JButton[][] casillas, String ficha) {
		int[] movimientosFila = {-2, -1, +1, +2, +2, +1, -1, -2};
	    int[] movimientosColumna = {+1, +2, +2, +1, -1, -2, -2, -1};

	    for (int i = 0; i < 8; i++) {
	        int nuevaFila = filaActual + movimientosFila[i];
	        int nuevaColumna = columnaActual + movimientosColumna[i];
	        // Verifica que esté dentro del tablero
	        if (nuevaFila >= 0 && nuevaFila < 8 && nuevaColumna >= 0 && nuevaColumna < 8) {
	            String texto = casillas[nuevaFila][nuevaColumna].getText();
	            if (texto.isEmpty() || !mismoColor(casillas, nuevaFila, nuevaColumna, ficha)) {
	            	conseguirJugadasLogicas(nuevaFila, nuevaColumna);
	        	    resaltarCasilla(nuevaFila, nuevaColumna, casillas,ficha);
	            }
	        }
	    }
	}

}

package Piezas;

import javax.swing.JButton;


public class Peon extends Piezas {

	String ficha;
	int filaDestino;
	
	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha) {
	    resetColores(casillas);
	    inicializarPosicion(posicion);

	    // Peón negro
	    if (ficha.contains("b")) {
	        // Movimiento de dos casillas desde la posición inicial
	        if (filaActual == 1 && casillas[filaActual + 1][columnaActual].getText().isEmpty() 
	            && casillas[filaActual + 2][columnaActual].getText().isEmpty()) {
	            resaltarCasilla(filaActual + 2, columnaActual, casillas);
	        }
	        // Movimiento de una casilla hacia adelante
	        if (filaActual + 1 < 8 && casillas[filaActual + 1][columnaActual].getText().isEmpty()) {
	            resaltarCasilla(filaActual + 1, columnaActual, casillas);
	        }
	        // Capturas diagonales
	        marcarCapturas(filaActual + 1, columnaActual, casillas, ficha);
	    }

	    // Peón blanco
	    if (ficha.contains("w")) {
	        // Movimiento de dos casillas desde la posición inicial
	        if (filaActual == 6 && casillas[filaActual - 1][columnaActual].getText().isEmpty() 
	            && casillas[filaActual - 2][columnaActual].getText().isEmpty()) {
	            resaltarCasilla(filaActual - 2, columnaActual, casillas);
	        }
	        // Movimiento de una casilla hacia adelante
	        if (filaActual - 1 >= 0 && casillas[filaActual - 1][columnaActual].getText().isEmpty()) {
	            resaltarCasilla(filaActual - 1, columnaActual, casillas);
	        }
	        // Capturas diagonales
	        marcarCapturas(filaActual - 1, columnaActual, casillas, ficha);
	    }

	    return jugadasTotales + "\n";
	}

	/**
	 * Marca las casillas de captura diagonal para el peón.
	 */
	static void marcarCapturas(int fila, int columna, JButton[][] casillas, String ficha) {
	    // Izquierda diagonal ademas de comprobar si se puede comer al paso
	    if (columna - 1 >= 0 && fila >= 0 && fila < 8) {
	        if ((!casillas[fila][columna - 1].getText().isEmpty() && !mismoColor(casillas, fila, columna - 1, ficha)) || casillas[fila][columna - 1].getText().contains(ficha)) {
	            resaltarCasilla(fila, columna - 1, casillas);
	        }
	    }
	    // Derecha diagonal ademas de comprobar si se puede comer al paso
	    if (columna + 1 < 8 && fila >= 0 && fila < 8) {
	        if ((!casillas[fila][columna + 1].getText().isEmpty() && !mismoColor(casillas, fila, columna + 1, ficha)) || casillas[fila][columna + 1].getText().contains(ficha)) {
	            resaltarCasilla(fila, columna + 1, casillas);
	        }
	    }
	}
	


}

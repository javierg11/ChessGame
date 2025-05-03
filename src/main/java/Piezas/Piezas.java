package Piezas;

import javax.swing.JButton;


public abstract class Piezas {
	protected static String jugadasTotales = "";
	protected static int filaActual = 0;
	protected static int columnaActual = 0;

	public abstract String calcularMovimientos(String posicion, JButton[][] casillas, String ficha, boolean usarMovimientosEspeciales);

	protected static boolean verPeonesAlPaso(JButton[][] casillas, int fila, int columna) {
		if ((casillas[fila][columna].getText().equals("wJa")) || (casillas[fila][columna].getText().equals("bJa"))) {
			return false; // Si se ve un peon temporal (para comer al paso) devuelve true
		}
		return true;

	}

	protected void inicializarPosicion(String posicion) {
		filaActual = Integer.parseInt(posicion.substring(0, 1));
		columnaActual = Integer.parseInt(posicion.substring(1, 2));
		jugadasTotales = "";
	}
	
	protected String conseguirPosicion(String posicion) {
		return posicion;
	}

	protected static boolean mismoColor(JButton[][] casillas, int fila, int columna, String fichaColor) {
		String contenidoCasilla = casillas[fila][columna].getText();

		// Mira si las fichas son del mismo color
		boolean esMismoColor = (contenidoCasilla.contains("b") && fichaColor.contains("b"))
				|| (contenidoCasilla.contains("w") && fichaColor.contains("w"));

		if (esMismoColor) {
			return true;
		} else {
			return false;
		}
	}

	

	protected static void conseguirJugadasLogicas(int fila, int columna) {
		if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
			//Guardar todas las posiciones posibles a las que puede ir una pieza
			jugadasTotales += fila + "" + columna + " ";

		}
	}
	protected static String VerconseguirJugadasLogicas() {
		return jugadasTotales;
	}
	

}

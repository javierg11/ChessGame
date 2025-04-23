package Piezas;

import java.util.HashMap;

import javax.swing.JButton;

public class enroqueRey {
	public static void enroque(String ficha, HashMap<Integer, String> jugadas, JButton[][] casillas) {

		boolean torreCortaMovidaBlancas = false;
		boolean torreLargaMovidaBlancas = false;

		boolean torreCortaMovidaNegras = false;
		boolean torreLargaMovidaNegras = false;

		boolean reyMovidoBlancas = false;
		boolean reyMovidoNegras = false;

		for (String jugada : jugadas.values()) {
			// Torres blancas
			if (jugada.matches("wT-77-\\d+"))
				torreCortaMovidaBlancas = true;
			if (jugada.matches("wT-70-\\d+"))
				torreLargaMovidaBlancas = true;

			// Torres negras
			if (jugada.matches("bT-07-\\d+"))
				torreCortaMovidaNegras = true;
			if (jugada.matches("bT-00-\\d+"))
				torreLargaMovidaNegras = true;

			// Reyes
			if (jugada.matches("wR-74-\\d+"))
				reyMovidoBlancas = true;
			if (jugada.matches("bR-04-\\d+"))
				reyMovidoNegras = true;

		}

		if (!reyMovidoBlancas && !torreCortaMovidaBlancas) {
			enroqueCorto(casillas, ficha);
		}
		if (!reyMovidoBlancas && !torreLargaMovidaBlancas) {
			enroqueLargo(casillas, ficha);
		}
		if (!reyMovidoNegras && !torreCortaMovidaNegras) {
			enroqueCorto(casillas, ficha);
		}
		if (!reyMovidoNegras && !torreLargaMovidaNegras) {
			enroqueLargo(casillas, ficha);
		}

	}

	private static void enroqueCorto(JButton[][] casillas, String ficha) {
		int fila;
		if (ficha.contains("b"))
			fila = 0;
		else
			fila = 7;
		if (casillas[fila][5].getText().isEmpty() && casillas[fila][6].getText().isEmpty()) {
			Piezas.resaltarCasilla(fila, 6, casillas,ficha);
        	Piezas.conseguirJugadasLogicas(fila, 6);

		}
	}

	private static void enroqueLargo(JButton[][] casillas, String ficha) {
		int fila;
		if (ficha.contains("b"))
			fila = 0;
		else
			fila = 7;
		if (casillas[fila][1].getText().isEmpty() && casillas[fila][2].getText().isEmpty()
				&& casillas[fila][3].getText().isEmpty()) {
			Piezas.resaltarCasilla(fila, 2, casillas,ficha);
        	Piezas.conseguirJugadasLogicas(fila, 2);

		}
	}
}

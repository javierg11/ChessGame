package Piezas;

import java.util.HashMap;

import javax.swing.JButton;

public class EnroqueRey {
	static boolean torreCortaMovidaBlancas = false;
	static boolean torreLargaMovidaBlancas = false;
	static boolean torreCortaMovidaNegras = false;
	static boolean torreLargaMovidaNegras = false;
	static boolean reyMovidoBlancas = false;
	static boolean reyMovidoNegras = false;

	public static String enroque(String ficha, HashMap<Integer, String> jugadas, JButton[][] casillas, String pos, boolean verEnroque) {
		String movimiento="";
		if (!verEnroque)
			return "";

		for (String jugada : jugadas.values()) {
		    // Torres blancas
		    if (jugada.startsWith("wT-77-")) torreCortaMovidaBlancas = true;
		    if (jugada.startsWith("wT-70-")) torreLargaMovidaBlancas = true;

		    // Torres negras
		    if (jugada.startsWith("bT-07-")) torreCortaMovidaNegras = true;
		    if (jugada.startsWith("bT-00-")) torreLargaMovidaNegras = true;

		    // Reyes
		    if (jugada.startsWith("wR")) {
		    	reyMovidoBlancas = true;
		    }
		    if (jugada.startsWith("bR")) {
		    	reyMovidoNegras = true;
		    }
		}

		// Solo para blancas
		if (ficha.startsWith("w")) {
		    if (!reyMovidoBlancas && !torreLargaMovidaBlancas) {
		        String movimietoEnroque = enroqueLargo(casillas, ficha, pos);
		        if (!movimietoEnroque.isEmpty())
		            movimiento += movimietoEnroque + " ";
		    }
		    if (!reyMovidoBlancas && !torreCortaMovidaBlancas) {
		        String movimietoEnroque = enroqueCorto(casillas, ficha, pos);
		        if (!movimietoEnroque.isEmpty())
		            movimiento += movimietoEnroque + " ";
		    }
		}
		// Solo para negras
		if (ficha.startsWith("b")) {
		    if (!reyMovidoNegras && !torreLargaMovidaNegras) {
		        String movimietoEnroque = enroqueLargo(casillas, ficha, pos);
		        if (!movimietoEnroque.isEmpty())
		            movimiento += movimietoEnroque + " ";
		    }
		    if (!reyMovidoNegras && !torreCortaMovidaNegras) {
		        String movimietoEnroque = enroqueCorto(casillas, ficha, pos);
		        if (!movimietoEnroque.isEmpty())
		            movimiento += movimietoEnroque + " ";
		    }
		}

		return movimiento;

	}

	
	private static String enroqueCorto(JButton[][] casillas, String ficha, String pos) {
	    int fila = ficha.contains("b") ? 0 : 7;
	    int colActual = Integer.parseInt(pos.substring(1, 2));

	    // 1. Verifica que las casillas intermedias estén vacías
	    if (!casillas[fila][colActual + 1].getText().isEmpty() ||
	        !casillas[fila][colActual + 2].getText().isEmpty()) {
	        return ""; // No se puede enrocar
	    }

	    // 2. Verifica que el rey no pase por jaque en ninguna de las casillas involucradas
	    for (int i = 0; i <= 2; i++) {
	        String posTest = fila + "" + (colActual + i);
	        String prueba = DetectarJaqueEnPartida.controlJugadasPorJaque(
	            posTest, casillas, ficha, fila + "" + colActual, false
	        );
	        if (prueba.isEmpty()) {
	            return "";
	        }
	    }


	    // 3. Si todo está bien, devuelve la casilla final del enroque corto
	    return fila + "" + (colActual + 2);
	}


	private static String enroqueLargo(JButton[][] casillas, String ficha, String pos) {
	    int fila = ficha.contains("b") ? 0 : 7;
	    int colActual = Integer.parseInt(pos.substring(1, 2));
	    // 1. Verifica que las casillas intermedias estén vacías (colActual - 1, -2, -3)
	    if (!casillas[fila][colActual - 1].getText().isEmpty() ||
	        !casillas[fila][colActual - 2].getText().isEmpty() ||
	        !casillas[fila][colActual - 3].getText().isEmpty()) {
	        return ""; // No se puede enrocar largo
	    }

	    // 2. Verifica que el rey no pase por jaque en ninguna de las casillas involucradas (colActual, colActual - 1, colActual - 2)
	    for (int i = 0; i <= 2; i++) {
	        String posTest = fila + "" + (colActual - i);
	        String prueba = DetectarJaqueEnPartida.controlJugadasPorJaque(
	            posTest, casillas, ficha, fila + "" + colActual, false
	        );
	        if (prueba.isEmpty()) {
	            return ""; // Si alguna casilla es insegura, no se puede enrocar largo
	        }
	    }

	    // 3. Si todo está bien, devuelve la casilla final del enroque largo
	    return fila + "" + (colActual - 2);
	}
	
}

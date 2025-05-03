package Piezas;

import java.util.HashMap;

import javax.swing.JButton;

public class enroqueRey {
	public static String enroque(String ficha, HashMap<Integer, String> jugadas, JButton[][] casillas, String pos, boolean verEnroque) {
		
		if (!verEnroque)
			return "";
		boolean torreCortaMovidaBlancas = false;
		boolean torreLargaMovidaBlancas = false;
		boolean torreCortaMovidaNegras = false;
		boolean torreLargaMovidaNegras = false;
		boolean reyMovidoBlancas = false;
		boolean reyMovidoNegras = false;

		for (String jugada : jugadas.values()) {
		    // Torres blancas
		    if (jugada.startsWith("wT-77-")) torreCortaMovidaBlancas = true;
		    else if (jugada.startsWith("wT-70-")) torreLargaMovidaBlancas = true;

		    // Torres negras
		    if (jugada.startsWith("bT-07-")) torreCortaMovidaNegras = true;
		    else if (jugada.startsWith("bT-00-")) torreLargaMovidaNegras = true;

		    // Reyes
		    if (jugada.startsWith("wR-74-")) reyMovidoBlancas = true;
		    else if (jugada.startsWith("bR-04-")) reyMovidoNegras = true;
		}

		// Ahora chequea las condiciones
		if (!reyMovidoBlancas && !torreCortaMovidaBlancas) {
		    return enroqueCorto(casillas, ficha, pos);
		}
		else if (!reyMovidoNegras && !torreCortaMovidaNegras) {
		    return enroqueCorto(casillas, ficha, pos);
		}
		if (!reyMovidoBlancas && !torreLargaMovidaBlancas) {
		    return enroqueLargo(casillas, ficha, pos);
		}
		else if (!reyMovidoNegras && !torreLargaMovidaNegras) {
		    return enroqueLargo(casillas, ficha, pos);
		}

		return "";

	}

	
	private static String enroqueCorto(JButton[][] casillas, String ficha, String pos) {
	    int fila = ficha.contains("b") ? 0 : 7;
	    int colActual = Integer.parseInt(pos.substring(1, 2));
	    // 1. Verifica que las casillas intermedias estén vacías
	    if (!casillas[fila][colActual + 1].getText().isEmpty() ||
	        !casillas[fila][colActual + 2].getText().isEmpty()) {
	    	System.out.println("hay pieza\n"+casillas[fila][colActual + 1].getText()+casillas[fila][colActual + 2].getText());
	        return ""; // No se puede enrocar
	    }

	    // 2. Verifica que el rey no pase por jaque en ninguna de las casillas involucradas
	    for (int i = 0; i <= 2; i++) {
	        String posTest = fila + "" + (colActual + i);
	        System.out.println("Chequeando jaque en: " + posTest + " para ficha: " + ficha);
	        String prueba = DetectarJaqueEnPartida.controlJugadasPorJaque(
	            posTest, casillas, ficha, fila + "" + colActual, false
	        );
	        System.out.println("Resultado: " + prueba);
	        if (prueba.isEmpty()) {
	            System.out.println("No se puede enrocar por jaque en: " + posTest);
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
	        System.out.println("hay pieza\n");
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

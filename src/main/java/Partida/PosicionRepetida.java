package Partida;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;

import Piezas.DetectarJaqueEnPartida;
import Tablero.PonerPiezasTablero;

public class PosicionRepetida {

	
	public static boolean posicionRepetidaTresVeces(HashMap<Integer, String> jugadas) {
		JButton[][] tableroActual = crearTableroInicial(); // ¡Empieza desde el inicio!
		ArrayList<String> historialPosiciones = new ArrayList<>();

		historialPosiciones.add(posicionTableroString(tableroActual));

		for (int k = 1; k <= jugadas.size(); k++) {
			String jugada = jugadas.get(k);
			if (jugada == null)
				continue;

			String[] partes = jugada.split("-");
			String ficha = partes[0];
			int filaInicial = Integer.parseInt(partes[1].substring(0, 1));
			int columnaInicial = Integer.parseInt(partes[1].substring(1, 2));
			int filaFinal = Integer.parseInt(partes[2].substring(0, 1));
			int columnaFinal = Integer.parseInt(partes[2].substring(1, 2));

			tableroActual = DetectarJaqueEnPartida.crearTableroDePrueba(tableroActual, filaFinal, columnaFinal, ficha,
					filaInicial, columnaInicial);

			historialPosiciones.add(posicionTableroString(tableroActual));
		}

		String posicionActual = historialPosiciones.get(historialPosiciones.size() - 1);
		int contador = 0;
		for (String pos : historialPosiciones) {
			if (pos.equals(posicionActual)) {
				contador++;
			}
		}
		return contador >= 3;
	}
	
	private static String posicionTableroString(JButton[][] casillas) {
		String pos = "";
		for (int h = 0; h < 8; h++) {
			for (int g = 0; g < 8; g++) {
				pos += " " + casillas[h][g].getText();
			}
		}
		return pos;
	}
	public static JButton[][] crearTableroInicial() {
		JButton[][] tablero = new JButton[8][8];
		for (int fila = 0; fila < 8; fila++) {
			for (int columna = 0; columna < 8; columna++) {
				JButton casilla = new JButton();
				PonerPiezasTablero.colocarPiezas(casilla, fila, columna);

				tablero[fila][columna] = casilla;
			}
		}
		return tablero;
	}
}

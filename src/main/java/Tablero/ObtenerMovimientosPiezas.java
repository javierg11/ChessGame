package Tablero;

import javax.swing.JButton;

import Piezas.Alfil;
import Piezas.Caballo;
import Piezas.Dama;
import Piezas.DetectarJaqueEnPartida;
import Piezas.Peon;
import Piezas.Piezas;
import Piezas.Rey;
import Piezas.Torre;

public class ObtenerMovimientosPiezas {

	public static Piezas identificarFuncionPieza(String ficha) {
		Piezas pieza = null;

		String tipo = "";
		if (ficha.length() == 0)
			return pieza;
		tipo = ficha.substring(1, 2); // "D", "T", "A", "C", "P", "R"

		switch (tipo) {
		case "D":
			pieza = new Dama();
			break;
		case "T":
			pieza = new Torre();
			break;
		case "A":
			pieza = new Alfil();
			break;
		case "C":
			pieza = new Caballo();
			break;
		case "P":
			pieza = new Peon();
			break;
		case "R":
			pieza = new Rey();
			break;
		default:
			break;
		}
		return pieza;
	}

	public static String identificarMovimientosDePieza(String ficha, String posicion, JButton[][] casillas) {
		Piezas pieza = null;
		pieza = identificarFuncionPieza(ficha);

		String movimientosPosibles = pieza.calcularMovimientos(posicion, casillas, ficha, true); // Aqui consigo todos
																									// los movimientos
																									// posibles de la
																									// pieza
		String movimientosLegales = DetectarJaqueEnPartida.controlJugadasPorJaque(movimientosPosibles, casillas, ficha,
				posicion, FuncionesVisualesTablero.verCasillas); // Con esto todos los movimientos legales se pasan a la
																	// String movimientosLegales
		return movimientosLegales;

	}
}

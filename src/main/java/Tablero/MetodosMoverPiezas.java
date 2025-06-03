package Tablero;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import ConexionPartida.Movimientos;
import Partida.CalculosEnPartida;
import Partida.ConvertirAJugadasAceptables;
import Partida.FinPartida;
import Piezas.Alfil;
import Piezas.Caballo;
import Piezas.Dama;
import Piezas.DetectarJaqueEnPartida;
import Piezas.JugadaEspecialPeon;
import Piezas.Peon;
import Piezas.Piezas;
import Piezas.Rey;
import Piezas.Torre;
import ProblemasAjedrez.ProblemaConseguido;
import guardar_CargarPartida.CargarPartida;
import guardar_CargarPartida.GuardarPartida;
import ProblemasAjedrez.CrearTableroProblemas;

public class MetodosMoverPiezas {
	public static Movimientos datosDeMovimientos;
	public static volatile boolean sensorDeTurnosDosJugadores = false;
	public static boolean tiempoIniciado = false;
	private static boolean hayPieza = false;
	private static int filaOrigen, colOrigen, filaDestino, colDestino;
	private static String fichaOriginal="";
	private static ConvertirAJugadasAceptables tarea;
	

	

	public static void moverPiezas(String origen, String destino, JButton[][] casillas, String ficha,
			String movimientos, boolean verTiempo, boolean verMovimientos, boolean esProblema) {
		hayPieza = false;

		filaOrigen = Integer.parseInt(origen.substring(0, 1));
		colOrigen = Integer.parseInt(origen.substring(1, 2));
		filaDestino = Integer.parseInt(destino.substring(0, 1));
		colDestino = Integer.parseInt(destino.substring(1, 2));

		String[] movimientosValidos = movimientos.split(" ");

		for (String movimiento : movimientosValidos) {

			if (movimiento.equals(destino)) {

				// Este metodo sirve para comprobar si un peon ha llegado a su casilla de
				// coronacion
				// Si ha llegado cornona si no, no hace nada

				moverPiezas(origen, destino, casillas, ficha, verMovimientos);

				
				Thread hilo = new Thread(tarea);
				hilo.start();
				

				// Crea un hilo para realizar la tarea de decorar las jugadas
				

				FinPartida.IdentificarFinPartida(casillas, CalculosEnPartida.getJugadas(), esProblema,
						ProblemaConseguido.problemaLogrado(destino, ficha, CrearTableroProblemas.getNumeroNivel()));

				JugadaEspecialPeon.comerAlPaso(filaOrigen, filaDestino, ficha, casillas, colDestino,
						CalculosEnPartida.getJugadas());

				DetectarJaqueEnPartida.detertarPosicionJaque(casillas);

				if (verMovimientos) {
					if (CrearTableroPartida.getLabelDeMovimientosPartida() != null) {
						SwingUtilities.invokeLater(() -> {
							ConvertirAJugadasAceptables
									.actualizarJugadasEnTablero(CrearTableroPartida.getLabelDeMovimientosPartida());
						});
					}
					if (GuardarPartida.getLabelDeMovimientosPartida() != null) {
						SwingUtilities.invokeLater(() -> {
							ConvertirAJugadasAceptables
									.actualizarJugadasEnTablero(GuardarPartida.getLabelDeMovimientosPartida());
						});
					}
				}

				if (verTiempo) {
					CrearTableroPartida.getTemporizador().aplicarIncremento();
					if (!tiempoIniciado) {
						CrearTableroPartida.getTemporizador().iniciar();
						tiempoIniciado = true;
					}
				}
				// imprimirTablero(casillas);

				datosDeMovimientos = new Movimientos(origen, destino, ficha, movimientos,casillas);
				sensorDeTurnosDosJugadores = true;
				movimientos = "";

			}
		}
		movimientos = "";
	}

	public static boolean mirarMoverEnroque(JButton[][] casillas, int filaOrigen, int colDestino, int colOrigen) {
		return (casillas[filaOrigen][colOrigen].getText().contains("R") && Math.abs(colOrigen - colDestino) == 2);
	}

	public static void moverEnroque(JButton[][] casillas, int filaOrigen, int colDestino, int colOrigen) {
		String color = casillas[filaOrigen][colOrigen].getText().substring(0, 1); // "w" o "b"
		int fila = filaOrigen; // Para blancas y negras es la misma fila de origen del rey
		// Determinar si es enroque corto o largo
		boolean enroqueCorto = (colDestino > colOrigen);

		// Coordenadas de las torres y destino según el tipo de enroque
		int torreColOrigen = enroqueCorto ? 7 : 0;
		int torreColDestino = enroqueCorto ? colDestino - 1 : colDestino + 1;
		JButton casilla = casillas[fila][torreColDestino];

		PonerPiezasTablero.crearPieza(casilla, color + "T");

		// Limpiar la casilla original de la torre
		casillas[fila][torreColOrigen].setText("");
		casillas[fila][torreColOrigen].setIcon(null);
	}

	public static void imprimirTablero(JButton[][] casillas) {
		System.out.println("Estado del tablero:");
		for (int fila = 0; fila < 8; fila++) {
			for (int columna = 0; columna < 8; columna++) {
				String texto = casillas[fila][columna].getText();
				if (texto.isEmpty()) {
					System.out.print("-- ");
				} else {
					System.out.print(texto + " ");
				}
			}
			System.out.println(); // Nueva línea por fila
		}
		System.out.println("------------------------");
	}

	public static void moverPiezas(String origen, String destino, JButton[][] casillas, String ficha, boolean jugadasBonitas) {
		fichaOriginal = ficha;

		filaOrigen = Integer.parseInt(origen.substring(0, 1));
		colOrigen = Integer.parseInt(origen.substring(1, 2));
		filaDestino = Integer.parseInt(destino.substring(0, 1));
		colDestino = Integer.parseInt(destino.substring(1, 2));
		if (!CargarPartida.isEsCargarPartida()) 
			ficha = JugadaEspecialPeon.coronarPeon(filaDestino, colDestino, ficha, casillas);

		// Esta parte solo es para ver si se puede comer al paso con el peon (porque
		// tiene que actualizar otras casillas)
		if (casillas[filaDestino][colDestino].getText().equals("bJa")
				&& casillas[filaOrigen][colOrigen].getText().equals("wP")) {
			casillas[filaDestino + 1][colDestino].setText("");
			casillas[filaDestino + 1][colDestino].setIcon(null);
		} else if (casillas[filaDestino][colDestino].getText().equals("wJa")
				&& casillas[filaOrigen][colOrigen].getText().equals("bP")) {
			casillas[filaDestino - 1][colDestino].setText("");
			casillas[filaDestino - 1][colDestino].setIcon(null);
		}
		// Aqui ya sigue con normalidad

		// Esto es para el enroque
		// Detectar si es un movimiento de rey de enroque (dos columnas de diferencia)
		if (mirarMoverEnroque(casillas, filaOrigen, colDestino, colOrigen))
			moverEnroque(casillas, filaOrigen, colDestino, colOrigen);

		JButton casilla = casillas[filaDestino][colDestino];
		casillas[filaOrigen][colOrigen].setText("");
		casillas[filaOrigen][colOrigen].setIcon(null);

		if (!casillas[filaDestino][colDestino].getText().isEmpty())
			hayPieza = true;
		PonerPiezasTablero.crearPieza(casilla, ficha);
		if (!CargarPartida.isEsCargarPartida()) {
			if (fichaOriginal!=ficha)
				CalculosEnPartida.guardarMovimientos(origen, destino+""+ficha, ficha);
			else
				CalculosEnPartida.guardarMovimientos(origen, destino, ficha);
			
			if (jugadasBonitas)
			tarea = new ConvertirAJugadasAceptables(ficha, fichaOriginal, casillas,
					destino, origen, hayPieza);
		}
		else
			CalculosEnPartida.sumarMovimientos();
	}

}

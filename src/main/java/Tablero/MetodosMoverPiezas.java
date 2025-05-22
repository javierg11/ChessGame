package Tablero;

import Partida.CalculosEnPartida;
import Partida.ConvertirAJugadasAceptables;
import Partida.FinPartida;
import Partida.TiempoPartida;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import ConexionPartida.Movimientos;
import Piezas.*;

public class MetodosMoverPiezas {
	public static Movimientos datosDeMovimientos;
	private static boolean jugando=false;
	public static volatile  boolean sensorDeTurnosDosJugadores=false;
	private static boolean tiempoIniciado=false;
	public static void setJugando(boolean jugando) {
		MetodosMoverPiezas.jugando = jugando;
	}

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
		if (!jugando) {
			
			jugando=true;
		}
		Piezas pieza = null;
		pieza = identificarFuncionPieza(ficha);
		
		String movimientosPosibles = pieza.calcularMovimientos(posicion, casillas, ficha, true); // Aqui consigo todos
																									// los movimientos
																									// posibles de la
																									// pieza
		String movimientosLegales = DetectarJaqueEnPartida.controlJugadasPorJaque(movimientosPosibles, casillas, ficha,
				posicion, FuncionesVisualesTablero.verCasillas); // Con esto todos los movimientos legales se pasan a la String movimientosLegales
		return movimientosLegales;

	}

	public static void moverPiezas(String origen, String destino, JButton[][] casillas, String ficha, String movimientos) {
        Movimientos.setCasillas(casillas);

		if(!tiempoIniciado) {
			TiempoPartida.iniciarTiempo();
			tiempoIniciado=true;
		}
		int filaOrigen = Integer.parseInt(origen.substring(0, 1));
		int colOrigen = Integer.parseInt(origen.substring(1, 2));
		int filaDestino = Integer.parseInt(destino.substring(0, 1));
		int colDestino = Integer.parseInt(destino.substring(1, 2));


		String[] movimientosValidos = movimientos.split(" ");

		for (String movimiento : movimientosValidos) {
			
			if (movimiento.equals(destino)) {
				
				// Este metodo sirve para comprobar si un peon ha llegado a su casilla de
				// coronacion
				// Si ha llegado cornona si no, no hace nada
				String fichaOriginal = ficha;

				//ficha = JugadaEspecialPeon.coronarPeon(filaDestino, colDestino, ficha, casillas);

				

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
				boolean hayPieza=false;
				if (!casillas[filaDestino][colDestino].getText().isEmpty())
					hayPieza=true;
				PonerPiezasTablero.crearPieza(casilla,ficha);

				casillas[filaOrigen][colOrigen].setText("");
				casillas[filaOrigen][colOrigen].setIcon(null);

				
				
				

				CalculosEnPartida.guardarMovimientos(origen, destino, ficha);

				// Crea un hilo para realizar la tarea de decorar las jugadas
				ConvertirAJugadasAceptables tarea = new ConvertirAJugadasAceptables(ficha, fichaOriginal, casillas, destino, origen,hayPieza);
				Thread hilo = new Thread(tarea);
				hilo.start();

				FinPartida.IdentificarFinPartida(casillas, CalculosEnPartida.getJugadas());

				JugadaEspecialPeon.comerAlPaso(filaOrigen, filaDestino, ficha, casillas, colDestino,
						CalculosEnPartida.getJugadas());

				DetectarJaqueEnPartida.detertarPosicionJaque(casillas);
				SwingUtilities.invokeLater(() -> {
				    ConvertirAJugadasAceptables.actualizarJugadasEnTablero(CrearTablero.getLabelDeMovimientosPartida());
				});
				TiempoPartida.incrementoTiempoPorJugada();
				//imprimirTablero(casillas);
				
				System.out.println("origenM: " + origen);
				System.out.println("destinoM: " + destino);
				System.out.println("fichaM: " + ficha);
				System.out.println("movimientosM: " + movimientos);
				datosDeMovimientos = new Movimientos(origen, destino, ficha, movimientos);
				Movimientos.setCasillas(casillas);
				sensorDeTurnosDosJugadores=true;
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

		
		PonerPiezasTablero.crearPieza(casilla,color + "T");
		

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


}

package Partida;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Inicio.PantallaPrincipalJuego;
import Piezas.DetectarJaqueEnPartida;
import Tablero.CrearTablero;
import Tablero.MovimientosPosibles;
import Tablero.PonerPiezasTablero;
import Tablero.ReiniciarTablero;
import Tablero.TableroAjedrez;

public class FinPartida {
	static String texto = "";

	public static void IdentificarFinPartida(JButton[][] casillas, HashMap<Integer, String> jugadas) {

		if (!MovimientosPosibles.tenerMovimientosPosibles(casillas, CalculosEnPartida.colorAMover())) {
			// Crea un Timer que espera 1000 ms (1 segundo) antes de ejecutar la acción
			if (DetectarJaqueEnPartida.mirarReyEnJaque(casillas,
					(CalculosEnPartida.colorAMover() ? "blanco" : "negro"))) {
				texto = "<html><b>¡Victoria!</b><br>El jugador "
						+ (CalculosEnPartida.colorAMover() ? "blanco" : "negro")
						+ " no tiene movimientos posibles.<br><i>Jaque mate.</i></html>";
			}

			else {
				texto = "<html><b>¡Tablas!</b><br>El jugador " + (CalculosEnPartida.colorAMover() ? "blanco" : "negro")
						+ " no tiene movimientos posibles.<br><i>Es tablas.</i></html>";
			}

		} else if (posicionRepetidaTresVeces(jugadas)) {
			texto = "<html><b>¡Tablas!</b><br>La posicion se ha repetido tres veces<br><i>Es tablas.</i></html>";
		}
		if (!texto.isEmpty()) {
			mensajeTerminarPartida(texto, casillas);
			texto = "";
		}

	}

	public static void mensajeTerminarPartida(String texto, JButton[][] casillas) {
		TiempoPartida.detenerTiempo();

		Timer timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Opciones personalizadas
				String[] opciones = { "Volver a jugar", "Menú principal", "Salir del juego" };
				int seleccion = JOptionPane.showOptionDialog(null, texto, // Mensaje
						"Fin de la partida", // Título
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, // Icono
						opciones, // Botones personalizados
						opciones[0] // Botón por defecto
				);

				// Detiene el Timer para que la acción solo se ejecute una vez
				((Timer) e.getSource()).stop();

				// Actúa según la opción elegida
				switch (seleccion) {
				case 0: // Volver a jugar
					ReiniciarTablero.reiniciar(casillas);
					break;
				case 1: // Menú principal
					ReiniciarTablero.reiniciar(casillas);
					CrearTablero.getTablero().dispose();

					System.out.println("Menu");
					SwingUtilities.invokeLater(() -> {
						PantallaPrincipalJuego pantalla = new PantallaPrincipalJuego();
						pantalla.mostrar();
					});
					break;
				case 2: // Salir del juego
					System.exit(0);
					break;
				default:
					// Si el usuario cierra la ventana, puedes decidir qué hacer aquí
					break;
				}
			}
		});
		// Inicia el Timer
		timer.start();

	}

	private static boolean posicionRepetidaTresVeces(HashMap<Integer, String> jugadas) {
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
				PonerPiezasTablero.colocarPiezas(tablero, casilla, fila, columna);

				tablero[fila][columna] = casilla;
			}
		}
		return tablero;
	}
}

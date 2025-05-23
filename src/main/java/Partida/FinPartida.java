package Partida;

import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import InterfazGrafica.PantallaPrincipalJuego;
import Piezas.DetectarJaqueEnPartida;
import Tablero.CrearTablero;
import Tablero.MovimientosPosibles;
import Tablero.ReiniciarTablero;

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

		} else if (PosicionRepetida.posicionRepetidaTresVeces(jugadas)) {
			texto = "<html><b>¡Tablas!</b><br>La posicion se ha repetido tres veces<br><i>Es tablas.</i></html>";
		}
		if (!texto.isEmpty()) {
			mensajeTerminarPartida(texto, casillas,true);
			texto = "";
		}

	}

	public static void mensajeTerminarPartida(String texto, JButton[][] casillas, boolean mostrarReiniciar) {
	    TiempoPartida.detenerTiempo();
	    Icon icono = new ImageIcon(FinPartida.class.getResource("/imagesPiezas/wP.png"));

	    // Opciones personalizadas según el contexto
	    String[] opciones;
	    if (mostrarReiniciar) {
	        opciones = new String[] { "Volver a jugar", "Menú principal", "Salir del juego" };
	    } else {
	        opciones = new String[] { "Menú principal", "Salir del juego" };
	    }

	    Timer timer = new Timer(3, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            try {
	                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	            } catch (Exception a) {
	                a.printStackTrace();
	            }

	            int seleccion = JOptionPane.showOptionDialog(
	                null, texto, "Fin de la partida",
	                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icono,
	                opciones, opciones[0]
	            );

	            ((Timer) e.getSource()).stop();

	            // Actúa según la opción elegida
	            if (mostrarReiniciar) {
	                switch (seleccion) {
	                    case 0: // Volver a jugar
	                        ReiniciarTablero.reiniciar(casillas);
	                        break;
	                    case 1: // Menú principal
	                        ReiniciarTablero.reiniciar(casillas);
	                        CrearTablero.getTablero().dispose();
	                        SwingUtilities.invokeLater(() -> {
	                            PantallaPrincipalJuego pantalla = new PantallaPrincipalJuego();
	                            pantalla.mostrar();
	                        });
	                        break;
	                    case 2: // Salir del juego
	                        System.exit(0);
	                        break;
	                    default:
	                        break;
	                }
	            } else {
	                switch (seleccion) {
	                    case 0: // Menú principal
	                        ReiniciarTablero.reiniciar(casillas);
	                        CrearTablero.getTablero().dispose();
	                        SwingUtilities.invokeLater(() -> {
	                            PantallaPrincipalJuego pantalla = new PantallaPrincipalJuego();
	                            pantalla.mostrar();
	                        });
	                        break;
	                    case 1: // Salir del juego
	                        System.exit(0);
	                        break;
	                    default:
	                        break;
	                }
	            }
	        }
	    });
	    timer.start();
	}

	

	

	
}

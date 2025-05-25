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

import InterfazGrafica.PantallaPrincipalJuego;
import Piezas.DetectarJaqueEnPartida;
import ProblemasAjedrez.CrearTableroProblemas;
import Tablero.CrearTableroPartida;
import Tablero.MovimientosPosibles;
import Tablero.TableroAjedrez;

public class FinPartida {
	static String texto = "";
	public static void IdentificarFinPartida(JButton[][] casillas, HashMap<Integer, String> jugadas, boolean esProblema, boolean problemaConseguido) {
		texto=""; //Si sogo en el juego hay que reiniciar el texto del mensaje
		if (!esProblema) {
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
		}else {
			if (problemaConseguido)
		        texto="Has consiguido el problema";
			else
		        texto="Lo siento, has fallado. ¿Quieres intentar de nuevo?";

			mensajeProblemaSiguiente(texto, casillas, esProblema, problemaConseguido);
			esProblema=false;
			return;
		}
		if (!texto.isEmpty()) {
			mensajeTerminarPartida(texto, casillas,true);
			texto = "";
		}

	}

	public static void mensajeTerminarPartida(String texto, JButton[][] casillas, boolean mostrarReiniciar) {
	    CrearTableroPartida.getTemporizador().detenerTiempo();
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

	            int seleccion = JOptionPane.showOptionDialog(
	                null, texto, "Fin de la partida",
	                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icono,
	                opciones, opciones[0]
	            );

	            ((Timer) e.getSource()).stop();

	            // Lógica de opciones sin repetir código
	            if (mostrarReiniciar) {
	                if (seleccion == 0) {
	                    volverAJugar(casillas);
	                } else if (seleccion == 1) {
	                    irAMenuPrincipalPartida();
	                } else if (seleccion == 2) {
	                    salirDelJuego();
	                }
	            } else {
	                if (seleccion == 0) {
	                    irAMenuPrincipalPartida();
	                } else if (seleccion == 1) {
	                    salirDelJuego();
	                }
	            }
	            // Si selecciona cerrar ventana o cualquier otro caso, no hace nada
	        }
	    });
	    timer.start();
	}

	public static void mensajeProblemaSiguiente(String texto, JButton[][] casillas, boolean mostrarReiniciar, boolean problemaConseguido) {
	    Icon icono = new ImageIcon(FinPartida.class.getResource("/imagesPiezas/wP.png"));

	    // Opciones personalizadas según el contexto
	    String[] opciones;
	    if (problemaConseguido) {
	        opciones = new String[] { "Seguiente Problema", "Menú principal", "Salir del juego" };
	    } else {
	        opciones = new String[] { "Repetir Problema", "Menú principal", "Salir del juego" };
	    }

	    Timer timer = new Timer(3, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            int seleccion = JOptionPane.showOptionDialog(
	                null, texto, "Problemas",
	                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icono,
	                opciones, opciones[0]
	            );

	            ((Timer) e.getSource()).stop();

	            // Acciones según la opción elegida
	            if (seleccion == 0) {
	                if (problemaConseguido) {
	                    // Siguiente problema
	                    siguienteProblema();
	                } else {
	                    // Repetir problema
	                    repetirProblema();
	                }
	            } else if (seleccion == 1) {
	                // Menú principal
	                irAMenuPrincipal();
	            } else if (seleccion == 2) {
	                // Salir del juego
	                salirDelJuego();
	            }
	            // Si selecciona cerrar ventana o cualquier otro caso, no hace nada
	        }
	    });
	    timer.start();
	}

	// Métodos auxiliares para cada acción

	private static void siguienteProblema() {
	    CrearTableroProblemas.setNumeroNivel(CrearTableroProblemas.getNumeroNivel() + 1);
	    CrearTableroProblemas.cerrarTablero();
	    TableroAjedrez.crearTipoProblemas(true, 0, 0, true, "Problemas Ajedrez");
	}

	private static void repetirProblema() {
	    CrearTableroProblemas.cerrarTablero();
	    TableroAjedrez.crearTipoProblemas(true, 0, 0, true, "Problemas Ajedrez");
	}

	private static void irAMenuPrincipal() {
	    CrearTableroProblemas.cerrarTablero();
	    SwingUtilities.invokeLater(() -> {
	        PantallaPrincipalJuego pantalla = new PantallaPrincipalJuego();
	        pantalla.mostrar();
	    });
	}

	private static void salirDelJuego() {
	    System.exit(0);
	}
	
	private static void volverAJugar(JButton[][] casillas) {
	    ReiniciarPartida.reiniciar(casillas);
	}

	private static void irAMenuPrincipalPartida() {
	    CrearTableroPartida.cerrarTablero();
	    SwingUtilities.invokeLater(() -> {
	        PantallaPrincipalJuego pantalla = new PantallaPrincipalJuego();
	        pantalla.mostrar();
	    });
	}
}
	

	


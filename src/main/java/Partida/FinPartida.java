package Partida;

import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import ConexionPartida.ServidorSala;
import ConstantesComunes.CreacionJOptionPanelDialog;
import InterfazGrafica.EmpezarAJugar;
import Piezas.DetectarJaqueEnPartida;
import ProblemasAjedrez.CrearTableroProblemas;
import Tablero.CrearTableroPartida;
import Tablero.MovimientosPosibles;
import Tablero.TableroAjedrez;
import guardar_CargarPartida.GuardarPartida;

public class FinPartida {
	static String texto = "";
	public static void IdentificarFinPartida(JButton[][] casillas, HashMap<Integer, String> jugadas, boolean esProblema, boolean problemaConseguido) {
		texto=""; //Si sogo en el juego hay que reiniciar el texto del mensaje
		boolean mostrarReiniciar=true;
		
		if (!esProblema) {
		if (!MovimientosPosibles.tenerMovimientosPosibles(casillas, CalculosEnPartida.colorAMover())) {
			// Crea un Timer que espera 1000 ms (1 segundo) antes de ejecutar la acción
			if (DetectarJaqueEnPartida.mirarReyEnJaque(casillas,
					(CalculosEnPartida.colorAMover() ? "blanco" : "negro"))) {
				texto = "<html><b>¡Victoria!</b><br>El jugador "
						+ (CalculosEnPartida.colorAMover() ? "blanco" : "negro")
						+ " no tiene movimientos posibles.<br><i>Jaque mate.</i></html>";
				if (ServidorSala.mov.isColorAJugar() == CalculosEnPartida.colorAMover()) {
					ServidorSala.jugando=false;
					mostrarReiniciar=false;
					texto = "<html><b>¡Derrorta!</b><br>El jugador "
							+ (CalculosEnPartida.colorAMover() ? "blanco" : "negro")
							+ " no tiene movimientos posibles.<br><i>Jaque mate.</i></html>";
				}else if (ServidorSala.mov.isColorAJugar() != CalculosEnPartida.colorAMover()) {
					ServidorSala.jugando=false;
					mostrarReiniciar=false;
				}
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
			mensajeTerminarPartida(texto, casillas,mostrarReiniciar);
			texto = "";
		}

	}

	public static void mensajeTerminarPartida(String texto, JButton[][] casillas, boolean mostrarReiniciar) {
		if (CrearTableroPartida.getTemporizador()!=null)
			CrearTableroPartida.getTemporizador().detenerTiempo();

	    // Opciones personalizadas según el contexto
	    String[] opciones;
	    if (mostrarReiniciar) {
	        opciones = new String[] { "Volver a jugar", "Menú", "Salir del juego","Guardar Partida" };
	    } else {
	        opciones = new String[] { "Menú", "Salir del juego","Guardar Partida" };
	    }


	            // Lógica de opciones sin repetir código
	            if (mostrarReiniciar) {
	            	CreacionJOptionPanelDialog.mensajeDeTextoConRetardo(texto, "Fin de la partida", opciones, a -> {
	        	        if (a == 0)
	        	        	 volverAJugar(casillas);
	        	        if (a == 1)
	        	        	irAMenuPrincipalPartida();
	        	        if (a == 2)
	        	        	 salirDelJuego();
	        	        if (a==3) {
	        	        	irAMenuPrincipalPartida();
	        	        	JButton botonCualquiera = casillas[0][0]; 
	        	        	JPanel panelPadre = (JPanel) botonCualquiera.getParent();
	        	        	GuardarPartida.guardarPartida(panelPadre);
	        	        }
	        	    });
	            } else {
	            	//VA mal en el servidor
	            	CreacionJOptionPanelDialog.mensajeDeTextoConRetardo(texto, "Fin de la partida", opciones, a -> {
	        	        if (a == 0)
	        	        	irAMenuPrincipalPartida();
	        	        if (a == 1)
	        	        	salirDelJuego();
	        	        if (a==2) {
	        	        	irAMenuPrincipalPartida();
	        	        	JButton botonCualquiera = casillas[0][0]; 
	        	        	JPanel panelPadre = (JPanel) botonCualquiera.getParent();
	        	        	GuardarPartida.guardarPartida(panelPadre);
	        	        }

	        	    });
	            
	}
	        }

	public static void mensajeProblemaSiguiente(String texto, JButton[][] casillas, boolean mostrarReiniciar, boolean problemaConseguido) {

	    // Opciones personalizadas según el contexto
	    String[] opciones;
	    if (problemaConseguido) {
	        opciones = new String[] { "Seguiente Problema", "Menú", "Salir del juego" };
	    } else {
	        opciones = new String[] { "Repetir Problema", "Menú", "Salir del juego" };
	    }
	    
	    CreacionJOptionPanelDialog.mensajeDeTextoConRetardo(texto, "Problemas", opciones, a -> {
	        if (a == 0) {
	        	if (problemaConseguido)
	        		siguienteProblema();
	        	else
	        		repetirProblema();
	        }
	        if (a == 1)
	        	irAMenuPrincipal();
	        if (a == 2)
	        	salirDelJuego();
	    });

	}

	// Métodos auxiliares para cada acción

	private static void siguienteProblema() {
	    CrearTableroProblemas.setNumeroNivel(CrearTableroProblemas.getNumeroNivel() + 1);
	    CrearTableroProblemas.cerrarTablero();
	    TableroAjedrez.crearTipoProblemas(true, 0, 0, "Problemas Ajedrez");
	}

	private static void repetirProblema() {
	    CrearTableroProblemas.cerrarTablero();
	    TableroAjedrez.crearTipoProblemas(true, 0, 0, "Problemas Ajedrez");
	}

	private static void irAMenuPrincipal() {
	    CrearTableroProblemas.cerrarTablero();
        EmpezarAJugar.getFrame().setVisible(true);

	}

	private static void salirDelJuego() {
	    System.exit(0);
	}
	
	private static void volverAJugar(JButton[][] casillas) {
	    ReiniciarPartida.reiniciar(casillas);
	}

	private static void irAMenuPrincipalPartida() {
	    CrearTableroPartida.cerrarTablero();
        EmpezarAJugar.getFrame().setVisible(true);

	}
}
	

	


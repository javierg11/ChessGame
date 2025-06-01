package Partida;

import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import ConexionPartida.ClienteSala;
import ConexionPartida.ServidorSala;
import ConstantesComunes.CreacionJOptionPanelDialog;
import InterfazGrafica.EmpezarAJugar;
import Piezas.DetectarJaqueEnPartida;
import ProblemasAjedrez.CrearTableroProblemas;
import Tablero.CrearTableroPartida;
import Tablero.FuncionesVisualesTablero;
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
				mostrarReiniciar=true;
				if (ServidorSala.mov.isColorAJugar() != null) {
					if (ServidorSala.mov.isColorAJugar() == CalculosEnPartida.colorAMover()) {
					mostrarReiniciar=false;
					texto = "<html><b>¡Derrorta!</b><br>El jugador "
							+ (CalculosEnPartida.colorAMover() ? "blanco" : "negro")
							+ " no tiene movimientos posibles.<br><i>Jaque mate.</i></html>";
				}else if (ServidorSala.mov.isColorAJugar() != CalculosEnPartida.colorAMover()) {
					mostrarReiniciar=false;
				}
				}
				if (GuardarPartida.isGuardandoPartida()) {
					texto = "<html><b>¡Informacion!</b><br>ELa partida ha termiando. <br>¿Qué quieres hacer?.</i></html>";
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
			mensajeTerminarPartida(texto, casillas,mostrarReiniciar, GuardarPartida.isGuardandoPartida());
			texto = "";
		}

	}

	public static void mensajeTerminarPartida(String texto, JButton[][] casillas, boolean mostrarReiniciar, boolean guardandoPartida) {
		if (CrearTableroPartida.getTemporizador()!=null)
			CrearTableroPartida.getTemporizador().detenerTiempo();

	    // Opciones personalizadas según el contexto
	    String[] opciones;
	    if (mostrarReiniciar) {
	        opciones = new String[] { "Volver a jugar", "Guardar Partida", "Salir del juego","Menú" };
	    } else if (guardandoPartida){
	    	opciones = new String[] { "Guardar Partida", "Salir del juego", "Menú", };
	    }
	    else {
	        opciones = new String[] { "Guardar Partida", "Salir del juego","Menú" };
	    }


	            // Lógica de opciones sin repetir código
	            if (mostrarReiniciar) {
	            	ClienteSala.jugando=false;
	            	ServidorSala.jugando=false;
	            	CreacionJOptionPanelDialog.mensajeDeTextoConRetardo(texto, "Fin de la partida", opciones, a -> {
	        	        if (a == 0)
	        	        	 volverAJugar(casillas);
	        	        else if (a == 1) {
	        	        	JButton botonCualquiera = casillas[0][0]; 
	        	        	JPanel panelPadre = (JPanel) botonCualquiera.getParent();
	        	        	GuardarPartida.guardarPartida(panelPadre);
	        	        	irAMenuPrincipalPartida();
	        	        }  	
	        	        else if (a == 2)
	        	        	 salirDelJuego();
	        	        else if (a==3) {
	        	        	
	        	        	irAMenuPrincipalPartida();
	        	        }
	        	    });
	            }else if(guardandoPartida) {
	            		CreacionJOptionPanelDialog.mensajeDeTextoConRetardo(texto, "Fin de la partida", opciones, a -> {
		        	        if (a == 0) {
		        	        	JButton botonCualquiera = casillas[0][0]; 
		        	        	JPanel panelPadre = (JPanel) botonCualquiera.getParent();
		        	        	GuardarPartida.guardarPartida(panelPadre);
		        	        }
		        	        else if (a == 1)
		        	        	salirDelJuego();
		        	        else if (a == 2)	 
		        	        	irAMenuPrincipalPartida();
		        	    });
	            	
	            } else {
	            	ConvertirAJugadasAceptables.getJugadasBonitas().clear();
	        		CalculosEnPartida.getJugadas().clear();
	        		CalculosEnPartida.setJugadasTotales(0);
	        		TiempoPartida.setTiempoBlancas(CrearTableroPartida.getTiempo()*60);
	        		TiempoPartida.setTiempoNegras(CrearTableroPartida.getTiempo()*60);

	        		ServidorSala.algo();
	        		FuncionesVisualesTablero.resetFullColores(casillas);
	        		if (CrearTableroPartida.arrastraPieza != null)
	        			CrearTableroPartida.arrastraPieza.reiniciarVariables();
	            	CreacionJOptionPanelDialog.mensajeDeTextoConRetardo(texto, "Fin de la partida", opciones, a -> {
	        	        if (a == 0) {
	        	        	JButton botonCualquiera = casillas[0][0]; 
	        	        	JPanel panelPadre = (JPanel) botonCualquiera.getParent();
	        	        	GuardarPartida.guardarPartida(panelPadre);
	        	        	irAMenuPrincipalPartida();
	        	        }
	        	        else if (a == 1)
	        	        	salirDelJuego();
	        	        else if (a==2) {
	        	        	irAMenuPrincipalPartida();

	        	        }

	        	    });
	            
	}
	        }

	public static void mensajeProblemaSiguiente(String texto, JButton[][] casillas, boolean mostrarReiniciar, boolean problemaConseguido) {

	    // Opciones personalizadas según el contexto
	    String[] opciones;
	    if (problemaConseguido) {
	        opciones = new String[] { "Seguiente Problema", "Salir del juego", "Menú" };
	    } else {
	        opciones = new String[] { "Repetir Problema", "Salir del juego", "Menú" };
	    }
	    
	    CreacionJOptionPanelDialog.mensajeDeTextoConRetardo(texto, "Problemas", opciones, a -> {
	        if (a == 0) {
	        	if (problemaConseguido) {
	        		siguienteProblema();
	        	}
	        	else
	        		repetirProblema();
	        }
	        if (a == 1)
	        	salirDelJuego();
	        if (a == 2)
	        	irAMenuPrincipal();

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
	

	


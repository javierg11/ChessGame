package Tablero;


import javax.swing.JButton;
import Partida.CalculosEnPartida;
import Partida.ConvertirAJugadasAceptables;
import Partida.TiempoPartida;

public class ReiniciarTablero {
	
	public static void reiniciar(JButton [][] casillas) {
		ConvertirAJugadasAceptables.getJugadasBonitas().clear();
		CalculosEnPartida.getJugadas().clear();
		CalculosEnPartida.setJugadasTotales(0);
		FuncionesVisualesTablero.resetFullColores(casillas);
		CrearTablero.limpiarTablero(casillas);
		//MetodosMoverPiezas.setJugando(false);
		TiempoPartida temporizador = new TiempoPartida(CrearTablero.getLabelTiempo(),1,casillas);
		temporizador.iniciar();
		for (int fila = 0; fila < 8; fila++) {
		    for (int columna = 0; columna < 8; columna++) {
		        JButton casilla = casillas[fila][columna];
		        casilla.setText(""); // Limpia el texto
		        casilla.setIcon(null); // Limpia el icono
		        CrearTablero.labelDeMovimientosPartida.setText(
		        	    "<html>" +
		                	    "<center><span style='font-size:20pt; font-weight:bold;'>Jugadas de la Partida</span></center><br>" +
		                	    "<table style='font-size:12pt; border-collapse:collapse;'>" +
		                	    "<tr>" +
		                	    "  <th style='padding:8px 16px; border:1px solid #888;'>Mov</th>" +
		                	    "  <th style='padding:8px 16px; border:1px solid #888;'>Blancas</th>" +
		                	    "  <th style='padding:8px 16px; border:1px solid #888;'>Negras</th>" +
		                	    "</tr>" +
		                	    "</table>" +
		                	    "</html>"
		                	);
		        PonerPiezasTablero.colocarPiezas(casillas, casilla, fila, columna); // Coloca la pieza inicial si corresponde
		    }
		}

		
	}
}

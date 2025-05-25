package Partida;

import javax.swing.JButton;

import Tablero.ArrastraPieza;
import Tablero.CrearTableroPartida;
import Tablero.FuncionesVisualesTablero;
import Tablero.MetodosMoverPiezas;
import Tablero.PonerPiezasTablero;

public class ReiniciarPartida {

	public static void reiniciar(JButton[][] casillas) {
		ConvertirAJugadasAceptables.getJugadasBonitas().clear();
		CalculosEnPartida.getJugadas().clear();
		CalculosEnPartida.setJugadasTotales(0);
//		
//		for (int i = 0; i < casillas.length; i++) {
//	        for (int j = 0; j < casillas[i].length; j++) {
//	            if (casillas[i][j] != null) {
//	                System.out.print("\"" + casillas[i][j].getText() + "\"\t");
//	            } else {
//	                System.out.print("null\t");
//	            }
//	        }
//	        System.out.println(); // Nueva fila
//	    }
		System.out.println("\n\n\n");
	    if (casillas != null) {
	        for (int i = 0; i < 8; i++) {
	            for (int j = 0; j < 8; j++) {
	                if (casillas[i][j] != null) {
	                    casillas[i][j].setText("");
	                    casillas[i][j].setIcon(null);
	                }
	            }
	        }
	    }
//	    for (int i = 0; i < casillas.length; i++) {
//	        for (int j = 0; j < casillas[i].length; j++) {
//	            if (casillas[i][j] != null) {
//	                System.out.print("\"" + casillas[i][j].getText() + "\"\t");
//	            } else {
//	                System.out.print("null\t");
//	            }
//	        }
//	        System.out.println(); // Nueva fila
//	    }

		FuncionesVisualesTablero.resetFullColores(casillas);

		// MetodosMoverPiezas.setJugando(false);
		// a.setTemporizador(new
		// TiempoPartida(a.getLabelTiempo(),a.getTiempo(),casillas,a.getIncremento()));
		MetodosMoverPiezas.tiempoIniciado = false;
		for (int fila = 0; fila < 8; fila++) {
			for (int columna = 0; columna < 8; columna++) {
				JButton casilla = casillas[fila][columna];
				casilla.setText(""); // Limpia el texto
				casilla.setIcon(null); // Limpia el icono
				CrearTableroPartida.labelDeMovimientosPartida.setText("<html>"
						+ "<center><span style='font-size:20pt; font-weight:bold;'>Jugadas de la Partida</span></center><br>"
						+ "<table style='font-size:12pt; border-collapse:collapse;'>" + "<tr>"
						+ "  <th style='padding:8px 16px; border:1px solid #888;'>Mov</th>"
						+ "  <th style='padding:8px 16px; border:1px solid #888;'>Blancas</th>"
						+ "  <th style='padding:8px 16px; border:1px solid #888;'>Negras</th>" + "</tr>" + "</table>"
						+ "</html>");
				PonerPiezasTablero.colocarPiezasNormal(casilla, fila, columna); // Coloca la pieza inicial si
																				// corresponde
			}
		}
		if (CrearTableroPartida.arrastraPieza != null)
			CrearTableroPartida.arrastraPieza.reiniciarVariables();

	}

	public void limpiarTablero(JButton[][] casillas) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (casillas[i][j] != null) {
					casillas[i][j].setText("");
					casillas[i][j].setIcon(null);
				}
			}
		}
	}
}

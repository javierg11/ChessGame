package Partida;

import javax.swing.JButton;


import Tablero.CrearTableroPartida;
import Tablero.FuncionesVisualesTablero;
import Tablero.MetodosMoverPiezas;
import Tablero.PonerPiezasTablero;

public class ReiniciarPartida {

	public static void reiniciar(JButton[][] casillas) {
		ConvertirAJugadasAceptables.getJugadasBonitas().clear();
		CalculosEnPartida.getJugadas().clear();
		CalculosEnPartida.setJugadasTotales(0);
		TiempoPartida.setTiempoBlancas(CrearTableroPartida.getTiempo()*60);
		TiempoPartida.setTiempoNegras(CrearTableroPartida.getTiempo()*60);


		FuncionesVisualesTablero.resetFullColores(casillas);
		String tiempoReloj = TiempoPartida.tiempoVisual(CrearTableroPartida.getTiempo() * 60);
		String tiempoRelojHTML = "<span style='color: #FFD700; font-size:28px; font-weight:bold;'>" + tiempoReloj
				+ " <small style='font-size:14px;'>Blancas</small></span>";

		String html = "<html><div style='text-align:center;'>" + tiempoRelojHTML + "<br>" + tiempoRelojHTML
				+ "</div></html>";
		CrearTableroPartida.labelTiempo.setText(html);
		// MetodosMoverPiezas.setJugando(false);
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

}

package Tablero;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ConstantesComunes.Colores;

public class CrearTablreoNormal {
	public void crearTablero(JButton[][] casillas, JButton casilla,JPanel panelTablero, ArrastraPieza arrastraPieza, JLabel textoFlotante,
			boolean verTiempo, boolean verMovimientos, boolean esProblema) {
		
		String nombreCoordenadas = null;
		int numeroFila = 0;
		char letraColumna = ' ';
		for (int fila = 0; fila < 9; fila++) {
			for (int columna = 0; columna < 9; columna++) {
				casilla = new JButton();
				casilla.setPreferredSize(new Dimension(60, 60)); // Tamaño de cada casilla
				casilla.setOpaque(true);
				casilla.setBorderPainted(false);
				casilla.setBorderPainted(false);
				if (fila == 8 && columna == 8)
					casilla.setEnabled(false);
				else if (fila == 8) {
					// casilla.setText(columna+"");
					casilla.setEnabled(false);
					letraColumna = (char) ('A' + columna);
					casilla.setText("" + letraColumna);
				} else if (columna == 8) {
					// casilla.setText(""+fila);
					casilla.setEnabled(false);
					numeroFila = 8 - fila;
					nombreCoordenadas = "" + numeroFila;
					casilla.setText(nombreCoordenadas);
				} else if ((fila + columna) % 2 == 0)
					casilla.setBackground(Colores.CASILLAS_BLANCAS);
				else
					casilla.setBackground(Colores.CASILLAS_NEGRAS);
				PonerPiezasTablero.colocarPiezasNormal(casilla, fila, columna);
				casillas[fila][columna] = casilla;
				arrastraPieza = new ArrastraPieza(panelTablero, casillas, textoFlotante,verTiempo,verMovimientos,esProblema);
				casilla.addMouseListener(arrastraPieza.new BotonMouseListener());
				panelTablero.add(casilla);
			}
		}

	}
	
	
	
}

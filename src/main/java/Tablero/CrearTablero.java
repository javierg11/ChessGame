package Tablero;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;


public class CrearTablero {
	public static void crearTableroBasico(JFrame tablero, JButton casillas[][],JButton casilla,int casillasFilas, int casillasColumnas,JLabel textoFlotante) {
		String nombreCoordenadas = null;
		int numeroFila = 0;
		char letraColumna = ' ';
		tablero = new JFrame("Tablero de Ajedrez");

		tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tablero.setLayout(new GridLayout(9, 9));

		casillas = new JButton[9][9];
		textoFlotante = new JLabel();
		textoFlotante.setVisible(false);
		ArrastraPieza arrastraPieza;
		for (int fila = 0; fila < casillasFilas; fila++) {
			for (int columna = 0; columna < casillasColumnas; columna++) {
				casilla = new JButton();
				casilla.setOpaque(true);
				casilla.setBorderPainted(false);
				// Poner los nombres a las coordenadas
				if (fila == 8 && columna == 8)
					casilla.setEnabled(false);
				else if (fila == 8) {
					casilla.setEnabled(false);
					letraColumna = (char) ('A' + columna);
					casilla.setText("" + letraColumna);
					} else if (columna == 8) {
					    casilla.setEnabled(false);
					    numeroFila = 8 - fila;
					    nombreCoordenadas = "" + numeroFila;
					    casilla.setText(nombreCoordenadas);
					}
				
				// Pintar las casillas
				else if ((fila + columna) % 2 == 0)
					casilla.setBackground(new Color(240, 217, 181)); // Beige cálido (marfil)
				else
					casilla.setBackground(new Color(181, 136, 99)); // Marrón terracota

				// Colocar las piezas en el tablero
				PonerPiezasTablero.colocarPiezas(casillas, casilla, fila, columna);

				// Asignar el ActionListener externo
				//casilla.addActionListener(new CasillaClickListener());
				casillas[fila][columna] = casilla;
				arrastraPieza = new ArrastraPieza(tablero, casillas, textoFlotante);
				casilla.addMouseListener(arrastraPieza.new BotonMouseListener());				

				
				tablero.add(casilla);
			}
		}
		tablero.getLayeredPane().setLayout(null); // Permitir posicionamiento absoluto
		tablero.getLayeredPane().add(textoFlotante, JLayeredPane.DRAG_LAYER);

		tablero.setSize(500, 500);
		tablero.setVisible(true);
		tablero.setLocationRelativeTo(null);
		tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tablero.setResizable(false);

	}
}

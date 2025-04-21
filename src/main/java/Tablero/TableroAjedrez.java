package Tablero;

import javax.swing.*;



import java.awt.*;

public class TableroAjedrez {
	public final static int casillasFilas = 9;
	public final static int casillasColumnas = 9;

	private static JLabel textoFlotante;

	public static JFrame tablero = null;
	public static JButton casilla = null;
	public static JButton[][] casillas = null;

	public static void main(String[] args) {

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
					letraColumna = (char) ('a' + columna);
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

//	private static class CasillaClickListener implements ActionListener {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// Si estamos en medio de un arrastre, no hacer nada al hacer clic
//			// if (arrastreEnProgreso) return;
//			arrastreEnProgreso = false;
//			for (int i = 0; i < casillasFilas; i++) {
//				for (int j = 0; j < casillasColumnas; j++) {
//					if (casillas[i][j] == e.getSource()) {
//						String posicionActual = "" + i + j;
//
//						// Si no hay una pieza seleccionada (primer clic), seleccionamos una pieza
//						if (posicionOrigen == null) {
//							// Solo seleccionamos si la casilla tiene una pieza
//							if (!casillas[i][j].getText().isEmpty()) {
//								posicionOrigen = posicionActual;
//								fichaSeleccionada = casillas[i][j].getText();
//								MetodosMoverPiezas.identificarPiezaParaMover(fichaSeleccionada, posicionOrigen,
//										casillas);
//							}
//						} else {
//							// Si ya hay una pieza seleccionada (segundo clic), intentamos mover la pieza
//							if (MetodosMoverPiezas.moverPiezas(posicionOrigen, posicionActual, casillas,
//									fichaSeleccionada)) {
//								// Si el movimiento es exitoso, restablecer todo
//								Piezas.resetColores(casillas);
//								posicionOrigen = null;
//								fichaSeleccionada = null;
//							} else {
//								// Si el movimiento no fue exitoso, restablecer colores y dejar todo como estaba
//								Piezas.resetColores(casillas);
//							}
//						}
//						return; // Salimos después de hacer clic en una casilla
//					}
//				}
//			}
//		}
//	}



}

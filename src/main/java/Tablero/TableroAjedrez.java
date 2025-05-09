package Tablero;

import javax.swing.*;



public class TableroAjedrez {
	public final static int casillasFilas = 9;
	public final static int casillasColumnas = 9;

	private static JLabel textoFlotante;

	private static JFrame tablero = null;
	public static JButton casilla = null;
	public static JButton[][] casillas = null;

	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        CrearTablero.crearTableroBasico(tablero, casillas, casilla, casillasFilas, casillasColumnas, textoFlotante);
		    }
		});

		//CrearTablero.crearTableroBasico(tablero, casillas,casilla, casillasFilas,casillasColumnas,textoFlotante);
	}
	
	public static void crearTipoTablero(boolean tipoDeJuego) {
		CrearTablero.crearTableroBasico(tablero, casillas,casilla, casillasFilas,casillasColumnas,textoFlotante);
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

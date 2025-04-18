package Tablero;

import javax.swing.*;


import Piezas.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class TableroAjedrez {
	private final static int casillasFilas = 9;
	private final static int casillasColumnas = 9;

	private static boolean arrastreEnProgreso = false;
	private static Point puntoInicialClick = null;

	public static JFrame tablero = null;
	public static JButton casilla = null;
	public static JButton[][] casillas = null;
	private static String textoArrastrado = null;
	private static JButton origen = null;
	private static JLabel textoFlotante;
	private static String posicionOrigen = null;

	private static String fichaSeleccionada = null;

	public static void main(String[] args) {

		String nombreCoordenadas = null;
		int numeroFila = 0;
		char letraColumna = ' ';
		tablero = new JFrame("Tablero de Ajedrez");

		tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tablero.setLayout(new GridLayout(9, 9));

		casillas = new JButton[9][9];
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
				casilla.addMouseListener(new BotonMouseListener());
				

				
				tablero.add(casilla);
			}
		}
		textoFlotante = new JLabel();
		textoFlotante.setVisible(false);
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

	private static class BotonMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent i) {
			origen = (JButton) i.getSource();
		    textoArrastrado = origen.getActionCommand();
		    fichaSeleccionada = origen.getText();
		    posicionOrigen = obtenerPosicion(origen);
		    //Mirar a quien le toca mover
			if (CalculosEnPartida.colorAMover() && fichaSeleccionada.contains("w"));
			else if (!(CalculosEnPartida.colorAMover()) && fichaSeleccionada.contains("b"));
			//Si se toca una pieza del color al que no le toca mover el programa no hace nada
			else return; 
			
			//Aqui hay un bug (solo me ha salido una vez) puedo mover una pieza que no deberia por color
			
			//Aqui se le añade la funcion de poder arrastrar la pieza al
		    origen.addMouseMotionListener(new BotonMouseMotionListener());
		    MetodosMoverPiezas.identificarPiezaParaMover(fichaSeleccionada, posicionOrigen, casillas);

		    puntoInicialClick = i.getPoint();
		    arrastreEnProgreso = false;

		    textoFlotante.setText(null);
		    textoFlotante.setIcon(origen.getIcon());
		    textoFlotante.setOpaque(false);
		    textoFlotante.setBorder(null);
		    textoFlotante.setSize(textoFlotante.getPreferredSize());
		    
		}

		@Override
		public void mouseReleased(MouseEvent a) {

			// Esto es para arreglar el bug visual de la pieza que cuando no salia de la
			// casilla se duplicaba en su misma casilla
			if ((puntoInicialClick == null))
				return;
			int dx = Math.abs(a.getX() - puntoInicialClick.x);
			int dy = Math.abs(a.getY() - puntoInicialClick.y);

			// Verificamos si el arrastre de una pieza está en progreso y si la pieza
			// original (origen) no es nula
			// También verificamos que el textoArrastrado no sea nulo
			if ((arrastreEnProgreso && origen != null && textoArrastrado != null && textoFlotante != null) && (dx > 5 || dy > 5)) {
				// Convertimos la posición del ratón en el marco a la posición en el contenedor
				// principal
				Point puntoEnFrame = SwingUtilities.convertPoint((Component) a.getSource(), a.getPoint(),
						tablero.getContentPane());

				// Obtenemos el componente (en este caso, el botón de destino) en la nueva
				// posición del ratón
				Component destino = tablero.getContentPane().getComponentAt(puntoEnFrame);

				// Si el destino es un botón y no es el mismo que el origen
				if (destino != origen && destino!=null) {
					// Convertimos el destino a un JButton
					JButton botonDestino = (JButton) destino;

					// Obtenemos la posición del destino
					String posicionDestino = obtenerPosicion(botonDestino);

					// Intentamos mover la pieza desde la posición de origen a la de destino
					MetodosMoverPiezas.intentarMover(posicionOrigen, posicionDestino, origen, botonDestino, casillas,
							fichaSeleccionada);
				textoFlotante.setVisible(false);
				textoFlotante.setIcon(null);
				origen = null;
				posicionOrigen = null;
				fichaSeleccionada = null;
				} else {
					fichaSeleccionada = null;
				}

				// Ocultamos el texto flotante que muestra la pieza mientras se arrastra

				// Restablecemos los colores de las casillas (en caso de haber resaltado
				// algunas)
				Piezas.resetColores(casillas);

				// Limpiamos las variables que controlan el arrastre
				textoArrastrado = null;
				origen = null;
				posicionOrigen = null;
				fichaSeleccionada = null;
			}

			// Si no hay arrastre en progreso y no se ha seleccionado una casilla con el
			// clic
			if (!arrastreEnProgreso) {
				// Limpiamos todas las variables de estado
				textoArrastrado = null;
				origen = null;
				posicionOrigen = null;
				fichaSeleccionada = null;

				// Restablecemos los colores de las casillas
				Piezas.resetColores(casillas);
			}

			// Restablecemos las variables que controlan el estado del arrastre y el clic
			arrastreEnProgreso = false;
			// puntoInicialClick = null;
		}

	}

	private static class BotonMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent c) {

			

			// Arreglo muy poco bonito
			Point puntoEnFrame = SwingUtilities.convertPoint((Component) c.getSource(), c.getPoint(),
					tablero.getContentPane());
			Component destino = tablero.getContentPane().getComponentAt(puntoEnFrame);

			if ((origen == destino || puntoInicialClick == null || origen==null)) {
				textoFlotante.setVisible(false);

				return;
			}
			else if (textoFlotante==null)
				return;

			// Hacer algo despues de preguntar
			int dx = Math.abs(c.getX() - puntoInicialClick.x);
			int dy = Math.abs(c.getY() - puntoInicialClick.y);
			if (dx > 5 || dy > 5) {
				arrastreEnProgreso = true;
				if (origen.isEnabled())
					origen.setBackground(Color.ORANGE);

				Point p = SwingUtilities.convertPoint(origen, c.getPoint(), tablero.getLayeredPane());
				int x = p.x - textoFlotante.getWidth() / 2;
				int y = p.y - textoFlotante.getHeight() / 2;
				textoFlotante.setLocation(x, y);
				textoFlotante.setVisible(true);
			}
		}

	}

	// Método auxiliar para obtener la posición de un botón en la matriz casillas
	private static String obtenerPosicion(JButton boton) {
		for (int i = 0; i < casillasFilas; i++) {
			for (int j = 0; j < casillasColumnas; j++) {
				if (casillas[i][j] == boton) {
					return "" + i + j;
				}
			}
		}
		return null;
	}

}

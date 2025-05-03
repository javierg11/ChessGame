package Tablero;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import ConstantesComunes.Colores;
import Partida.CalculosEnPartida;
import Piezas.FuncionesVisualesTablero;

public class ArrastraPieza {
	private String textoArrastrado = null;
	private JButton origen = null;
	private String posicionOrigen = null;
	public final static int casillasFilas = 9;
	public final static int casillasColumnas = 9;

	private String fichaSeleccionada = null;
	private Point puntoInicialClick = null;

	private JLabel textoFlotante;

	private JFrame tablero = null;
	private JButton[][] casillas = null;

	public ArrastraPieza(JFrame tablero, JButton[][] casillas, JLabel textoFlotante) {
		this.tablero = tablero;
		this.casillas = casillas;
		this.textoFlotante = textoFlotante;
	}

	class BotonMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent i) {
			FuncionesVisualesTablero.resetColores(casillas);

			origen=(JButton) i.getSource();
			if (origen.getText().equals(""))
				return;
			
			textoArrastrado = origen.getActionCommand();
			fichaSeleccionada = origen.getText();
			posicionOrigen = obtenerPosicion(origen);
			// Si no es el turno del color de la ficha seleccionada, no hacer nada
			if ((CalculosEnPartida.colorAMover() && !fichaSeleccionada.contains("w")) ||
			    (!CalculosEnPartida.colorAMover() && !fichaSeleccionada.contains("b"))) {
			    return;
			}



			// Aqui se le añade la funcion de poder arrastrar la pieza
			origen.addMouseMotionListener(new BotonMouseMotionListener());
			MetodosMoverPiezas.identificarMovimientosDePieza(fichaSeleccionada, posicionOrigen, casillas);

			puntoInicialClick = i.getPoint();

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
			if ((origen != null && textoArrastrado != null && textoFlotante != null)
					&& (dx > 5 || dy > 5)) {
				// Convertimos la posición del ratón en el marco a la posición en el contenedor
				// principal
				Point puntoEnFrame = SwingUtilities.convertPoint((Component) a.getSource(), a.getPoint(),
						tablero.getContentPane());

				// Obtenemos el componente (en este caso, el botón de destino) en la nueva
				// posición del ratón
				Component destino = tablero.getContentPane().getComponentAt(puntoEnFrame);

				// Si el destino es un botón y no es el mismo que el origen
				if (destino != origen && destino != null && destino instanceof JButton && destino.isEnabled()) {
					// Convertimos el destino a un JButton
					JButton botonDestino = (JButton) destino;

					// Obtenemos la posición del destino
					String posicionDestino = obtenerPosicion(botonDestino);

					// Intentamos mover la pieza desde la posición de origen a la de destino
					MetodosMoverPiezas.moverPiezas(posicionOrigen, posicionDestino, casillas,
							fichaSeleccionada);
					textoFlotante.setVisible(false);
					textoFlotante.setIcon(null);
					origen = null;
					posicionOrigen = null;
					fichaSeleccionada = null;
				} else {
					fichaSeleccionada = null;
					
				}

				// Restablecemos los colores de las casillas (en caso de haber resaltado
				// algunas)
				FuncionesVisualesTablero.resetColores(casillas);

				// Limpiamos las variables que controlan el arrastre
				textoArrastrado = null;
				origen = null;
				posicionOrigen = null;
				fichaSeleccionada = null;
				

			}
			
			// Restablecemos las variables que controlan el estado del arrastre y el clic
			puntoInicialClick = null;
			tablero.setCursor(Cursor.getDefaultCursor());
		}

	}

	private class BotonMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent c) {
			if (origen == null||origen.getText().equals(""))
				return;
			tablero.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			Point puntoEnFrame = SwingUtilities.convertPoint((Component) c.getSource(), c.getPoint(),
					tablero.getContentPane());
			Component destino = tablero.getContentPane().getComponentAt(puntoEnFrame);

			if ((origen == destino || puntoInicialClick == null || origen == null || textoFlotante == null)) {
				textoFlotante.setVisible(false);
				return;
			} 

			int dx = Math.abs(c.getX() - puntoInicialClick.x);
			int dy = Math.abs(c.getY() - puntoInicialClick.y);
			if (dx > 5 || dy > 5) {
				if (origen.isEnabled() && !origen.getBackground().equals(Colores.JAQUE_ROJO))
					origen.setBackground(Colores.ARRASTRAR_PIEZA);

				Point p = SwingUtilities.convertPoint(origen, c.getPoint(), tablero.getLayeredPane());
				int x = p.x - textoFlotante.getWidth() / 2;
				int y = p.y - textoFlotante.getHeight() / 2;
				textoFlotante.setLocation(x, y);
				textoFlotante.setVisible(true);
			}
		}

	}

	// Método auxiliar para obtener la posición de un botón en la matriz casillas
	private String obtenerPosicion(JButton boton) {
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

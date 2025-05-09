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
import javax.swing.JPanel;
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

    private JPanel panelTablero = null; // Cambiado de JFrame a JPanel
    private JButton[][] casillas = null;
    private String movimientos="";
    public ArrastraPieza(JPanel panelTablero, JButton[][] casillas, JLabel textoFlotante) {
        this.panelTablero = panelTablero;
        this.casillas = casillas;
        this.textoFlotante = textoFlotante;
    }

    class BotonMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent i) {
            FuncionesVisualesTablero.resetColores(casillas);

            origen = (JButton) i.getSource();
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
            movimientos=MetodosMoverPiezas.identificarMovimientosDePieza(fichaSeleccionada, posicionOrigen, casillas);

            puntoInicialClick = i.getPoint();

            textoFlotante.setText(null);
            textoFlotante.setIcon(origen.getIcon());
            textoFlotante.setOpaque(false);
            textoFlotante.setBorder(null);
            textoFlotante.setSize(textoFlotante.getPreferredSize());
        }

        @Override
        public void mouseReleased(MouseEvent a) {
            if ((puntoInicialClick == null))
                return;
            int dx = Math.abs(a.getX() - puntoInicialClick.x);
            int dy = Math.abs(a.getY() - puntoInicialClick.y);

            if ((origen != null && textoArrastrado != null && textoFlotante != null)
                    && (dx > 8 || dy > 8)) {
                // Convertimos la posición del ratón al panel del tablero
                Point puntoEnTablero = SwingUtilities.convertPoint((Component) a.getSource(), a.getPoint(), panelTablero);

                // Obtenemos el componente (en este caso, el botón de destino) en la nueva posición del ratón
                Component destino = panelTablero.getComponentAt(puntoEnTablero);

                if (puntoEnTablero.x < 0 || puntoEnTablero.y < 0 ||
                	    puntoEnTablero.x >= panelTablero.getWidth() ||
                	    puntoEnTablero.y >= panelTablero.getHeight()
                		|| !destino.isEnabled()
                		|| destino==origen){
                	    // Está fuera del tablero
                	    textoFlotante.setVisible(false);
                	    textoArrastrado = null;
                	    origen = null;
                	    posicionOrigen = null;
                	    fichaSeleccionada = null;
                	    puntoInicialClick = null;
                	    panelTablero.setCursor(Cursor.getDefaultCursor());
                	    return;
                	}

                // Si el destino es un botón y no es el mismo que el origen
                if (destino != origen && destino != null && destino instanceof JButton && destino.isEnabled()) {
                    JButton botonDestino = (JButton) destino;
                    String posicionDestino = obtenerPosicion(botonDestino);

                    MetodosMoverPiezas.moverPiezas(posicionOrigen, posicionDestino, casillas, fichaSeleccionada,movimientos);
                    textoFlotante.setVisible(false);
                    textoFlotante.setIcon(null);
                    origen = null;
                    posicionOrigen = null;
                    fichaSeleccionada = null;
                } else {
                    fichaSeleccionada = null;
                }

                FuncionesVisualesTablero.resetColores(casillas);

                textoArrastrado = null;
                origen = null;
                posicionOrigen = null;
                fichaSeleccionada = null;
            }

            puntoInicialClick = null;
            panelTablero.setCursor(Cursor.getDefaultCursor());
        }
    }

    private class BotonMouseMotionListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent c) {
            // Si no hay origen, el texto está vacío o no se ha registrado el punto inicial del click, salimos del método
            if (origen == null || origen.getText().equals("") || puntoInicialClick == null)
                return;

            // Cambia el cursor a una mano para indicar que se está arrastrando una pieza
            panelTablero.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Calcula la distancia movida desde el punto inicial del click
            int dx = Math.abs(c.getX() - puntoInicialClick.x);
            int dy = Math.abs(c.getY() - puntoInicialClick.y);

            // Si se ha movido más de 5 píxeles en cualquier dirección, se considera un arrastre válido
            if (dx > 5 || dy > 5) {
                // Si el origen está habilitado y no está en jaque (color rojo), cambia el color para indicar arrastre
                if (origen.isEnabled() && !origen.getBackground().equals(Colores.JAQUE_ROJO))
                    origen.setBackground(Colores.ARRASTRAR_PIEZA);

                // Obtiene el JFrame principal desde el panel del tablero
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panelTablero);

                // Convierte la posición del mouse relativa al origen a coordenadas del LayeredPane del JFrame
                Point p = SwingUtilities.convertPoint(origen, c.getPoint(), frame.getLayeredPane());

                // Centra el texto flotante respecto al cursor
                int x = p.x - textoFlotante.getWidth() / 2;
                int y = p.y - textoFlotante.getHeight() / 2;

                // Mueve y muestra el texto flotante en la nueva posición
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

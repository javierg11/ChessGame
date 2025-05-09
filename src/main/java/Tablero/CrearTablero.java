package Tablero;

import ConstantesComunes.Colores;
import Partida.TiempoPartida;

import java.awt.*;
import javax.swing.*;

/**
 * Clase que crea el tablero de ajedrez en un hilo.
 */
public class CrearTablero implements Runnable {
    private JFrame tablero;
    private JButton[][] casillas;
    private JButton casilla;
    private int casillasFilas;
    private int casillasColumnas;
    private JLabel textoFlotante;
    private static JLabel labelDeMovimientosPartida;
	private static JLabel labelTiempo;

    public static JLabel getLabelDeMovimientosPartida() {
		return labelDeMovimientosPartida;
	}
    public static JLabel getLabelTiempo() {
		return labelTiempo;
	}
    
    
	static int tiempoBlancas = 180; // en segundos
	static int tiempoNegras = 180;  // en segundos
	static boolean enPartida = true;    

	
    public CrearTablero() {
        this.tablero = null;
        this.casillas = null;
        this.casilla = null;
        this.casillasFilas = 9;
        this.casillasColumnas = 9;
        this.textoFlotante = null;
    }

    @Override
    public void run() {
        crearTableroBasico(tablero, casillas, casilla, casillasFilas, casillasColumnas, textoFlotante);
    }
    public static void crearTableroBasico(JFrame tablero, JButton casillas[][], JButton casilla, int casillasFilas, int casillasColumnas, JLabel textoFlotante) {
        String nombreCoordenadas = null;
        int numeroFila = 0;
        char letraColumna = ' ';
        tablero = new JFrame("Tablero de Ajedrez");
        tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tablero.setLayout(new BorderLayout());

        JPanel panelIzquierda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelIzquierda.setPreferredSize(new Dimension(250, 600)); // Ampliado
        JLabel labelIzquierda = new JLabel("Tiempo");
        labelIzquierda.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente Arial, negrita, tamaño 24

        labelTiempo = new JLabel("01:00-01:00");
        labelTiempo.setFont(new Font("Arial", Font.BOLD, 24)); // Igual tamaño para consistencia
        panelIzquierda.setLayout(new BoxLayout(panelIzquierda, BoxLayout.Y_AXIS));
        panelIzquierda.add(labelIzquierda);
        panelIzquierda.add(labelTiempo);

        // Panel derecho con JLabel arriba y dos botones abajo
        JPanel panelDerecha = new JPanel();
        panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));
        panelDerecha.setPreferredSize(new Dimension(250, 600)); // Ampliado
        labelDeMovimientosPartida = new JLabel(
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
        	labelDeMovimientosPartida.setAlignmentX(Component.CENTER_ALIGNMENT);
        	labelDeMovimientosPartida.setVerticalAlignment(JLabel.TOP); // Para que el texto empiece arriba
        	JScrollPane scrollPane = new JScrollPane(labelDeMovimientosPartida);
        	scrollPane.setPreferredSize(new Dimension(400, 300)); // Ajusta el tamaño a tu gusto
        	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        	scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        	panelDerecha.add(scrollPane);
        // Panel central para el tablero de ajedrez, con tamaño fijo
        JPanel panelTablero = new JPanel(new GridLayout(9, 9));
        panelTablero.setPreferredSize(new Dimension(600, 600)); // Ampliado

        casillas = new JButton[9][9];
        textoFlotante = new JLabel();
        textoFlotante.setVisible(false);
        ArrastraPieza arrastraPieza;

        for (int fila = 0; fila < casillasFilas; fila++) {
            for (int columna = 0; columna < casillasColumnas; columna++) {
                casilla = new JButton();
                casilla.setPreferredSize(new Dimension(60, 60)); // Tamaño de cada casilla
                casilla.setOpaque(true);
                casilla.setBorderPainted(false);
                casilla.setBorderPainted(false);
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
                } else if ((fila + columna) % 2 == 0)
                    casilla.setBackground(Colores.CASILLAS_BLANCAS);
                else
                    casilla.setBackground(Colores.CASILLAS_NEGRAS);

                PonerPiezasTablero.colocarPiezas(casillas, casilla, fila, columna);
                casillas[fila][columna] = casilla;
                arrastraPieza = new ArrastraPieza(panelTablero, casillas, textoFlotante);
                casilla.addMouseListener(arrastraPieza.new BotonMouseListener());
                panelTablero.add(casilla);
            }
        }
        
        //Esto es para ir actualizando el reloj 
        TiempoPartida temporizador = new TiempoPartida(CrearTablero.getLabelTiempo(), 1);
        temporizador.iniciar();

        // Añadir paneles al frame
        tablero.add(panelIzquierda, BorderLayout.WEST);
        tablero.add(panelTablero, BorderLayout.CENTER);
        tablero.add(panelDerecha, BorderLayout.EAST);

        // Añadir texto flotante al LayeredPane
        tablero.getLayeredPane().setLayout(null);
        tablero.getLayeredPane().add(textoFlotante, JLayeredPane.DRAG_LAYER);

        tablero.pack();
        tablero.setLocationRelativeTo(null);
        tablero.setResizable(false);
        tablero.setVisible(true);
        
        

    }
    
   


}

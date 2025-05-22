package Tablero;

import ConstantesComunes.Colores;

import Partida.TiempoPartida;

import java.awt.*;
import javax.swing.*;

import ConexionPartida.Movimientos;

/**
 * Clase que crea el tablero de ajedrez en un hilo.
 */

public class CrearTablero implements Runnable {
    
public CrearTablero() {
    this.casillas = new JButton[9][9];
}

    private static JFrame tablero;
    private JButton[][] casillas;
    private JButton casilla;
    private int casillasFilas;
    private int casillasColumnas;
    private JLabel textoFlotante;
    private int tiempo;
    private int incremento;
    private boolean blancas;    public static JLabel labelDeMovimientosPartida;
	private static JLabel labelTiempo;
	public static TiempoPartida temporizador=null;
	private String nombre=null;
    public static JLabel getLabelDeMovimientosPartida() {
		return labelDeMovimientosPartida;
	}
    public static JLabel getLabelTiempo() {
		return labelTiempo;
	}
    
    

	
    public CrearTablero(JFrame tablero, JButton[][] casillas, JButton casilla, int casillasFilas, int casillasColumnas,
            JLabel textoFlotante, int tiempo, int incremento, boolean blancas,String nombre) {
    	CrearTablero.tablero = tablero;
  this.casillas = casillas;
  this.casilla = casilla;
  this.casillasFilas = casillasFilas;
  this.casillasColumnas = casillasColumnas;
  this.textoFlotante = textoFlotante;
  this.tiempo = tiempo;
  this.incremento = incremento;
  this.blancas = blancas;
  this.nombre=nombre;
}



    @Override
    public void run() {
		crearTableroBasico(tablero, casillas,casilla, casillasFilas,casillasColumnas,textoFlotante, tiempo, incremento, blancas,nombre);
    }
    public static void crearTableroBasico(JFrame tablero, JButton casillas[][], JButton casilla, int casillasFilas, 
    		int casillasColumnas, JLabel textoFlotante,int tiempo, int incremento, boolean blancas,String nombre) {
        String nombreCoordenadas = null;
        int numeroFila = 0;
        char letraColumna = ' ';
        CrearTablero.tablero = new JFrame(nombre);
        CrearTablero.tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CrearTablero.tablero.setLayout(new BorderLayout());
        
        JPanel panelIzquierda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelIzquierda.setPreferredSize(new Dimension(250, 600)); // Ampliado
        JLabel labelIzquierda = new JLabel("Tiempo");
        labelIzquierda.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente Arial, negrita, tamaño 24

        if (tiempo<0)
        	tiempo=0;
        // Construye el HTML inicial
        String tiempoReloj = TiempoPartida.tiempoVisual(tiempo*60);
        String tiempoRelojHTML = "<span style='color: #FFD700; font-size:28px; font-weight:bold;'>" 
            + tiempoReloj + " <small style='font-size:14px;'>Blancas</small></span>";

        String html = "<html><div style='text-align:center;'>"
            + tiempoRelojHTML + "<br>" + tiempoRelojHTML +
            "</div></html>";

        labelTiempo = new JLabel(html, SwingConstants.CENTER);
        if (!(tiempo==0)) {
	        //Esto es para ir actualizando el reloj 
	        temporizador = new TiempoPartida(CrearTablero.getLabelTiempo(),tiempo,casillas,incremento);
        }
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
                	//casilla.setText(columna+"");
                    casilla.setEnabled(false);
                    letraColumna = (char) ('A' + columna);
                    casilla.setText("" + letraColumna);
                } else if (columna == 8) {
                	//casilla.setText(""+fila);
                    casilla.setEnabled(false);
                    numeroFila = 8 - fila;
                    nombreCoordenadas = "" + numeroFila;
                    casilla.setText(nombreCoordenadas);
                } else if ((fila + columna) % 2 == 0)
                    casilla.setBackground(Colores.CASILLAS_BLANCAS);
                else
                    casilla.setBackground(Colores.CASILLAS_NEGRAS);

                PonerPiezasTablero.colocarPiezas(casilla, fila, columna);
                casillas[fila][columna] = casilla;
                arrastraPieza = new ArrastraPieza(panelTablero, casillas, textoFlotante);
                casilla.addMouseListener(arrastraPieza.new BotonMouseListener());
                panelTablero.add(casilla);
            }
        }
        Movimientos.setCasillas(casillas);

        // Añadir paneles al frame
        CrearTablero.tablero.add(panelIzquierda, BorderLayout.WEST);
        CrearTablero.tablero.add(panelTablero, BorderLayout.CENTER);
        CrearTablero.tablero.add(panelDerecha, BorderLayout.EAST);

        // Añadir texto flotante al LayeredPane
        CrearTablero.tablero.getLayeredPane().setLayout(null);
        CrearTablero.tablero.getLayeredPane().add(textoFlotante, JLayeredPane.DRAG_LAYER);

        CrearTablero.tablero.pack();
        CrearTablero.tablero.setLocationRelativeTo(null);
        CrearTablero.tablero.setResizable(false);
        CrearTablero.tablero.setVisible(true);
        
        

    }
    
    public static void limpiarTablero(JButton[][] casillas) {
	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            if (casillas[i][j] != null) {
	                casillas[i][j].setText("");
	                casillas[i][j].setIcon(null);
	            }
	        }
	    }
	}
	public void setCasillas(JButton[][] casillas) {
		this.casillas = casillas;
	}

	public  JButton[][] getCasillas() {
		return casillas;
	}
	
	public static JFrame getTablero() {
		return tablero;
	}
	
}

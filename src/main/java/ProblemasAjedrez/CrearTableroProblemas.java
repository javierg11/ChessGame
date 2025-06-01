package ProblemasAjedrez;

import ConstantesComunes.Colores;
import ConstantesComunes.JFrames;
import Partida.CalculosEnPartida;
import Partida.FinPartida;
import Tablero.ArrastraPieza;
import Tablero.PonerPiezasTablero;

import java.awt.*;
import javax.swing.*;
import java.util.List;


/**
 * Clase que crea el tablero de ajedrez en un hilo.
 */

public class CrearTableroProblemas implements Runnable {

	public CrearTableroProblemas() {
		this.casillas = new JButton[9][9];
	}

	static private JFrame tablero;
	private JButton[][] casillas;
	private JButton casilla;
	

	private int casillasFilas;
	private int casillasColumnas;
	private JLabel textoFlotante;
	private static int numeroNivel=1;
	private JPanel panelLateral;
	private JLabel labelNivel;
	private static boolean problema;
	

	public static JLabel labelDeMovimientosPartida;
	public static ArrastraPieza arrastraPieza;
	private JPanel panelTablero;
	public CrearTableroProblemas(JFrame tablero, JButton[][] casillas, JButton casilla, int casillasFilas, int casillasColumnas,
			JLabel textoFlotante) {
		CrearTableroProblemas.tablero = tablero;
		this.casillas = casillas;
		this.casilla = casilla;
		this.casillasFilas = casillasFilas;
		this.casillasColumnas = casillasColumnas;
		this.textoFlotante = textoFlotante;
	}

		@Override
	    public void run() {
			problema=true;
			CalculosEnPartida.guardarMovimientos("23","23","wR");
			CalculosEnPartida.guardarMovimientos("23","23","bR");

	        crearTableroBasico();
	        
	        problema=false;
	    }
		private void crearPanelLateral(int nivelProblema) {
		    panelLateral = new JPanel();
		    panelLateral.setPreferredSize(new Dimension(150, 600));
		    panelLateral.setBackground(new Color(235, 235, 235));

		    labelNivel = new JLabel("Nivel " + nivelProblema);
		    labelNivel.setFont(new Font("Arial", Font.BOLD, 40)); // Más grande
		    labelNivel.setHorizontalAlignment(SwingConstants.CENTER);
		    labelNivel.setVerticalAlignment(SwingConstants.CENTER);

		    // Usa GridBagLayout para centrar completamente el texto
		    panelLateral.setLayout(new GridBagLayout());
		    GridBagConstraints gbc = new GridBagConstraints();
		    gbc.gridx = 0;
		    gbc.gridy = 0;
		    gbc.weightx = 1.0;
		    gbc.weighty = 1.0;
		    gbc.anchor = GridBagConstraints.CENTER;
		    gbc.fill = GridBagConstraints.BOTH;

		    panelLateral.add(labelNivel, gbc);
		}


	    public void crearTableroBasico() {
	    	 

	    	 if (CalculosEnPartida.getJugadasTotales()%2!=0) {
	    		 CalculosEnPartida.sumarMovimientos();
	    	 }
	        
	    	tablero = new JFrame();
	    	tablero=new JFrame();
	        JFrames.crearJFrameBasicos(tablero,"Problemas",600,600);
	    	tablero.setLayout(new BorderLayout());


	        int nivelActual = getNumeroNivel(); 
	        crearPanelLateral(nivelActual);     

	        jpanelTablero();


	        // Añadir paneles al frame
	        tablero.add(panelLateral, BorderLayout.WEST); 
	        tablero.add(panelTablero, BorderLayout.CENTER);
	        
	        tablero.getLayeredPane().setLayout(null);
	        tablero.getLayeredPane().add(textoFlotante, JLayeredPane.DRAG_LAYER);

	        tablero.pack();
			tablero.setLocationRelativeTo(null);

	    }
	    


	

	public void crearTablero(List<PosicionPiezas> problemas) {
	    String nombreCoordenadas = null;
	    int numeroFila = 0;
	    char letraColumna = ' ';

	    for (int fila = 0; fila < casillasFilas; fila++) {
	        for (int columna = 0; columna < casillasColumnas; columna++) {
	            casilla = new JButton();
	            casilla.setPreferredSize(new Dimension(60, 60));
	            casilla.setOpaque(true);
	            casilla.setBorderPainted(false);

	            if (fila == 8 && columna == 8) {
	                casilla.setEnabled(false);
	            } else if (fila == 8) {
	                casilla.setEnabled(false);
	                letraColumna = (char) ('A' + columna);
	                casilla.setText("" + letraColumna);
	            } else if (columna == 8) {
	                casilla.setEnabled(false);
	                numeroFila = 8 - fila;
	                nombreCoordenadas = "" + numeroFila;
	                casilla.setText(nombreCoordenadas);
	            } else {
	                // Solo para casillas del tablero real (0-7, 0-7)
	                if ((fila + columna) % 2 == 0)
	                    casilla.setBackground(Colores.CASILLAS_BLANCAS);
	                else
	                    casilla.setBackground(Colores.CASILLAS_NEGRAS);

	                String codCasilla = "" + fila + columna;
	                for (PosicionPiezas p : problemas) {
	                    if (p.getCasilla().equals(codCasilla)) {
	                        PonerPiezasTablero.crearPieza(casilla, p.getPieza());
	                    }
	                }
	            }

	            casillas[fila][columna] = casilla;
	            arrastraPieza = new ArrastraPieza(panelTablero, casillas, textoFlotante, false, false,problema);
	            casilla.addMouseListener(arrastraPieza.new BotonMouseListener());
	            panelTablero.add(casilla);
	        }
	    }
	}

	
	public void jpanelTablero() {
	    panelTablero = new JPanel(new GridLayout(9, 9));
	    panelTablero.setPreferredSize(new Dimension(600, 600)); // Ampliado

	    casillas = new JButton[9][9];
	    textoFlotante = new JLabel();
	    textoFlotante.setVisible(false);

	    LeerJSON lector = new LeerJSON();
	    List<NivelProblema> niveles = lector.leerNiveles("/problemas/problemas.json");

	    int nivelActual = getNumeroNivel(); // El nivel que quieres cargar

	    List<PosicionPiezas> piezasDelNivel = null;
	    for (NivelProblema nivel : niveles) {
	        if (nivel.getNivel() == nivelActual) {
	            piezasDelNivel = nivel.getPiezas();
	            break;
	        }
	    }

	    if (piezasDelNivel != null) {
 	        crearTablero(piezasDelNivel);
	    } else {
	    	FinPartida.mensajeProblemaSiguiente("No hay más problemas disponibles",casillas,false,false);
	        System.out.println("No se encontró el nivel " + nivelActual);
	    }
	}




	public static int getNumeroNivel() {
		return numeroNivel;
	}

	public static void setNumeroNivel(int numeroNivel) {
		CrearTableroProblemas.numeroNivel = numeroNivel;
	}
	public JFrame getTablero() {
		return tablero;
	}
	public static void cerrarTablero() {
		tablero.dispose();
	}
}

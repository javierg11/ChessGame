package Tablero;

import ConstantesComunes.Colores;


import Partida.TiempoPartida;

import java.awt.*;
import javax.swing.*;


/**
 * Clase que crea el tablero de ajedrez en un hilo.
 */

public class CrearTableroPartida implements Runnable {

	public CrearTableroPartida() {
		this.casillas = new JButton[9][9];
	}

	public static JFrame tablero;
	private JButton[][] casillas;
	private JButton casilla;
	private JLabel textoFlotante;
	public static void setTiempo(double tiempo) {
		CrearTableroPartida.tiempo = tiempo;
	}

	public static double tiempo;
	private int incremento;

	public static JLabel labelDeMovimientosPartida;
	private JLabel labelTiempo;
	private static TiempoPartida temporizador = null;
	private String nombre = null;
	public static ArrastraPieza arrastraPieza;
	private JPanel panelTiempo ,panelDerecha,panelTablero;
	public CrearTableroPartida(JFrame tablero, JButton[][] casillas, JButton casilla,
			JLabel textoFlotante, int tiempo, int incremento, String nombre) {
		CrearTableroPartida.tablero = tablero;
		this.casillas = casillas;
		this.casilla = casilla;
		this.textoFlotante = textoFlotante;
		CrearTableroPartida.tiempo = tiempo;
		this.incremento = incremento;
		this.nombre = nombre;
	}

		@Override
	    public void run() {

	        crearTableroBasico();

	    }

	    public void crearTableroBasico() {
	        
	        tablero = new JFrame(nombre);
	        tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        tablero.setLayout(new BorderLayout());


	        
	        jpanelTablero();
	        jpanelMovimientos();
	        jpanelTiempo();


	        // Añadir paneles al frame
	        tablero.add(panelTiempo, BorderLayout.WEST);
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
	    
	

	public void setCasillas(JButton[][] casillas) {
		this.casillas = casillas;
	}

	public JButton[][] getCasillas() {
		return casillas;
	}

	public JFrame getTablero() {
		return tablero;
	}

	public static JLabel getLabelDeMovimientosPartida() {
		return labelDeMovimientosPartida;
	}

	

	
	
	
	public void jpanelTablero() {
		// Panel central para el tablero de ajedrez, con tamaño fijo
		
		panelTablero = new JPanel(new GridLayout(9, 9));
		panelTablero.setPreferredSize(new Dimension(600, 600)); // Ampliado

		casillas = new JButton[9][9];
		textoFlotante = new JLabel();
		textoFlotante.setVisible(false);

		CrearTablreoNormal crearTableroNormal = new CrearTablreoNormal();
		crearTableroNormal.crearTablero(casillas,casilla,panelTablero,arrastraPieza,textoFlotante,true,true,false);
	}

	private void jpanelMovimientos() {
		// Panel derecho con JLabel arriba y dos botones abajo
		panelDerecha = new JPanel();
		panelDerecha.setLayout(new BoxLayout(panelDerecha, BoxLayout.Y_AXIS));
		panelDerecha.setPreferredSize(new Dimension(250, 600)); // Ampliado
		labelDeMovimientosPartida = new JLabel("<html>"
				+ "<center><span style='font-size:20pt; font-weight:bold;'>Jugadas de la Partida</span></center><br>"
				+ "<table style='font-size:12pt; border-collapse:collapse;'>" + "<tr>"
				+ "  <th style='padding:8px 16px; border:1px solid #888;'>Mov</th>"
				+ "  <th style='padding:8px 16px; border:1px solid #888;'>Blancas</th>"
				+ "  <th style='padding:8px 16px; border:1px solid #888;'>Negras</th>" + "</tr>" + "</table>"
				+ "</html>");
		labelDeMovimientosPartida.setAlignmentX(Component.CENTER_ALIGNMENT);
		labelDeMovimientosPartida.setVerticalAlignment(JLabel.TOP); // Para que el texto empiece arriba
		JScrollPane scrollPane = new JScrollPane(labelDeMovimientosPartida);
		scrollPane.setPreferredSize(new Dimension(400, 300)); // Ajusta el tamaño a tu gusto
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelDerecha.add(scrollPane);

	}

	public void jpanelTiempo() {
		
		panelTiempo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelTiempo.setPreferredSize(new Dimension(250, 600)); // Ampliado
		
		JLabel labelIzquierda = new JLabel("Tiempo");
		labelIzquierda.setFont(new Font("Arial", Font.BOLD, 24)); // Fuente Arial, negrita, tamaño 24
		if (tiempo < 0)
			tiempo = 0;
		// Construye el HTML inicial
		String tiempoReloj = TiempoPartida.tiempoVisual(tiempo * 60);
		String tiempoRelojHTML = "<span style='color: #FFD700; font-size:28px; font-weight:bold;'>" + tiempoReloj
				+ " <small style='font-size:14px;'>Blancas</small></span>";

		String html = "<html><div style='text-align:center;'>" + tiempoRelojHTML + "<br>" + tiempoRelojHTML
				+ "</div></html>";

		labelTiempo = new JLabel(html, SwingConstants.CENTER);
		if (!(tiempo == 0)) {
			// Esto es para ir actualizando el reloj
			setTemporizador(new TiempoPartida(labelTiempo, tiempo, casillas, incremento));
		}
		panelTiempo.setLayout(new BoxLayout(panelTiempo, BoxLayout.Y_AXIS));
		panelTiempo.add(labelIzquierda);
		panelTiempo.add(labelTiempo);
		System.out.println(tiempo);


	}

	public void setTemporizador(TiempoPartida temporizador) {
		CrearTableroPartida.temporizador = temporizador;
		CrearTableroPartida.temporizador.tiempoBlancas=tiempo*60;
		CrearTableroPartida.temporizador.tiempoNegras=tiempo*60;
	}

	public static TiempoPartida getTemporizador() {
		// TODO Auto-generated method stub
		return temporizador;
	}
	
	public static void cerrarTablero() {
		tablero.dispose();
	}
}

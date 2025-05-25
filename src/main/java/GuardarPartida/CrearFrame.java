package GuardarPartida;

import java.awt.*;
import javax.swing.*;

import ConstantesComunes.Botones;
import ConstantesComunes.Colores;
import Tablero.ArrastraPieza;
import Tablero.CrearTableroPartida;
import Tablero.CrearTablreoNormal;

public class CrearFrame implements Runnable{

	private static JFrame tablero;
	private JButton[][] casillas;
	private JButton casilla;
	private JLabel textoFlotante;

	public static void setTiempo(double tiempo) {
		CrearTableroPartida.tiempo = tiempo;
	}

	public static JLabel labelDeMovimientosPartida;
	private String nombre = null;
	public static ArrastraPieza arrastraPieza;
	private JPanel panelTablero;

	public CrearFrame(JFrame tablero, JButton[][] casillas, JButton casilla,
			JLabel textoFlotante) {
		CrearTableroPartida.tablero = tablero;
		this.casillas = casillas;
		this.casilla = casilla;
		this.textoFlotante = textoFlotante;
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

	    // Crea y añade los paneles separados
	    JPanel panelIzquierda = crearPanelGuardar();
	    JPanel panelDerecha = crearPanelMovimientos();

	    tablero.add(panelTablero, BorderLayout.CENTER);
	    tablero.add(panelIzquierda, BorderLayout.WEST);
	    tablero.add(panelDerecha, BorderLayout.EAST);

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
		crearTableroNormal.crearTablero(casillas,casilla,panelTablero,arrastraPieza,textoFlotante,false,true,false);
	}

	private JPanel crearPanelMovimientos() {
	    JPanel panelMovimientos = new JPanel();
	    panelMovimientos.setLayout(new BoxLayout(panelMovimientos, BoxLayout.Y_AXIS));
	    panelMovimientos.setPreferredSize(new Dimension(250, 600));

	    labelDeMovimientosPartida = new JLabel("<html>"
	            + "<center><span style='font-size:20pt; font-weight:bold;'>Jugadas de la Partida</span></center><br>"
	            + "<table style='font-size:12pt; border-collapse:collapse;'>" + "<tr>"
	            + "  <th style='padding:8px 16px; border:1px solid #888;'>Mov</th>"
	            + "  <th style='padding:8px 16px; border:1px solid #888;'>Blancas</th>"
	            + "  <th style='padding:8px 16px; border:1px solid #888;'>Negras</th>" + "</tr>" + "</table>"
	            + "</html>");
	    labelDeMovimientosPartida.setAlignmentX(Component.CENTER_ALIGNMENT);
	    labelDeMovimientosPartida.setVerticalAlignment(JLabel.TOP);

	    JScrollPane scrollPane = new JScrollPane(labelDeMovimientosPartida);
	    scrollPane.setPreferredSize(new Dimension(400, 300));
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	    panelMovimientos.add(scrollPane);
	    panelMovimientos.add(Box.createVerticalGlue());

	    return panelMovimientos;
	}


	public static void cerrarTablero() {
		tablero.dispose();
	}
	
	private JPanel crearPanelGuardar() {
	    JPanel panelGuardar = new JPanel();
	    panelGuardar.setLayout(new BoxLayout(panelGuardar, BoxLayout.Y_AXIS));
	    panelGuardar.setPreferredSize(new Dimension(200, 600));

	    JLabel tituloGuardar = new JLabel("Guardar Partida");
	    tituloGuardar.setFont(new Font("Arial", Font.BOLD, 18));
	    tituloGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);

	    // Botón Guardar
	    JButton botonGuardar = Botones.crearBotonBasico("Guardar Partida", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
	    Botones.addHoverEffect(botonGuardar, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
	    botonGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);
	    botonGuardar.addActionListener(e -> {
	        // Lógica para guardar la partida
	        JOptionPane.showMessageDialog(panelGuardar, "¡Partida guardada!");
	    });

	    // Botón Retroceder
	    JButton botonRetroceder = Botones.crearBotonBasico("Retroceder", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
	    Botones.addHoverEffect(botonRetroceder, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
	    botonRetroceder.setAlignmentX(Component.CENTER_ALIGNMENT);
	    botonRetroceder.addActionListener(e -> {
	        // Lógica para retroceder
	        JOptionPane.showMessageDialog(panelGuardar, "¡Retroceder pulsado!");
	    });

	    // Botón Salir (abajo del todo)
	    JButton botonSalir = Botones.crearBotonBasico("Volver al inicio", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
	    Botones.addHoverEffect(botonSalir, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
	    botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
	    botonSalir.addActionListener(e -> {
	        // Lógica para salir
	        System.exit(0);
	    });

	    // Construcción del panel
	    panelGuardar.add(Box.createVerticalStrut(20));
	    panelGuardar.add(tituloGuardar);
	    panelGuardar.add(Box.createVerticalStrut(20));
	    panelGuardar.add(botonGuardar);
	    panelGuardar.add(Box.createVerticalStrut(10));
	    panelGuardar.add(botonRetroceder);
	    panelGuardar.add(Box.createVerticalGlue()); // Empuja el botón salir abajo
	    panelGuardar.add(botonSalir);
	    panelGuardar.add(Box.createVerticalStrut(10)); // Espacio inferior

	    return panelGuardar;
	}


}

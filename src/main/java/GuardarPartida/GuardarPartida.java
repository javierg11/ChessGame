package GuardarPartida;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.*;
import java.util.List;

import ConstantesComunes.Botones;
import ConstantesComunes.Colores;
import InterfazGrafica.EmpezarAJugar;
import Partida.CalculosEnPartida;
import Partida.PosicionRepetida;
import Tablero.ArrastraPieza;
import Tablero.CrearTableroPartida;
import Tablero.CrearTablreoNormal;
import Tablero.MetodosMoverPiezas;

public class GuardarPartida implements Runnable{

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

	public GuardarPartida(JFrame tablero, JButton[][] casillas, JButton casilla,
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
	        String nombre = JOptionPane.showInputDialog(panelGuardar, "Introduce el nombre de la partida:");
	        if (nombre != null && !nombre.trim().isEmpty()) {
	            HashMap<Integer, String> jugadas = CalculosEnPartida.getJugadas(); 
	            Guardar_Cargar_Partida_Funciones.guardarPartida(nombre, jugadas);
	            JOptionPane.showMessageDialog(panelGuardar, "¡Partida guardada!");
	        }
	    });


	    // Botón Retroceder
	    JButton botonRetroceder = Botones.crearBotonBasico("Retroceder", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
	    Botones.addHoverEffect(botonRetroceder, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
	    botonRetroceder.setAlignmentX(Component.CENTER_ALIGNMENT);
	    botonRetroceder.addActionListener(e -> {
	        // Lógica para retroceder
	        deshacerUltimoMovimiento(CalculosEnPartida.getJugadas(),casillas);
	    });

	    // Botón Salir (abajo del todo)
	    JButton botonSalir = Botones.crearBotonBasico("Volver al inicio", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
	    Botones.addHoverEffect(botonSalir, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
	    botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
	    botonSalir.addActionListener(e -> {
	        // Lógica para salir
            EmpezarAJugar.getFrame().setVisible(true);
            tablero.dispose();
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
	
	public static void deshacerUltimoMovimiento(HashMap<Integer, String> jugadas, JButton[][] casillas) {
	    if (jugadas.isEmpty()) return;
	    CalculosEnPartida.setJugadasTotales(CalculosEnPartida.getJugadasTotales()-1);
	    // 1. Elimina el último movimiento
	    int ultimaClave = Collections.max(jugadas.keySet());
	    jugadas.remove(ultimaClave);

	    // 2. Reinicia el tablero
	    JButton[][] tableroInicial = PosicionRepetida.crearTableroInicial();
	    // Copia el estado del tablero inicial al tablero actual
	    for (int fila = 0; fila < 8; fila++) {
	        for (int columna = 0; columna < 8; columna++) {
	            casillas[fila][columna].setText(tableroInicial[fila][columna].getText());
	            casillas[fila][columna].setIcon(tableroInicial[fila][columna].getIcon());
	        }
	    }

	    // 3. Reaplica todos los movimientos restantes en orden
	    List<Integer> clavesOrdenadas = new ArrayList<>(jugadas.keySet());
	    Collections.sort(clavesOrdenadas);
	    for (int clave : clavesOrdenadas) {
	        String movimiento = jugadas.get(clave);
	        String[] partes = movimiento.split("-");
	        if (partes.length != 3) {
	            System.err.println("Formato de movimiento incorrecto: " + movimiento);
	            continue;
	        }
	        String ficha = partes[0];
	        String origen = partes[1];
	        String destino = partes[2];
	        MetodosMoverPiezas.moverPiezas(origen, destino, casillas, ficha);
	    }

	}




}

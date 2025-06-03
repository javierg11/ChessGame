package Tablero;

import Partida.CalculosEnPartida;
import Partida.TiempoPartida;
import UtilsComunes.JFrames;

import java.awt.*;
import javax.swing.*;

import ConexionPartida.Movimientos;
import ConexionPartida.SalaInfo;
import ConexionPartida.ServidorSala;

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

	public static double tiempo;

	private int incremento;

	public static JLabel labelDeMovimientosPartida;
	public static JLabel labelTiempo;
	private static TiempoPartida temporizador = null;
	private String nombre = null;
	public static ArrastraPieza arrastraPieza;
	private JPanel panelTiempo, panelDerecha, panelTablero;

	public CrearTableroPartida(JFrame tablero, JButton[][] casillas, JButton casilla, JLabel textoFlotante, int tiempo,
			int incremento, String nombre) {
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
		CalculosEnPartida.getJugadas().clear();
		CalculosEnPartida.setJugadasTotales(0);

		tablero = new JFrame();
		JFrames.crearJFrameBasicos(tablero, nombre, 600, 600);

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

	public static void setTiempo(double tiempo) {
		CrearTableroPartida.tiempo = tiempo;
	}

	public void jpanelTablero() {
		// Panel central para el tablero de ajedrez, con tamaño fijo

		panelTablero = new JPanel(new GridLayout(9, 9));
		panelTablero.setPreferredSize(new Dimension(600, 600)); // Ampliado

		casillas = new JButton[9][9];
		textoFlotante = new JLabel();
		textoFlotante.setVisible(false);

		CrearTableroNormal crearTableroNormal = new CrearTableroNormal();
		crearTableroNormal.crearTablero(casillas, casilla, panelTablero, arrastraPieza, textoFlotante, true, true,
				false);
		Movimientos.setCasillas(casillas);

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
		panelTiempo = new JPanel();
		panelTiempo.setLayout(new BoxLayout(panelTiempo, BoxLayout.Y_AXIS));
		panelTiempo.setPreferredSize(new Dimension(250, 600));

		JLabel labelIzquierda = new JLabel("Tiempo");
		labelIzquierda.setFont(new Font("Arial", Font.BOLD, 24));

		if (tiempo < 0)
			tiempo = 0;
		labelTiempo = new JLabel();
		labelTiempo = crearLabelTiempo(tiempo, labelTiempo);

		if (!(tiempo == 0)) {
			setTemporizador(new TiempoPartida(labelTiempo, tiempo, casillas, incremento));
		}

		// Añade los componentes del tiempo
		panelTiempo.add(labelIzquierda);
		panelTiempo.add(labelTiempo);

		// Añade espacio
		panelTiempo.add(Box.createVerticalStrut(15));

		// Panel para mostrar el color de la sala
		JPanel panelColorSala = new JPanel();
		panelColorSala.setPreferredSize(new Dimension(100, 30)); // Tamaño pequeño

		// Colorea el panel según la variable de color
		Boolean colorSala = SalaInfo.isColor();
		if (colorSala != null) {
		    Boolean colorAJugar = ServidorSala.mov.isColorAJugar();
		    if (Boolean.TRUE.equals(colorAJugar)) {
		        panelColorSala.setBackground(Color.WHITE);
		    } else if (Boolean.FALSE.equals(colorAJugar)) {
		        panelColorSala.setBackground(Color.BLACK);
		    } else {
		        panelColorSala.setBackground(Color.WHITE);
		    }
		} else {
		    panelColorSala.setBackground(Color.WHITE);
		}

		// Añade el panel de color al panel principal, en la parte de abajo
		panelTiempo.add(panelColorSala);

	}

	public void setTemporizador(TiempoPartida temporizador) {
		CrearTableroPartida.temporizador = temporizador;
		CrearTableroPartida.temporizador.tiempoBlancas = tiempo * 60;
		CrearTableroPartida.temporizador.tiempoNegras = tiempo * 60;
	}

	public static JLabel crearLabelTiempo(double tiempo, JLabel labelTiempo) {
		String tiempoReloj = TiempoPartida.tiempoVisual(tiempo * 60);
		String tiempoRelojHTMLBlancas = "<span style='color: #FFD700; font-size:28px; font-weight:bold;'>" + tiempoReloj
				+ " <small style='font-size:14px;'>Blancas</small></span>";
		String tiempoRelojHTMLNegras = "<span style='color: #FFD700; font-size:28px; font-weight:bold;'>" + tiempoReloj
				+ " <small style='font-size:14px;'>Negras</small></span>";

		// Usar una tabla para centrar el contenido
		String html = "<html>" + "<table width='100%' height='100%'><tr><td align='center' valign='middle'>"
				+ tiempoRelojHTMLBlancas + "<br>" + tiempoRelojHTMLNegras + "</td></tr></table></html>";

		labelTiempo.setText(html);
		return labelTiempo;
	}

	public static TiempoPartida getTemporizador() {
		// TODO Auto-generated method stub
		return temporizador;
	}

	public static void cerrarTablero() {
		tablero.dispose();
	}

	public static double getTiempo() {
		return tiempo;
	}
}

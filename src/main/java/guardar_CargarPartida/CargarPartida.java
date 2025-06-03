package guardar_CargarPartida;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ConexionBBDD.UsuarioConectado;
import Partida.CalculosEnPartida;
import Partida.PosicionRepetida;
import Tablero.ArrastraPieza;
import Tablero.CrearTableroPartida;
import Tablero.CrearTableroNormal;
import Tablero.MetodosMoverPiezas;
import UtilsComunes.Botones;
import UtilsComunes.Colores;
import UtilsComunes.JFrames;
import interfazGrafica.EmpezarAJugar;
import interfazGrafica.JFrameInicioSesion;

public class CargarPartida implements Runnable {
	private static JFrame tablero;
	private JButton[][] casillas;
	private JButton casilla;
	private JLabel textoFlotante;
	private int indiceActual = 1; // 0 = tablero inicial, 1 = primer movimiento hecho, etc.
	private static boolean esCargarPartida = false;
	public static void setTiempo(double tiempo) {
		CrearTableroPartida.tiempo = tiempo;
	}

	public static JLabel labelDeMovimientosPartida;
	private String nombre = null;
	public static ArrastraPieza arrastraPieza;
	private JPanel panelTablero;

	public CargarPartida(JFrame tablero, JButton[][] casillas, JButton casilla, JLabel textoFlotante) {
		CrearTableroPartida.tablero = tablero;
		this.casillas = casillas;
		this.casilla = casilla;
		this.textoFlotante = textoFlotante;
	}

	@Override
	public void run() {
		esCargarPartida = true;
		CalculosEnPartida.getJugadas().clear();
		CalculosEnPartida.setJugadasTotales(0);

		crearTableroBasico();

	}

	public void crearTableroBasico() {
		tablero = new JFrame();
		JFrames.crearJFrameBasicos(tablero, nombre, 600, 1000);
		tablero.setLayout(new BorderLayout());

		jpanelTablero();

		// Crea y añade los paneles separados
		JPanel panelIzquierda = crearPanelGuardar();

		tablero.add(panelTablero, BorderLayout.CENTER);
		tablero.add(panelIzquierda, BorderLayout.WEST);

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

	public void jpanelTablero() {
		// Panel central para el tablero de ajedrez, con tamaño fijo

		panelTablero = new JPanel(new GridLayout(9, 9));
		panelTablero.setPreferredSize(new Dimension(600, 600)); // Ampliado

		casillas = new JButton[9][9];
		textoFlotante = new JLabel();
		textoFlotante.setVisible(false);

		CrearTableroNormal crearTableroNormal = new CrearTableroNormal();
		crearTableroNormal.crearTablero(casillas, casilla, panelTablero, arrastraPieza, textoFlotante, false, true,
				false);
	}


	private JPanel crearPanelGuardar() {
		JPanel panelGuardar = new JPanel();
		panelGuardar.setLayout(new BoxLayout(panelGuardar, BoxLayout.Y_AXIS));
		panelGuardar.setPreferredSize(new Dimension(200, 600));

		JLabel tituloGuardar = new JLabel("Cargar Partida");
		tituloGuardar.setFont(new Font("Arial", Font.BOLD, 18));
		tituloGuardar.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton botonRetroceder = Botones.crearBotonBasico("Retroceder", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
		Botones.addHoverEffect(botonRetroceder, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
		botonRetroceder.setEnabled(false);
		botonRetroceder.setAlignmentX(Component.CENTER_ALIGNMENT);
		botonRetroceder.addActionListener(e -> {
			if (indiceActual > 1) {

				indiceActual--;
				mostrarTableroHasta(CalculosEnPartida.getJugadas(), casillas, indiceActual - 1);
			}
		});

		// Botón Avanzar un movimiento
		JButton botonAvanzar = Botones.crearBotonBasico("Avanzar", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
		Botones.addHoverEffect(botonAvanzar, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
		botonAvanzar.setAlignmentX(Component.CENTER_ALIGNMENT);
		botonAvanzar.setEnabled(false);
		botonAvanzar.addActionListener(e -> {
			if (indiceActual <= CalculosEnPartida.getJugadas().size()) {
				indiceActual++;
				actualizarTableroHasta(indiceActual);
			}
		});

		// Botón Ir al principio
		JButton botonInicio = Botones.crearBotonBasico("Ir al principio", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
		Botones.addHoverEffect(botonInicio, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
		botonInicio.setEnabled(false);

		botonInicio.setAlignmentX(Component.CENTER_ALIGNMENT);
		botonInicio.addActionListener(e -> {
			indiceActual = 1;
			mostrarTableroHasta(CalculosEnPartida.getJugadas(), casillas, indiceActual - 1);
		});

		// Botón Ir al final
		JButton botonFinal = Botones.crearBotonBasico("Ir al final", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
		Botones.addHoverEffect(botonFinal, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
		botonFinal.setEnabled(false);
		botonFinal.setAlignmentX(Component.CENTER_ALIGNMENT);
		botonFinal.addActionListener(e -> {
			indiceActual = CalculosEnPartida.getJugadas().size() + 1;
			actualizarTableroHasta(indiceActual);
		});

		// Botón Salir (abajo del todo)
		JButton botonSalir = Botones.crearBotonBasico("Volver al inicio", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
		Botones.addHoverEffect(botonSalir, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
		botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
		botonSalir.addActionListener(e -> {
			// Lógica para salir
			EmpezarAJugar.getFrame().setVisible(true);
			esCargarPartida = false;
			tablero.dispose();
		});

		JButton botonCargar = Botones.crearBotonBasico("Cargar Partida", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
		Botones.addHoverEffect(botonCargar, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);

		botonCargar.setAlignmentX(Component.CENTER_ALIGNMENT);
		botonCargar.addActionListener(e -> {
			CalculosEnPartida.getJugadas().clear();
			CalculosEnPartida.setJugadasTotales(0);
			List<Partida> partidas = null;
				if (UsuarioConectado.usuarioConetado(JFrameInicioSesion.getUsuario())) {
					partidas = Funcion_Guardar_Cargar_Partida.cargarPartidaBBDD();
				}
			 else {
				partidas = Funcion_Guardar_Cargar_Partida.cargarPartidas();
			}
			if (partidas.isEmpty()) {
				JOptionPane.showMessageDialog(panelGuardar, "No hay partidas guardadas.");
				return;
			}
			String[] nombres = partidas.stream().map(Partida::getNombre).toArray(String[]::new);

			String seleccion = (String) JOptionPane.showInputDialog(panelGuardar, "Selecciona la partida a cargar:",
					"Cargar partida", JOptionPane.PLAIN_MESSAGE, null, nombres, nombres[0]);

			if (seleccion != null) {
				Partida partidaSeleccionada = partidas.stream().filter(p -> p.getNombre().equals(seleccion)).findFirst()
						.orElse(null);

				if (partidaSeleccionada != null) {
					List<String> jugadas = new ArrayList<>();
					jugadas = partidaSeleccionada.getJugadas();
					for (String movimiento : jugadas) {
						String[] partes = movimiento.split("-");
						if (partes.length == 3) {
							String ficha = partes[0];
							String origen = partes[1];
							String destino = partes[2];
							// MetodosMoverPiezas.moverPiezas(origen, destino, casillas, ficha);
							CalculosEnPartida.guardarMovimientos(origen, destino, ficha);
						}
					}
					// Actualiza tu interfaz aquí si es necesario
					JOptionPane.showMessageDialog(panelGuardar, "¡Partida cargada!");
				}
				CalculosEnPartida.setJugadasTotales(0);
				indiceActual = 1;
				mostrarTableroHasta(CalculosEnPartida.getJugadas(), casillas, indiceActual - 1);

				botonInicio.setEnabled(true);
				botonAvanzar.setEnabled(true);
				botonRetroceder.setEnabled(true);
				botonFinal.setEnabled(true);

			}

		});

		// Construcción del panel
		panelGuardar.add(Box.createVerticalStrut(20));
		panelGuardar.add(tituloGuardar);
		panelGuardar.add(Box.createVerticalStrut(20));
		panelGuardar.add(botonCargar);
		panelGuardar.add(Box.createVerticalGlue()); // Empuja el botón salir abajo
		panelGuardar.add(botonSalir);
		panelGuardar.add(Box.createVerticalStrut(10)); // Espacio inferior
		panelGuardar.add(botonRetroceder);
		panelGuardar.add(Box.createVerticalStrut(10));
		panelGuardar.add(botonAvanzar);
		panelGuardar.add(Box.createVerticalStrut(10));
		panelGuardar.add(botonInicio);
		panelGuardar.add(Box.createVerticalStrut(10));
		panelGuardar.add(botonFinal);
		panelGuardar.add(Box.createVerticalStrut(20));
		return panelGuardar;
	}

	private void actualizarTableroHasta(int hasta) {
		// Aplica movimientos hasta el índice indicado (sin incluir el movimiento en
		// 'hasta')
		for (int i = 1; i < hasta; i++) {
			String movimiento = CalculosEnPartida.getJugadas().get(i);
			String[] partes = movimiento.split("-");
			if (partes.length == 3) {
				String ficha = partes[0];
				String origen = partes[1];
				String destino = partes[2];
				if (destino.length() > 2) {
					ficha = destino.substring(2, 4);
				}
				MetodosMoverPiezas.moverPiezas(origen, destino, casillas, ficha, false);
			}
		}
		// Aquí puedes refrescar la interfaz si es necesario
	}

	public static boolean isEsCargarPartida() {
		return esCargarPartida;
	}

	public static void mostrarTableroHasta(HashMap<Integer, String> jugadas, JButton[][] casillas, int hasta) {
		// 1. Ordena las claves
		List<Integer> clavesOrdenadas = new ArrayList<>(jugadas.keySet());
		Collections.sort(clavesOrdenadas);

		// 2. Reinicia el tablero
		JButton[][] tableroInicial = PosicionRepetida.crearTableroInicial();
		for (int fila = 0; fila < 8; fila++) {
			for (int columna = 0; columna < 8; columna++) {
				casillas[fila][columna].setText(tableroInicial[fila][columna].getText());
				casillas[fila][columna].setIcon(tableroInicial[fila][columna].getIcon());
			}
		}

		// 3. Reaplica jugadas desde la primera hasta la número 'hasta' (inclusive)
		for (int clave : clavesOrdenadas) {
			if (clave > hasta)
				break;
			String movimiento = jugadas.get(clave);
			if (movimiento == null)
				continue;
			String[] partes = movimiento.split("-");
			if (partes.length != 3)
				continue;
			String ficha = partes[0];
			String origen = partes[1];
			String destino = partes[2];
			MetodosMoverPiezas.moverPiezas(origen, destino, casillas, ficha, false);
		}
	}

}

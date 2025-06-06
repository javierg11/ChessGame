package guardar_CargarPartida;

import java.awt.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.*;

import ConexionBBDD.UsuarioConectado;

import java.util.List;

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

public class GuardarPartida implements Runnable{

	private static JFrame tablero;
	private JButton[][] casillas;
	private JButton casilla;
	private JLabel textoFlotante;
	private static boolean guardandoPartida;

	public static boolean isGuardandoPartida() {
		return guardandoPartida;
	}

	public static void setTiempo(double tiempo) {
		CrearTableroPartida.tiempo = tiempo;
	}

	public static JLabel labelDeMovimientosPartida;
	private String nombre = null;
	public static ArrastraPieza arrastraPieza;
	private JPanel panelTablero;

	public GuardarPartida(JFrame tablero, JButton[][] casillas, JButton casilla,
			JLabel textoFlotante, String nombre) {
		CrearTableroPartida.tablero = tablero;
		this.casillas = casillas;
		this.casilla = casilla;
		this.textoFlotante = textoFlotante;
		this.nombre=nombre;

	}

	@Override
	public void run() {
		guardandoPartida=true;
		CalculosEnPartida.setJugadasTotales(0);
    	CalculosEnPartida.getJugadas().clear();

		crearTableroBasico();

	}

	public void crearTableroBasico() {
    	tablero=new JFrame();
        JFrames.crearJFrameBasicos(tablero,nombre,600,600);
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
		crearTableroNormal.crearTablero(casillas,casilla,panelTablero,arrastraPieza,textoFlotante,false,false,false);
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
	    	guardarPartida(panelGuardar);
	    	});



	    // Botón Retroceder
	    JButton botonRetroceder = Botones.crearBotonBasico("Retroceder", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
	    Botones.addHoverEffect(botonRetroceder, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
	    botonRetroceder.setAlignmentX(Component.CENTER_ALIGNMENT);
	    botonRetroceder.addActionListener(e -> {
	    	
	        deshacerUltimoMovimiento(CalculosEnPartida.getJugadas(), casillas);
	    });


	    // Botón Salir (abajo del todo)
	    JButton botonSalir = Botones.crearBotonBasico("Volver al inicio", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
	    Botones.addHoverEffect(botonSalir, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
	    botonSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
	    botonSalir.addActionListener(e -> {
	    	guardandoPartida=false;
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
	    if (CalculosEnPartida.getJugadas().size()<1)
	        return;
	    else if (CalculosEnPartida.getJugadasTotales()>=1)
	    	CalculosEnPartida.setJugadasTotales(0);
	    

	    // 1. Elimina el último movimiento
	    int ultimaClave = Collections.max(jugadas.keySet());
	    jugadas.remove(ultimaClave);

	    // 2. Haz una copia de los movimientos restantes
	    List<Integer> clavesOrdenadas = new ArrayList<>(jugadas.keySet());
	    Collections.sort(clavesOrdenadas);
	    List<String> movimientosRestantes = new ArrayList<>();
	    for (int clave : clavesOrdenadas) {
	        movimientosRestantes.add(jugadas.get(clave));
	    }

	    // 3. Borra el HashMap ANTES de aplicar los movimientos
	    CalculosEnPartida.getJugadas().clear();

	    // 4. Reinicia el tablero
	    JButton[][] tableroInicial = PosicionRepetida.crearTableroInicial();
	    for (int fila = 0; fila < 8; fila++) {
	        for (int columna = 0; columna < 8; columna++) {
	            casillas[fila][columna].setText(tableroInicial[fila][columna].getText());
	            casillas[fila][columna].setIcon(tableroInicial[fila][columna].getIcon());
	        }
	    }

	    // 5. Aplica los movimientos desde la copia
	    for (String movimiento : movimientosRestantes) {
	        String[] partes = movimiento.split("-");
	        if (partes.length != 3) {
	            System.err.println("Formato de movimiento incorrecto: " + movimiento);
	            continue;
	        }
	        String ficha = partes[0];
	        String origen = partes[1];
	        String destino = partes[2];
	        MetodosMoverPiezas.moverPiezas(origen, destino, casillas, ficha,false);
	    }
	}

	public static void guardarPartida(JPanel panelGuardar) {

        // Obtener las jugadas
        HashMap<Integer, String> jugadas = CalculosEnPartida.getJugadas();
        // Convertir jugadas a String para mostrarlas
        List<String> listaJugadas = new ArrayList<>();
        for (int i = 1; i <= jugadas.size(); i++) {
            if (jugadas.containsKey(i)) {
                listaJugadas.add(jugadas.get(i));
            }
        }
        // Crear el panel para pedir los datos
        JTextField nombreField = new JTextField();
        JTextField oponenteField = new JTextField();
        JTextField colorField = new JTextField("B");

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Nombre de la partida:"));
        panel.add(nombreField);
        panel.add(new JLabel("Nombre del oponente:"));
        panel.add(oponenteField);
        panel.add(new JLabel("Color ('B' o 'N'):"));
        panel.add(colorField);

        int result = JOptionPane.showConfirmDialog(
            panelGuardar,
            panel,
            "Guardar partida",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText().trim();
            String nombreOponente = oponenteField.getText().trim();
            char color = colorField.getText().trim().toUpperCase().charAt(0);
            if (color != 'B' && color != 'N') color = 'B'; // Por defecto 'B'

            if (JFrameInicioSesion.getUsuario()!=null) {
                if (UsuarioConectado.usuarioConetado(JFrameInicioSesion.getUsuario())) {
                    // Usuario conectado: guardar en BBDD
                    Funcion_Guardar_Cargar_Partida.guardarPartidaBBDD(nombre, nombreOponente, color, jugadas);
                    JOptionPane.showMessageDialog(panelGuardar, "¡Partida guardada en la base de datos!");
                } 
            }else {
                    // Usuario no conectado: guardar en JSON
                    Funcion_Guardar_Cargar_Partida.guardarPartida(nombre, jugadas, nombreOponente, color);
                    JOptionPane.showMessageDialog(panelGuardar, "¡Partida guardada!");
                }
            
        }
    
	}



}

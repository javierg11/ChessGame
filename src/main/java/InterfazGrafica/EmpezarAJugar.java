package InterfazGrafica;

import javax.swing.*;

import ConstantesComunes.Botones;
import ConstantesComunes.Colores;
import ConstantesComunes.JFrames;
import Partida.CalculosEnPartida;
import Tablero.TableroAjedrez;

import java.awt.*;

public class EmpezarAJugar {
    private static JFrame frame;
    private static JButton btnSolo, btnAlguien, btnProblemas, btnPartida, esquinaButton = null;

    public static void opcionesDeJuego() {
        CalculosEnPartida.getJugadas().clear();
        CalculosEnPartida.setJugadasTotales(0);

        frame = new JFrame();
        frame = JFrames.crearJFrameBasicos(frame, "Menú de Ajedrez", 420, 360);

        // Panel personalizado para el fondo degradado
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(181, 136, 99);    // Marrón terracota
                Color color2 = new Color(240, 217, 181);   // Beige cálido
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        panelFondo.setLayout(null); // Para permitir el botón de volver en la esquina

        // Panel central con BoxLayout para los botones
        JPanel panelCentral = new JPanel();
        panelCentral.setOpaque(false); // Para que se vea el degradado
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

        // Espaciador superior
        panelCentral.add(Box.createVerticalStrut(15));

        btnSolo = Botones.crearBotonBasico("Jugar Solo", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
        btnAlguien = Botones.crearBotonBasico("Jugar con Alguien", Colores.CASILLAS_BLANCAS, Color.BLACK, 17);
        btnProblemas = Botones.crearBotonBasico("Problemas", Colores.CASILLAS_NEGRAS, Color.WHITE, 17);
        btnPartida = Botones.crearBotonBasico("Partida", Colores.CASILLAS_BLANCAS, Color.BLACK, 17);

        // Tamaño uniforme
        Dimension d = new Dimension(240, 42);
        btnSolo.setMaximumSize(d);
        btnAlguien.setMaximumSize(d);
        btnProblemas.setMaximumSize(d);
        btnPartida.setMaximumSize(d);

        // Centra los botones
        btnSolo.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAlguien.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnProblemas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPartida.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Añade botones y separadores
        panelCentral.add(btnSolo);
        panelCentral.add(Box.createVerticalStrut(18));
        panelCentral.add(btnAlguien);
        panelCentral.add(Box.createVerticalStrut(18));
        panelCentral.add(btnProblemas);
        panelCentral.add(Box.createVerticalStrut(18));
        panelCentral.add(btnPartida);

        // Botón de volver en la esquina superior izquierda (posición absoluta)
        esquinaButton = Botones.crearBotonBasico("←", new Color(44, 36, 24), Color.WHITE, 15);
        esquinaButton.setBounds(10, 10, 50, 40);

        // Coloca el panel central centrado en la ventana
        panelCentral.setBounds(90, 40, 240, 260);
        panelFondo.add(panelCentral);
        panelFondo.add(esquinaButton);

        // Efectos hover
        Botones.addHoverEffect(btnSolo, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
        Botones.addHoverEffect(btnAlguien, Colores.CASILLAS_BLANCAS, Colores.CASILLAS_BLANCAS_OSCURO);
        Botones.addHoverEffect(btnProblemas, Colores.CASILLAS_NEGRAS_OSCURO, Colores.CASILLAS_NEGRAS_OSCURO);
        Botones.addHoverEffect(btnPartida, Colores.CASILLAS_BLANCAS, Colores.CASILLAS_BLANCAS_OSCURO);
        Botones.addHoverEffect(esquinaButton, new Color(44, 36, 24), Colores.CASILLAS_NEGRAS_OSCURO);

        // Listeners de los botones
        btnSolo.addActionListener(e -> {
            JTextField tiempoField = new JTextField(5);
            JTextField incrementoField = new JTextField(5);

            JCheckBox blancasCheck = new JCheckBox("Jugar con blancas");

            JPanel panel = new JPanel();
            panel.add(new JLabel("Tiempo (minutos):"));
            panel.add(tiempoField);
            panel.add(Box.createHorizontalStrut(15));
            panel.add(new JLabel("Incremento (segundos):"));
            panel.add(incrementoField);
            panel.add(Box.createVerticalStrut(15));
            panel.add(blancasCheck);

            int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Configura la partida",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                frame.dispose();

                int tiempo;
                try {
                    tiempo = Integer.parseInt(tiempoField.getText());
                } catch (NumberFormatException a) {
                    tiempo = 0;
                }

                int incremento;
                try {
                    incremento = Integer.parseInt(incrementoField.getText());
                } catch (NumberFormatException a) {
                    incremento = 0;
                }
                boolean blancas = blancasCheck.isSelected();
                frame.dispose();

                TableroAjedrez.crearTipoTablero(true, tiempo, incremento, blancas, "Tablero Ajedrez");
            }
        });

        btnAlguien.addActionListener(e -> {
            frame.dispose();
            JugarEnLAN.crearJFrameJugarEnLAN();
        });

        btnProblemas.addActionListener(e -> {
            frame.dispose();
            TableroAjedrez.crearTipoProblemas(true, 0, 0, "Problemas Ajedrez");
        });

        btnPartida.addActionListener(e -> {
            // Crear un panel personalizado
            JPanel panel = new JPanel();
            panel.setBackground(Color.WHITE);

            JLabel label = new JLabel("¿Qué acción deseas realizar?");
            label.setBackground(Color.WHITE);
            label.setOpaque(true);
            panel.add(label);

            String[] opciones = {"Guardar partida", "Cargar partida", "Cancelar"};

            // Cambiar el fondo de los botones (opcional)
            UIManager.put("Button.background", new java.awt.Color(200, 200, 255));

            int seleccion = JOptionPane.showOptionDialog(
                frame,
                panel,
                "Partida",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            if (seleccion == 0) {
                TableroAjedrez.crearTipoGuadar();
            } else if (seleccion == 1) {
                TableroAjedrez.crearTipoCargar();
            }
        });



        esquinaButton.addActionListener(e -> {
            PantallaPrincipalJuego pantallaPrincipalJuego = new PantallaPrincipalJuego();
            pantallaPrincipalJuego.mostrar();
        });

        frame.setContentPane(panelFondo);
        frame.setVisible(true);
    }

	public static JFrame getFrame() {
		return frame;
	}
    
    
    
}

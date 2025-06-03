package interfazGrafica;

import javax.swing.*;

import ConexionBBDD.UsuarioConectado;
import Tablero.TableroAjedrez;
import UtilsComunes.Botones;
import UtilsComunes.Colores;
import UtilsComunes.JFrames;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PantallaPrincipalJuego {
    // Atributos de la clase
    private JFrame frame;
    private JPanel centerPanel, botonesPanel;
    private JLabel titleLabel, subtitleLabel;
    private JButton startButton, inicioSesionButton;

    public PantallaPrincipalJuego() {
        crearPantallaPrincipalJuego();
    }

    private void crearTextoJFrame() {
        // Título
        titleLabel = new JLabel("Juego de Ajedrez");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 60));
        titleLabel.setForeground(new Color(245, 245, 220));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo
        subtitleLabel = new JLabel("¡Bienvenido al juego de ajedrez!");
        subtitleLabel.setFont(new Font("Helvetica", Font.PLAIN, 30));
        subtitleLabel.setForeground(new Color(245, 245, 220));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Espaciado superior
        centerPanel.add(Box.createVerticalStrut(150));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(subtitleLabel);
    }

    private void crearBotonesJFrame() {
        // Panel de botones
        botonesPanel = new JPanel();
        botonesPanel.setOpaque(false);
        botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.X_AXIS));

        // Botón "Jugar"
        startButton=Botones.crearBotonBasico("Jugar Partida",Colores.CASILLAS_BLANCAS,Color.BLACK,28);
        startButton.setBorder(BorderFactory.createEmptyBorder(18, 60, 18, 60));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón "Iniciar Sesión"
        inicioSesionButton=Botones.crearBotonBasico("Iniciar Sesión",Colores.CASILLAS_NEGRAS,Color.WHITE,28);
        inicioSesionButton.setBorder(BorderFactory.createEmptyBorder(18, 60, 18, 60));
        inicioSesionButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Efecto hover para "Jugar"        
        Botones.addHoverEffect(startButton,Colores.CASILLAS_BLANCAS,Colores.CASILLAS_BLANCAS_OSCURO);
        // Efecto hover para "Iniciar Sesión"
        Botones.addHoverEffect(inicioSesionButton,Colores.CASILLAS_NEGRAS,Colores.CASILLAS_NEGRAS_OSCURO);
       

        // Espacio entre botones
        botonesPanel.add(startButton);
        botonesPanel.add(Box.createRigidArea(new Dimension(60, 0)));
        botonesPanel.add(inicioSesionButton);

        centerPanel.add(Box.createVerticalStrut(80));
        centerPanel.add(botonesPanel);

        frame.add(centerPanel, BorderLayout.CENTER);

        // Acción del botón "Jugar"
        startButton.addActionListener(e -> {
        	frame.dispose();
            EmpezarAJugar.opcionesDeJuego();
        });
     // Acción del botón "IniciarSesion"
        inicioSesionButton.addActionListener(e -> {
            frame.dispose();
            JFrameInicioSesion.crearJFrameInicioSesion();
        });
    }

    private void crearPantallaPrincipalJuego() {
    	frame=new JFrame();
        frame = JFrames.crearJFrameBasicos(frame,"Chess Master",800, 600);

        frame.setLayout(new BorderLayout());

        // Imagen de fondo
        ImageIcon fondoIcon = new ImageIcon(TableroAjedrez.class.getResource("/fondo/fondo.jpg"));
        centerPanel = new ImagePanel(fondoIcon);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Icono de ajedrez

        crearTextoJFrame();
        crearBotonesJFrame();
    }

    // Clase interna para panel con imagen de fondo
    private class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(ImageIcon imageIcon) {
            this.backgroundImage = imageIcon.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	UIManager.put("OptionPane.messageFont", new Font("Palatino Linotype", Font.BOLD, 15));
    	 Runtime.getRuntime().addShutdownHook(new Thread(() -> {
             onExit();
         }));
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.put("OptionPane.background", new java.awt.Color(255, 255, 255));
            UIManager.put("Panel.background", new java.awt.Color(255, 255, 255));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            PantallaPrincipalJuego pantalla = new PantallaPrincipalJuego();
            pantalla.mostrar();
        });
       
    }
    public static void onExit() {
        String ARCHIVO_JSON = "GuardarPartida" + File.separator + "partidas2.txt";
        String contenido = "Este es un ejemplo de contenido para guardar en el archivo.";

        // Asegúrate de que la carpeta existe
        File directorio = new File("GuardarPartida");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        // Escribir en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_JSON, true))) { // true para agregar al final
            writer.write(contenido);
            writer.newLine(); // Salto de línea
            System.out.println("Contenido escrito correctamente en el archivo.");
            if (UsuarioConectado.usuarioConetado(JFrameInicioSesion.getUsuario())) {
            		UsuarioConectado.cerrar_Iniciar_Sesion(JFrameInicioSesion.getUsuario(),false);
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
        
    }
}

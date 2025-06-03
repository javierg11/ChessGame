package interfazGrafica;
import javax.swing.*;


import ConexionBBDD.CrearUsuario;
import ConexionBBDD.IniciarSesion;
import UtilsComunes.Botones;
import UtilsComunes.Colores;
import UtilsComunes.JFrames;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
public class JFrameInicioSesion {
	static final String tipoLetra="Georgia";
	static GridBagConstraints gbc=null;
	static JTextField userText,emailText=null;
	static JLabel passLabel,emailLabel,userLabel,titleLabel=null;
	static JPasswordField passText=null;
	static JPanel backgroundPanel=null;
	static JFrame frame=null;
	static JButton esquinaButton,actionButton,switchButton=null;

	private static String usuario=null;
    // Estado del formulario: true = login, false = registro
    private static boolean isLogin = true;

    // Clase personalizada para campos de texto con bordes redondeados
    public static class JERoundTextField extends JTextField {
        private int arcw = 20; // Ancho del arco de redondeo
        private int arch = 20; // Alto del arco de redondeo

        public JERoundTextField(int columns) {
            super(columns);
            setOpaque(false); // Permite dibujar el fondo personalizado
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Margen interno
        }

        // Ajusta los márgenes internos para que el texto no toque los bordes redondeados
        @Override
        public Insets getInsets() {
            Insets insets = super.getInsets();
            return new Insets(insets.top, 15, insets.bottom, 15);
        }

        // Dibuja el fondo redondeado
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Shape round = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, arcw, arch);
            g2.setColor(getBackground());
            g2.fill(round);
            g2.dispose();
            super.paintComponent(g);
        }

        // Dibuja el borde redondeado
        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Shape round = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, arcw, arch);
            g2.setColor(Color.GRAY); // Color del borde
            g2.setStroke(new BasicStroke(3f)); // Grosor del borde
            g2.draw(round);
            g2.dispose();
        }
    }

    // Clase personalizada para campos de contraseña con bordes redondeados
    public static class JERoundPasswordField extends JPasswordField {
        private int arcw = 20;
        private int arch = 20;

        public JERoundPasswordField(int columns) {
            super(columns);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        }

        @Override
        public Insets getInsets() {
            Insets insets = super.getInsets();
            return new Insets(insets.top, 15, insets.bottom, 15);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Shape round = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, arcw, arch);
            g2.setColor(getBackground());
            g2.fill(round);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Shape round = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, arcw, arch);
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(3f));
            g2.draw(round);
            g2.dispose();
        }
    }

    
    public static void JButtonsConfiracion() {
    	// Panel para los botones (centrados)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        buttonPanel.setOpaque(false);
        
        // Botón principal de acción (Iniciar sesión o Registrar)
        actionButton=Botones.crearBotonBasico("Iniciar Sesión",Colores.CASILLAS_NEGRAS,Color.WHITE,17);
        actionButton.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));

        // Botón para cambiar entre inicio de sesión y registro
        switchButton=Botones.crearBotonBasico("Regístrate",Colores.CASILLAS_BLANCAS,Color.BLACK,17);
        switchButton.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        
        // --- Botón en la esquina superior izquierda ---
        esquinaButton=Botones.crearBotonBasico("←",new Color(44, 36, 24),Color.WHITE,18);
        esquinaButton.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        // Cambia el cursor a mano cuando el ratón está sobre el botón de acción
        Botones.addHoverEffect(actionButton, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
        Botones.addHoverEffect(esquinaButton, new Color(44, 36, 24), Colores.CASILLAS_NEGRAS_OSCURO);
        Botones.addHoverEffect(switchButton, Colores.CASILLAS_BLANCAS, Colores.CASILLAS_BLANCAS_OSCURO);

        esquinaButton.addActionListener(e -> {
            frame.dispose();
            PantallaPrincipalJuego pantallaPrincipalJuego = new PantallaPrincipalJuego();
			pantallaPrincipalJuego.mostrar();
        });
     // Acción del botón principal (solo muestra un mensaje de demostración)
        actionButton.addActionListener(e -> {
            usuario = userText.getText();
            String pass = new String(passText.getPassword());
            String correo = emailText.getText();

            if (isLogin) {
                IniciarSesion.iniciarSesion(usuario, pass, frame);
            } else {
            	CrearUsuario.crearUsuario(usuario, pass,correo, frame);
            	}
        });
        // Acción para cambiar entre login y registro
        switchButton.addActionListener(e -> {
            isLogin = !isLogin;
            if (isLogin) {
                titleLabel.setText("Inicio de sesión");
                actionButton.setText("Iniciar sesión");
                switchButton.setText("Regístrate");
                emailLabel.setVisible(false);
                emailText.setVisible(false);
                
                // Mover el botón a la izquierda
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 1;
                gbc.weightx = 0;
                gbc.weighty = 0;
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.insets = new Insets(-60, 10, 0, 0); 
                backgroundPanel.add(esquinaButton, gbc);
            } else {
                titleLabel.setText("Registro");
                actionButton.setText("Registrar");
                switchButton.setText("Inicia sesión");
                emailLabel.setVisible(true);
                emailText.setVisible(true);
                
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 1;
                gbc.weightx = 0;
                gbc.weighty = 0;
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.insets = new Insets(-43, 10, 0, 0); 
                backgroundPanel.add(esquinaButton, gbc);
            }
            userText.setText("");
            passText.setText("");
            emailText.setText("");
        });

        // Añadir botones al panel de botones
        buttonPanel.add(actionButton);
        buttonPanel.add(switchButton);

        // Añadir el panel de botones al panel principal
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(buttonPanel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(-60, 10, 0, 0); 
        backgroundPanel.add(esquinaButton, gbc);
    }
	public static void crearJFrameInicioSesion() {
    	frame=new JFrame();
        frame=JFrames.crearJFrameBasicos(frame,"Inicio de sesión / Registro",450,370);
        crearCamposJFrame();

//      Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ruta/a/tu/icono.png"));
//      frame.setIconImage(icono);

      
      JButtonsConfiracion();
      

      

      // Añadir el panel principal al frame y mostrar la ventana
      frame.getContentPane().add(backgroundPanel);
    }
    
    public static void crearCamposJFrame() {
        // Panel de fondo con degradado personalizado
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth(), h = getHeight();
                Color color1 = new Color(181, 136, 99);    // Marrón terracota (CASILLAS_NEGRAS)
                Color color2 = new Color(240, 217, 181);   // Beige cálido (CASILLAS_BLANCAS)
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        // --- Título principal del formulario ---
        titleLabel = new JLabel("Inicio de sesión", SwingConstants.CENTER);
        titleLabel.setFont(new Font(tipoLetra, Font.BOLD, 30)); 
        titleLabel.setForeground(new Color(245, 245, 220)); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 40, 10); 
        backgroundPanel.add(titleLabel, gbc);

        // --- Resto de componentes ---
        gbc.insets = new Insets(4, 10, 4, 10);
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Etiqueta y campo de usuario
        userLabel = new JLabel("Usuario:");
        userLabel.setFont(new Font(tipoLetra, Font.PLAIN, 16));
        userText = new JERoundTextField(18);
        userText.setBackground(Color.WHITE);
        gbc.gridy = 1;
        backgroundPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        backgroundPanel.add(userText, gbc);

        // Etiqueta y campo de contraseña
        gbc.gridx = 0;
        gbc.gridy = 2;
        passLabel = new JLabel("Contraseña:");
        passLabel.setFont(new Font(tipoLetra, Font.PLAIN, 16));
        passText = new JERoundPasswordField(18);
        passText.setBackground(Color.WHITE);
        backgroundPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        backgroundPanel.add(passText, gbc);

        // Etiqueta y campo de correo electrónico (solo para registro)
        gbc.gridx = 0;
        gbc.gridy = 3;
        emailLabel = new JLabel("Correo electrónico:");
        emailLabel.setFont(new Font(tipoLetra, Font.PLAIN, 16));
        emailText = new JERoundTextField(18);
        emailText.setBackground(Color.WHITE);
        backgroundPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        backgroundPanel.add(emailText, gbc);
        emailLabel.setVisible(false); // Oculto por defecto, solo se ve en registro
        emailText.setVisible(false);
    }
	public static String getUsuario() {
		return usuario;
	}

}

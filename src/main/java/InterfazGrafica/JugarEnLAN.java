package interfazGrafica;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ConexionPartida.ServidorSala;
import ConexionPartida.ClienteSala;
import ProblemasAjedrez.CrearTableroProblemas;
import UtilsComunes.Botones;
import UtilsComunes.Colores;
import UtilsComunes.JFrames;

public class JugarEnLAN {
	static final String tipoLetra="Georgia";
	static GridBagConstraints gbc=null;
	static JPanel backgroundPanel=null;
	private static JFrame frame=null;
	public static JFrame getFrame() {
		return frame;
	}

	public static JButton esquinaButton,crearPartidaButton,unirsePartidaButton=null;
	 
    
    
    public static void JButtonsConfiracion() {
    	// Panel para los botones (centrados)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        buttonPanel.setOpaque(false);
        
        // Botón principal de acción (Iniciar sesión o Registrar)
        crearPartidaButton=Botones.crearBotonBasico("Crear Partida",Colores.CASILLAS_NEGRAS,Color.WHITE,17);

        // Botón para cambiar entre inicio de sesión y registro
        unirsePartidaButton=Botones.crearBotonBasico("Unirse a Partida",Colores.CASILLAS_BLANCAS,Color.BLACK,17);
        
     // Acción del botón principal (solo muestra un mensaje de demostración)
        crearPartidaButton.addActionListener(e -> {
        	new Thread(() -> {
            	ServidorSala sErvSala = new ServidorSala();
            	sErvSala.crearSala(backgroundPanel,frame);
            }).start();
        });
        unirsePartidaButton.addActionListener(e -> {
            new Thread(() -> {
            	ClienteSala cliSala = new ClienteSala();
            	cliSala.refrescarSalas(backgroundPanel,buttonPanel,frame);
            }).start();
        });
        // --- Botón en la esquina superior izquierda ---
        esquinaButton=Botones.crearBotonBasico("←",new Color(44, 36, 24),Color.WHITE,15);
        esquinaButton.addActionListener(e -> {
            // Crear y mostrar el nuevo JFrame
        	ServidorSala.algo();
        	frame.dispose();
            EmpezarAJugar.getFrame().setVisible(true);
            
        });        
        // Cambia el cursor a mano cuando el ratón está sobre el botón de acción
        Botones.addHoverEffect(crearPartidaButton, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
        Botones.addHoverEffect(unirsePartidaButton, Colores.CASILLAS_BLANCAS, Colores.CASILLAS_BLANCAS_OSCURO);
        Botones.addHoverEffect(esquinaButton, new Color(44, 36, 24), Colores.CASILLAS_NEGRAS_OSCURO);
        // Añadir botones al panel de botones
        buttonPanel.setOpaque(false);
        buttonPanel.add(crearPartidaButton);
        buttonPanel.add(unirsePartidaButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0)); // 30 píxeles de margen abajo

        
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(esquinaButton);
        // Layout principal
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(backgroundPanel);
    }
    public static void crearJFrameJugarEnLAN() {
    	frame=new JFrame();
        frame=JFrames.crearJFrameBasicos(frame,"Partida en LAN",450,370);
        crearCamposJFrame();

//      Image icono = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ruta/a/tu/icono.png"));
//      frame.setIconImage(icono);

      
      JButtonsConfiracion();
      

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
    }

}

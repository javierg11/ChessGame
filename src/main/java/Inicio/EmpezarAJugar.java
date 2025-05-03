package Inicio;

import javax.swing.*;

import ConstantesComunes.Botones;
import ConstantesComunes.Colores;
import ConstantesComunes.JFrames;
import Tablero.TableroAjedrez;

import java.awt.*;

public class EmpezarAJugar {
	private static JFrame frame;
	private static JButton btnSolo,btnAlguien,btnProblemas,esquinaButton=null;
    public static void opcionesDeJuego() {
    	frame=new JFrame();
    	frame=JFrames.crearJFrameBasicos(frame,"Menú de Ajedrez",420,320);

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
        panelFondo.setLayout(null); // Layout absoluto para mayor control


        // Configuración de botones
        btnSolo=Botones.crearBotonBasico("Jugar Solo",Colores.CASILLAS_NEGRAS,Color.WHITE,17);
        btnSolo.setBounds(60, 60, 300, 45);

        btnAlguien=Botones.crearBotonBasico("Jugar con Alguien",Colores.CASILLAS_BLANCAS,Color.BLACK,17);
        btnAlguien.setBounds(100, 130, 220, 40);

        btnProblemas=Botones.crearBotonBasico("Problemas",Colores.CASILLAS_NEGRAS,Color.WHITE,17);
        btnProblemas.setBounds(140, 200, 140, 35);

        esquinaButton=Botones.crearBotonBasico("←",new Color(44, 36, 24),Color.WHITE,15);
        esquinaButton.setBounds(4, 10, 50, 40);

        // Acción de cada botón
        btnSolo.addActionListener(e ->{
        	frame.dispose();
        	TableroAjedrez.crearTipoTablero(true);
        });
        btnAlguien.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "¡Has elegido jugar con alguien!");
        });
        btnProblemas.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "¡Has elegido resolver problemas!");
        } );

        esquinaButton.addActionListener(e -> {
            frame.dispose();
            PantallaPrincipalJuego pantallaPrincipalJuego = new PantallaPrincipalJuego();
			pantallaPrincipalJuego.mostrar();
        });
        // Efecto hover para cada botón
        Botones.addHoverEffect(btnSolo, Colores.CASILLAS_NEGRAS, Colores.CASILLAS_NEGRAS_OSCURO);
        Botones.addHoverEffect(btnAlguien, Colores.CASILLAS_BLANCAS, Colores.CASILLAS_BLANCAS_OSCURO);
        Botones.addHoverEffect(btnProblemas, Colores.CASILLAS_NEGRAS_OSCURO, Colores.CASILLAS_NEGRAS_OSCURO);
        Botones.addHoverEffect(esquinaButton, new Color(44, 36, 24), Colores.CASILLAS_NEGRAS_OSCURO);

        // Añadir los botones al panel
        panelFondo.add(esquinaButton);
        panelFondo.add(btnSolo);
        panelFondo.add(btnAlguien);
        panelFondo.add(btnProblemas);
        frame.setContentPane(panelFondo);
    }

}

package UtilsComunes;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

import Tablero.TableroAjedrez;

public class JFrames {
	public static JFrame crearJFrameBasicos(JFrame frame,String titulo, int windth, int height) {
        frame.setTitle(titulo);
        frame.setSize(windth, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
        Image icono = Toolkit.getDefaultToolkit().getImage(TableroAjedrez.class.getResource("/logo/logoApp.jpg"));
        frame.setIconImage(icono);

		return frame;
	}
}

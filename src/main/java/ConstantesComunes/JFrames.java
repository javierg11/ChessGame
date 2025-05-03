package ConstantesComunes;

import javax.swing.JFrame;

public class JFrames {
	public static JFrame crearJFrameBasicos(JFrame frame,String titulo, int windth, int height) {
        frame.setTitle(titulo);
        frame.setSize(windth, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		return frame;
	}
}

import javax.swing.*;

import Piezas.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableroAjedrez {
	private final static int casillasFilas = 9;
	private final static int casillasColumnas = 9;

	public static JFrame tablero = null;
	public static JButton casilla = null;
	public static JButton[][] casillas = null;

	public static void main(String[] args) {
		tablero = new JFrame("Tablero de Ajedrez Interactivo");

		tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tablero.setLayout(new GridLayout(9, 9));

		casillas = new JButton[9][9];
		// JLabel posicion = new JLabel();
		for (int fila = 0; fila < casillasFilas; fila++) {
			for (int columna = 0; columna < casillasColumnas; columna++) {
				casilla = new JButton();
				casilla.setOpaque(true);
				casilla.setBorderPainted(false);

				if ((fila == 8)) {
					casilla.setEnabled(false);
					casilla.setText("" + columna);
				} else if (columna == 8) {
					casilla.setEnabled(false);
					casilla.setText("" + fila);
				} else if ((fila + columna) % 2 == 0) {
					casilla.setBackground(Color.WHITE);
				} else {
					casilla.setBackground(Color.BLACK);
				}

				casillas[fila][columna] = casilla;
				if (fila == 0 && columna == 1) {
					casilla.setText("bT");
				}
				if (fila == 2 && columna == 5) {
					casilla.setText("bT");
				}
				if (fila == 2 && columna == 1) {
					casilla.setText("wT");
				}
				
				if (fila == 0 && columna == 5) {
					casilla.setText("wT");
				}
				if (fila == 0 && columna == 3) {
					casilla.setText("wT");
				}

				// Asignar el ActionListener externo
                casilla.addActionListener(new CasillaClickListener());
                
                casillas[fila][columna] = casilla;
                tablero.add(casilla);
			}
		}

		tablero.setSize(500, 500);
		tablero.setVisible(true);
		tablero.setLocationRelativeTo(null);
		tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tablero.setResizable(false);
	}

	public static String identificarPiezaParaMover(String ficha, String posicion, JButton[][] casillas) {
		Piezas pieza;
	    if (ficha.contains("T")) {
	    	pieza = new Torre();
	    	return pieza.calcularMovimientos(posicion,casillas,ficha);
	    }
	    else
	    	return "No";	    
	}
	
	// Clase interna para manejar los clicks
    private static class CasillaClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ficha = "";
            String posicion = "";
            
            for (int i = 0; i < casillasFilas; i++) {
                for (int j = 0; j < casillasColumnas; j++) {
                    if (casillas[i][j] == e.getSource()) {
                        ficha = casillas[i][j].getText();
                        posicion = "" + i + j;
                        System.out.println(identificarPiezaParaMover(ficha, posicion, casillas));
                        System.out.println("Pieza: " + casillas[i][j].getText() + "[" + i + "][" + j + "]");
                        break;
                    }
                }
            }
        }
    }
	
	
	
}

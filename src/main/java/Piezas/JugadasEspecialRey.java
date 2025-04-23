package Piezas;

import java.awt.Color;


import javax.swing.JButton;

public class JugadasEspecialRey {
	private final static Color colorRojo = new Color(255, 102, 102);

	public static boolean quitarJaque(JButton[][] casillas, int fila, int columna, String ficha) {
		JButton[][] casillasCopia = new JButton[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
		        casillasCopia[i][j] = new JButton();
		        casillasCopia[i][j].setText(casillas[i][j].getText());
		        casillasCopia[i][j].setBackground(casillas[i][j].getBackground());
		        }
		}

        casillasCopia[fila][columna].setText(ficha);
        	darJaque(casillasCopia);
		
        	for (int i = 0; i < 8; i++) {
        	    for (int j = 0; j < 8; j++) {
        	        Color bg = casillasCopia[i][j].getBackground();
        	        if (bg != null && bg.equals(colorRojo)) {
        	            return false;
        	        }
        	    }
        	}


		return true;
	}

	public static void darJaque(JButton[][] casillas) {
		String posicionReyNegro = "";
		String posicionReyBlanco = "";

		// 1. Buscar la posición de ambos reyes
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (casillas[i][j].getText().equals("wR")) {
					posicionReyBlanco = i + "" + j;
				}
				if (casillas[i][j].getText().equals("bR")) {
					posicionReyNegro = i + "" + j;
				}
			}
		}

		boolean jaqueAlNegro = false;
		boolean jaqueAlBlanco = false;

		// Recorrer todas las piezas
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				String pieza = casillas[i][j].getText();
				if (pieza.equals(""))
					continue; // Casilla vacía

				// Crear las piezas
				Piezas piezaObj = null;
				String color = pieza.substring(0, 1); // "w" o "b"
				String tipo = pieza.substring(1, 2); // "D", "T", "A", "C", "P", "R"

				switch (tipo) {
				case "D":
					piezaObj = new Dama();
					break;
				case "T":
					piezaObj = new Torre();
					break;
				case "A":
					piezaObj = new Alfil();
					break;
				case "C":
					piezaObj = new Caballo();
					break;
				case "P":
					piezaObj = new Peon();
					break;
				default:
					continue; // Si es rey, saltar
				}

				String movimientos = piezaObj.calcularMovimientos(i + "" + j, casillas, pieza, false);
				String[] movimientosValidos = movimientos.split(" ");

				// Ver si alguno de los movimientos da jaque
				for (String movimiento : movimientosValidos) {
					if (color.equals("w") && movimiento.equals(posicionReyNegro)) {
						// Jaque al negro
						int fila = Integer.parseInt(posicionReyNegro.substring(0, 1));
						int col = Integer.parseInt(posicionReyNegro.substring(1, 2));
						casillas[fila][col].setBackground(colorRojo);
						System.out.println("Jaque al Negro por " + pieza + " en " + i + "," + j);
						jaqueAlNegro = true;
					}
					if (color.equals("b") && movimiento.equals(posicionReyBlanco)) {
						// Jaque al blanco
						int fila = Integer.parseInt(posicionReyBlanco.substring(0, 1));
						int col = Integer.parseInt(posicionReyBlanco.substring(1, 2));
						casillas[fila][col].setBackground(colorRojo);
						System.out.println("Jaque al Blanco por " + pieza + " en " + i + "," + j);
						jaqueAlBlanco = true;
					}
				}
			}
		}

		// Quitar la casilla de jaque del rey
		if (!jaqueAlNegro && !posicionReyNegro.isEmpty()) {
			int fila = Integer.parseInt(posicionReyNegro.substring(0, 1));
			int col = Integer.parseInt(posicionReyNegro.substring(1, 2));
			casillas[fila][col].setBackground(null);
		}
		if (!jaqueAlBlanco && !posicionReyBlanco.isEmpty()) {
			int fila = Integer.parseInt(posicionReyBlanco.substring(0, 1));
			int col = Integer.parseInt(posicionReyBlanco.substring(1, 2));
			casillas[fila][col].setBackground(null);
		}

	}

	
}

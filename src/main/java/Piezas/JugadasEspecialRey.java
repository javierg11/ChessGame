package Piezas;

import java.awt.Color;


import javax.swing.JButton;

public class JugadasEspecialRey {
	private final static Color colorRojo = new Color(255, 102, 102);

	public static boolean crearTableroDePrueba(JButton[][] casillas, int fila, int columna, String ficha,int filaInicial, int ColumnaACtual) {
		JButton[][] casillasCopia = new JButton[8][8];
		
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    casillasCopia[i][j] = new JButton();
                    casillasCopia[i][j].setText(casillas[i][j].getText());
                    casillasCopia[i][j].setBackground(casillas[i][j].getBackground());
                }
            }

            // Simula el movimiento
            casillasCopia[fila][columna].setText(ficha);
            casillasCopia[filaInicial][ColumnaACtual].setText("");
            // Recalcula jaques y colorea
            detertarPosicionJaque(casillasCopia);

         // Busca la posición del propio rey tras el movimiento
            String colorPropio = ficha.substring(0, 1); // "w" o "b"
            String rey = colorPropio + "R";
            int reyFila = -1, reyCol = -1;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (casillasCopia[i][j].getText().equals(rey)) {
                        reyFila = i;
                        reyCol = j;
                    }
                }
            }

            // Si el propio rey está en jaque (casilla roja), el movimiento es inválido
            if (reyFila != -1 && reyCol != -1) {
                Color bg = casillasCopia[reyFila][reyCol].getBackground();
                if (bg != null && bg.equals(colorRojo)) {
                    return false;
                }
            }


        	


		return true;
	}

	

	public static String controlJugadasPorJaque(String mov, JButton[][] casillas,String ficha,String posicionInicial) {
		String jugadasTotales="";
	    // Divide el string por los espacios
	    String[] movimientos = mov.split(" ");
	    int filaInicial = Integer.parseInt(posicionInicial.substring(0, 1));
        int colInicial = Integer.parseInt(posicionInicial.substring(1, 2));
	    for (String movimiento : movimientos) {
	        if (movimiento.length() == 2) { // Asegúrarse de que el movimiento tiene dos caracteres
	            int fila = Integer.parseInt(movimiento.substring(0, 1));
	            int col = Integer.parseInt(movimiento.substring(1, 2));
	            if (crearTableroDePrueba(casillas,fila,col,ficha,filaInicial,colInicial)) {
	            	Piezas.resaltarCasilla2(fila, col, casillas);
	            	jugadasTotales += fila + "" + col + " ";;
	            }
	        }
	    }
		return jugadasTotales;
	}


	
	public static void detertarPosicionJaque(JButton[][] casillas) {
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
				case "R":
					piezaObj = new Rey();
					break;
				default:
					continue; 
				}

				String movimientos = piezaObj.calcularMovimientos(i + "" + j, casillas, pieza, false);
				String[] movimientosValidos = movimientos.split(" ");

				// Ver si alguno de los movimientos da jaque
				for (String movimiento : movimientosValidos) {
					if ( movimiento.equals(posicionReyNegro)) {
						// Jaque al negro
						int fila = Integer.parseInt(posicionReyNegro.substring(0, 1));
						int col = Integer.parseInt(posicionReyNegro.substring(1, 2));
						casillas[fila][col].setBackground(colorRojo);
						System.out.println("Jaque al Negro por " + pieza + " en " + i + "," + j);
						jaqueAlNegro = true;
					}
					if (movimiento.equals(posicionReyBlanco)) {
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

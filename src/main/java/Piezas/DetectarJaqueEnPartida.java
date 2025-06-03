package Piezas;




import java.awt.Color;


import javax.swing.JButton;

import Tablero.FuncionesVisualesTablero;
import Tablero.ObtenerMovimientosPiezas;
import UtilsComunes.Colores;

public class DetectarJaqueEnPartida {

	public static JButton[][] crearTableroDePrueba(JButton[][] casillas, int fila, int columna, String ficha,int filaInicial, int ColumnaACtual) {
		JButton[][] casillasCopia = new JButton[8][8];

		for (int i = 0; i < 8; i++) {
		    for (int j = 0; j < 8; j++) {
		        casillasCopia[i][j] = new JButton();
		        casillasCopia[i][j].setText(casillas[i][j].getText());
		        // Copia el color usando un nuevo objeto Color para evitar referencias a recursos UI
		    }
		}


            // Simula el movimiento
            casillasCopia[filaInicial][ColumnaACtual].setText("");
            casillasCopia[fila][columna].setText(ficha);
           return casillasCopia;
	}
	
	public static boolean mirarReyEnJaque(JButton[][] casillas, String color) {
        String colorPropio = color.substring(0, 1); // "w" o "b"
        String rey = colorPropio + "R";
        int reyFila = -1, reyCol = -1;

		 // Recalcula jaques y colorea
        detertarPosicionJaque(casillas);

     // Busca la posición del propio rey tras el movimiento
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (casillas[i][j].getText().equals(rey)) {
                    reyFila = i;
                    reyCol = j;
                }
            }
        }

        
        if (reyFila != -1 && reyCol != -1) {
            Color bg = casillas[reyFila][reyCol].getBackground();
            if (bg != null && bg.equals(Colores.JAQUE_ROJO)) {
                return false;
            }
        }
        FuncionesVisualesTablero.resetFullColores(casillas);
	return true;
	}

	

	public static String controlJugadasPorJaque(String mov, JButton[][] casillas,String ficha,String posicionInicial, boolean visual) {
		String jugadasTotales="";
	    // Divide el string por los espacios
	    String[] movimientos = mov.split(" ");
	    int filaInicial = Integer.parseInt(posicionInicial.substring(0, 1));
        int colInicial = Integer.parseInt(posicionInicial.substring(1, 2));

	    for (String movimiento : movimientos) {
	        if (movimiento.length() == 2) { // Asegúrarse de que el movimiento tiene dos caracteres
	            int fila = Integer.parseInt(movimiento.substring(0, 1));
	            int col = Integer.parseInt(movimiento.substring(1, 2));
	    	    JButton[][] casillasCopia = crearTableroDePrueba(casillas,fila,col,ficha,filaInicial,colInicial);
	            if (mirarReyEnJaque(casillasCopia,ficha)) {
	            	if (visual)
	            		FuncionesVisualesTablero.resaltarCasilla(fila, col, casillas);
	            	jugadasTotales += fila + "" + col + " ";
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
				if (pieza.equals("") || pieza.equals(null))
					continue; // Casilla vacía

				// Crear las piezas
				Piezas piezaObj = ObtenerMovimientosPiezas.identificarFuncionPieza(pieza);
				if (piezaObj==null)
					return;
				String movimientos = piezaObj.calcularMovimientos(i + "" + j, casillas, pieza,false);
				String[] movimientosValidos = movimientos.split(" ");

				// Ver si alguno de los movimientos da jaque
				for (String movimiento : movimientosValidos) {
					if (movimiento.equals(posicionReyNegro)) {
						// Jaque al negro
						int fila = Integer.parseInt(posicionReyNegro.substring(0, 1));
						int col = Integer.parseInt(posicionReyNegro.substring(1, 2));
						casillas[fila][col].setBackground(Colores.JAQUE_ROJO);
						jaqueAlNegro = true;
					}
					if (movimiento.equals(posicionReyBlanco)) {
						// Jaque al blanco
						int fila = Integer.parseInt(posicionReyBlanco.substring(0, 1));
						int col = Integer.parseInt(posicionReyBlanco.substring(1, 2));
						casillas[fila][col].setBackground(Colores.JAQUE_ROJO);
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

package Piezas;

import java.awt.Color;

import javax.swing.JButton;
public abstract class Piezas {
	protected static String jugadasTotales = "";
	protected static int filaActual = 0;
	protected static int columnaActual = 0;
	protected static boolean verMov;


	public abstract String calcularMovimientos(String posicion, JButton[][] casillas, String ficha, boolean verMovimientos);

	
	protected void verPeonesAlPaso(JButton[][] casillas, String ficha) {
//		for (int i =0;i<8;i++) {
//			for (int j = 0;j<8;j++) {
//				if (!ficha.contains("P") && casillas[i][j].getText().equals("wJa") || casillas[i][j].getText().equals("bJa")) {
//					casillas[i][j].setText("");
//				}
//			}
//		}
			
	}
	
    protected void inicializarPosicion(String posicion, boolean verMovimientos) {
        filaActual = Integer.parseInt(posicion.substring(0, 1));
        columnaActual = Integer.parseInt(posicion.substring(1, 2));
        jugadasTotales = "";
        verMov=verMovimientos;
    }
	protected static boolean mismoColor(JButton[][] casillas, int fila, int columna, String fichaColor) {
		String contenidoCasilla = casillas[fila][columna].getText();

		// Mira si las fichas son del mismo color
		boolean esMismoColor = (contenidoCasilla.contains("b") && fichaColor.contains("b"))
				|| (contenidoCasilla.contains("w") && fichaColor.contains("w"));

		if (esMismoColor) {
			return true;
		} else {
			return false;
		}
	}

	// Ver casillas para mover
	protected static void resaltarCasilla(int fila, int columna, JButton[][] casillas, String ficha) {
	    if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
	        // Aquí SIEMPRE false para validación
	        //if (JugadasEspecialRey.quitarJaque(casillas, fila, columna, ficha, false)) {
	            // Aquí sí puedes mostrar visualmente
	          
	            if (verMov) {
	                casillas[fila][columna].setBackground(new Color(144, 238, 144));
	                casillas[fila][columna].setEnabled(true);
	            }
	       // }
	    }
	}
	
	protected static void conseguirJugadasLogicas(int fila, int columna) {
		  jugadasTotales += fila + "" + columna + " ";
	}

	

	// Volver a como estaba
	public static void resetColores(JButton[][] casillas) {
	    Color rojo = new Color(255, 102, 102); // El rojo que usas para jaque

	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            // Si la casilla NO es roja, la resetea
	            if (!casillas[i][j].getBackground().equals(rojo)) {
	                if ((i + j) % 2 == 0) {
	                    casillas[i][j].setBackground(new Color(240, 217, 181)); // Beige cálido (marfil)
	                } else {
	                    casillas[i][j].setBackground(new Color(181, 136, 99));  // Marrón terracota
	                }
	            }
	        }
	    }
	}

}

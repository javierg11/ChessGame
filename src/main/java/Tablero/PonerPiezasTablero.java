package Tablero;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PonerPiezasTablero {
	public static void colocarPiezas(JButton[][] casillas, JButton casilla, int fila, int columna) {
		// Piezas de ajedrez diseñadas por Colin M.L. Burnett (cburnett)
		// Licencia: GNU General Public License v2 (GPLv2)
		// Más información en: licenses-third-party/GPL-2.0.txt

		// Acuerdate de quitar esta linea y añadir en el readme esto:
//		## Créditos
//
//		Este proyecto utiliza las piezas de ajedrez "cburnett", diseñadas por Colin M.L. Burnett.
//		- Licencia: GNU General Public License v2 (GPLv2)
//		- Más información: Ver archivo `licenses-third-party/GPL-2.0.txt`
//		Este proyecto utiliza piezas de ajedrez "cburnett" convertidas de SVG a PNG.

		// Configuración de piezas negras (fila 0)
		casillas[fila][columna] = casilla;
		if (fila == 0 && columna < 8) {
			switch (columna) {
			case 0:
			case 7: // Torres
				crearPieza(casilla, "bT");
				break;
			case 1:
			case 6: // Caballos
				crearPieza(casilla, "bC");
				break;
			case 2:
			case 5: // Alfiles
				crearPieza(casilla, "bA");
				break;
			case 3: // Reina
				crearPieza(casilla, "bD");
				break;
			case 4: // Rey
				crearPieza(casilla, "bR");
				break;
			}
		}

		// Configuración de piezas blancas (fila 7)
		if (fila == 7 && columna < 8) {
			switch (columna) {
			case 0:
			case 7: // Torres
				crearPieza(casilla, "wT");
				break;
			case 1:
			case 6: // Caballos
				crearPieza(casilla, "wC");
				break;
			case 2:
			case 5: // Alfiles
				crearPieza(casilla, "wA");
				break;
			case 3: // Reina
				crearPieza(casilla, "wD");
				break;
			case 4: // Rey
				crearPieza(casilla, "wR");
				break;
			}
		}

		// Peones (filas 1 para negros y 6 para blancos)
		if (fila == 1 && columna < 8) {
			crearPieza(casilla, "bP");
		}
		if (fila == 6 && columna < 8) {
			crearPieza(casilla, "wP");
		}
	}

	public static void crearPieza(JButton casilla, String pieza) {
		casilla.setText(pieza); // Texto lógico (no visible)
		casilla.setHorizontalTextPosition(JButton.CENTER); // Alinear texto
		casilla.setVerticalTextPosition(JButton.CENTER); // en el centro
		casilla.setForeground(new Color(0, 0, 0, 0)); // Texto transparente
		ImageIcon iconoOriginal = new ImageIcon(TableroAjedrez.class.getResource("/imagesPiezas/" + pieza + ".png"));
		Image imagen = iconoOriginal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		casilla.setIcon(new ImageIcon(imagen));
		casilla.revalidate(); // Actualizar la interfaz gráfica
	}
	
}

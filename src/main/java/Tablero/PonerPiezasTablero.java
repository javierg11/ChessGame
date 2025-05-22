package Tablero;


import java.awt.Image;
import ConstantesComunes.Colores;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PonerPiezasTablero {
	public static void colocarPiezas(JButton casilla, int fila, int columna) {
		String pieza="";
		if (fila==0)
			pieza="b";
		else if (fila==7)
			pieza="w";
		if (columna < 8) {
		    // Filas principales (0 y 7)
		    if (fila == 0 || fila == 7) {
		    	if (!pieza.equals("")) {
		            // Añade color según la fila
		            pieza = (fila == 0 ? "b" : "w");
		        }
		        switch (columna) {
		            case 0:
		            case 7: pieza += "T"; break; // Torres
		            case 1:
		            case 6: pieza += "C"; break; // Caballos
		            case 2:
		            case 5: pieza += "A"; break; // Alfiles
		            case 3: pieza += "D"; break; // Dama
		            case 4: pieza += "R"; break; // Rey
		        }
		        
		    }
		    // Peones
		    else if (fila == 1) {
		        pieza = "bP";
		    }
		    else if (fila == 6) {
		        pieza = "wP";
		    }

		    // Solo llamas a crearPieza si hay una pieza para colocar
		    if (!pieza.equals("")) {
		        crearPieza(casilla, pieza);
		    }
		}
	}

	public static void crearPieza(JButton casilla, String pieza) {
		// Piezas de ajedrez diseñadas por Colin M.L. Burnett (cburnett)
				// Licencia: GNU General Public License v2 (GPLv2)
				// Más información en: licenses-third-party/GPL-2.0.txt

//				## Créditos
		//
//				Este proyecto utiliza las piezas de ajedrez "cburnett", diseñadas por Colin M.L. Burnett.
//				- Licencia: GNU General Public License v2 (GPLv2)
//				- Más información: Ver archivo `licenses-third-party/GPL-2.0.txt`
//				Este proyecto utiliza piezas de ajedrez "cburnett" convertidas de SVG a PNG.
		
		casilla.setText(pieza); // Texto lógico (no visible)
		casilla.setHorizontalTextPosition(JButton.CENTER); // Alinear texto
		casilla.setVerticalTextPosition(JButton.CENTER); // en el centro
		casilla.setForeground(Colores.SIN_COLOR); // Texto transparente
		ImageIcon iconoOriginal = new ImageIcon(TableroAjedrez.class.getResource("/imagesPiezas/" + pieza + ".png"));
		Image imagen = iconoOriginal.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
		casilla.setIcon(new ImageIcon(imagen));
		casilla.revalidate(); // Actualizar la interfaz gráfica
	}
	
	


	
}

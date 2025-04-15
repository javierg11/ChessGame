package Tablero;

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
				    casilla.setBackground(new Color(240, 217, 181)); // Beige cálido (marfil)
				} else {
				    casilla.setBackground(new Color(181, 136, 99));  // Marrón terracota
				}



				colocarPiezas(casillas, fila, columna);

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
			return pieza.calcularMovimientos(posicion, casillas, ficha);
		} else
			return "No";
	}

	// Clase interna para manejar los clicks
	// Variables de clase para mantener el estado
	private static String posicionOrigen = null;
	private static String fichaSeleccionada = null;

	private static void moverPiezas(String origen, String destino, JButton[][] casillas, String ficha) {
		// Convertir coordenadas
		int filaOrigen = Integer.parseInt(origen.substring(0, 1));
		int colOrigen = Integer.parseInt(origen.substring(1, 2));
		int filaDestino = Integer.parseInt(destino.substring(0, 1));
		int colDestino = Integer.parseInt(destino.substring(1, 2));

		// Obtener movimientos permitidos
		String movimientos = identificarPiezaParaMover(ficha, origen, casillas);
		String[] movimientosValidos = movimientos.split(" ");

		// Verificar si el destino es válido
		for (String movimiento : movimientosValidos) {
			if (movimiento.equals(destino)) {
				// Realizar movimiento (poner exactamente la misma informacion que tenia el boton anterior en el nuevo)
            	ImageIcon iconoOriginal = new ImageIcon(TableroAjedrez.class.getResource("/"+ficha+".png"));
                Image imagen = iconoOriginal.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                casillas[filaDestino][colDestino].setIcon(new ImageIcon(imagen));
				casillas[filaDestino][colDestino].setText(ficha);
				casillas[filaDestino][colDestino].setHorizontalTextPosition(JButton.CENTER); // Alinear texto
				casillas[filaDestino][colDestino].setVerticalTextPosition(JButton.CENTER);   // en el centro
				casillas[filaDestino][colDestino].setForeground(new Color(0, 0, 0, 0));      // Texto transparente
            	
				
				//Vaciar la casilla anterior de la pieza
				casillas[filaOrigen][colOrigen].setText("");
				casillas[filaOrigen][colOrigen].setIcon(null);
			}
		}

	}

	private static class CasillaClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < casillasFilas; i++) {
				for (int j = 0; j < casillasColumnas; j++) {
					//En este if se crea el "puente" entre lo que el usuario ve y lo que se programa
					if (casillas[i][j] == e.getSource()) {
						String posicionActual = "" + i + j;

						if (posicionOrigen == null) {
							// Primera selección: guardar posición y ficha original
							posicionOrigen = posicionActual;
							fichaSeleccionada = casillas[i][j].getText();
							identificarPiezaParaMover(fichaSeleccionada, posicionOrigen, casillas);
						} else {
							// Segunda selección: intentar mover
							moverPiezas(posicionOrigen, posicionActual, casillas, fichaSeleccionada);
							Piezas.resetColores(casillas);
							// Resetear selección
							posicionOrigen = null;
							fichaSeleccionada = null;
						}
						return;
					}
				}
			}
		}
	}
	
	public static void colocarPiezas(JButton[][] casillas, int fila, int columna ) {
		// Piezas de ajedrez diseñadas por Colin M.L. Burnett (cburnett)
		// Licencia: GNU General Public License v2 (GPLv2)
		// Más información en: licenses-third-party/GPL-2.0.txt
		
		
		//Acuerdate de quitar esta linea y añadir en el readme esto:
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
	                case 0: case 7: // Torres
	                	crearPieza(casilla,"bT");
	                    break;
	                case 1: case 6: // Caballos
	                	crearPieza(casilla,"bC");
	                    break;
	                case 2: case 5: // Alfiles
	                	crearPieza(casilla,"bA");
	                    break;
	                case 3: // Reina
	                	crearPieza(casilla,"bD");
	                    break;
	                case 4: // Rey
	                	crearPieza(casilla,"bR");
	                    break;
	            }
	        }

	        // Configuración de piezas blancas (fila 7)
	        if (fila == 7 && columna < 8) {
	        	switch (columna) {
                case 0: case 7: // Torres
                	crearPieza(casilla,"wT");
                    break;
                case 1: case 6: // Caballos
                	crearPieza(casilla,"wC");
                    break;
                case 2: case 5: // Alfiles
                	crearPieza(casilla,"wA");
                    break;
                case 3: // Reina
                	crearPieza(casilla,"wD");
                    break;
                case 4: // Rey
                	crearPieza(casilla,"wR");
                    break;
            }
	        }

	        // Peones (filas 1 para negros y 6 para blancos)
	        if (fila == 1 && columna < 8) {
	        	crearPieza(casilla,"bP");
	        }
	        if (fila == 6 && columna < 8) {
	        	crearPieza(casilla,"wP");
	        }
	}
	
	public static void crearPieza(JButton casilla, String pieza) {
		casilla.setText(pieza); // Texto lógico (no visible)
    	casilla.setHorizontalTextPosition(JButton.CENTER); // Alinear texto
    	casilla.setVerticalTextPosition(JButton.CENTER);   // en el centro
    	casilla.setForeground(new Color(0, 0, 0, 0));      // Texto transparente
    	ImageIcon iconoOriginal = new ImageIcon(TableroAjedrez.class.getResource("/"+pieza+".png"));
        Image imagen = iconoOriginal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        casilla.setIcon(new ImageIcon(imagen));
        casilla.revalidate(); // Actualizar la interfaz gráfica
	}
	

}

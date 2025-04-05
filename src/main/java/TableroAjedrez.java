import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableroAjedrez {
	public static void main(String[] args) {
		JFrame tablero = new JFrame("Tablero de Ajedrez Interactivo");
		tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tablero.setLayout(new GridLayout(8, 8));

		JButton[][] casillas = new JButton[8][8];

		for (int fila = 0; fila < 8; fila++) {
			for (int columna = 0; columna < 8; columna++) {
				JButton casilla = new JButton();
				casilla.setOpaque(true);
				casilla.setBorderPainted(false); 

				if ((fila + columna) % 2 == 0) {
					casilla.setBackground(Color.WHITE);
				} else {
					casilla.setBackground(Color.BLACK);
				}

				casillas[fila][columna] = casilla;

				casilla.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for (int i = 0; i < 8; i++) {
							for (int j = 0;j < 8; j++) {
								if (casillas[i][j] == e.getSource()) {
									System.out.println("Casilla clickeada: [" + i + "][" + j + "]");
									break;
								}
							}
						}
					}
				});

				tablero.add(casilla);
			}
		}

		tablero.setSize(500, 500);
		tablero.setVisible(true);
	}
}

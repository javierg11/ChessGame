package Piezas;



import java.awt.Color;
import java.util.HashMap;

import javax.swing.JButton;

public class JugadaEspecialPeon {
	public static String coronarPeon(int fila, String ficha) {
		if (fila==0 && ficha.equals("wP")) {
			return "wD";
		}
		else if (fila==7 && ficha.equals("bP")){
			return "bD";
		}
		else
			return ficha;
		
		
		
		}
	
	
	public static void comerAlPaso(int filaOrigen, int filaDestino, String ficha, JButton [][] casillas, int columna,HashMap<Integer, String> jugadas) {
		
		
        Integer claveMasNueva = jugadas.size();
        String valorMasNuevo =jugadas.get(claveMasNueva);

        // Regex para saber si blancas o negras han hecho un movimiento doble con el peon
        String regexBlancas = "wP-6\\d-4\\d";
        String regexNegras = "bP-1\\d-3\\d";

        //Este comprueba si el regex es correcto, y si hay un peon de color contrario al aldo del peon (esto se hace para ver si es posible ejecutar 
        //el comer al paso)
        
        boolean puedeComerAlPaso = false;

        if (valorMasNuevo.matches(regexBlancas)) {
            // Peón blanco: busca peón negro a izquierda o derecha
            boolean izquierda = (columna - 1 >= 0) && casillas[filaDestino][columna - 1].getText().equals("bP");
            boolean derecha   = (columna + 1 < 8) && casillas[filaDestino][columna + 1].getText().equals("bP");
            puedeComerAlPaso = izquierda || derecha;
        } else if (valorMasNuevo.matches(regexNegras)) {
            // Peón negro: busca peón blanco a derecha o izquierda
            boolean derecha   = (columna + 1 < 8) && casillas[filaDestino][columna + 1].getText().equals("wP");
            boolean izquierda = (columna - 1 >= 0) && casillas[filaDestino][columna - 1].getText().equals("wP");
            puedeComerAlPaso = derecha || izquierda;
        }

        if (puedeComerAlPaso) {
            //Este if sirve para saber a qué color se le hace el comer al paso, creando un peon temporal que si no se come en ese turno desaparece
            if (ficha.equals("wP")) {
                if (filaOrigen - 1 >= 0) { // Control de límites
                    casillas[filaOrigen - 1][columna].setText("wJa");
                    casillas[filaOrigen - 1][columna].setHorizontalTextPosition(JButton.CENTER);
                    casillas[filaOrigen - 1][columna].setVerticalTextPosition(JButton.CENTER);
                    casillas[filaOrigen - 1][columna].setForeground(new Color(0, 0, 0, 0));
                }
            } else {
                if (filaOrigen + 1 < 8) { // Control de límites
                    casillas[filaOrigen + 1][columna].setText("bJa");
                    casillas[filaOrigen + 1][columna].setHorizontalTextPosition(JButton.CENTER);
                    casillas[filaOrigen + 1][columna].setVerticalTextPosition(JButton.CENTER);
                    casillas[filaOrigen + 1][columna].setForeground(new Color(0, 0, 0, 0));
                }
            }
        } else {
            // Si no se puede comer al paso, limpia los peones temporales
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (casillas[i][j].getText().equals("wJa") || casillas[i][j].getText().equals("bJa"))
                        casillas[i][j].setText("");
                }
            }
        }

	}
}

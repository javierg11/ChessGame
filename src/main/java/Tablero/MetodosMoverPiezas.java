package Tablero;

import java.awt.Color;


import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import Piezas.*;

public class MetodosMoverPiezas {
	public static String identificarPiezaParaMover(String ficha, String posicion, JButton[][] casillas) {
		Piezas pieza=null;
		String tipo = ficha.substring(1, 2); // "D", "T", "A", "C", "P", "R"

		switch (tipo) {
		case "D":
			pieza = new Dama();
			break;
		case "T":
			pieza = new Torre();
			break;
		case "A":
			pieza = new Alfil();
			break;
		case "C":
			pieza = new Caballo();
			break;
		case "P":
			pieza = new Peon();
			break;
		case "R":
			pieza = new Rey();
			break;
		}

		return pieza.calcularMovimientos(posicion, casillas, ficha, true);
			
	}


	public static boolean moverPiezas(String origen, String destino, JButton[][] casillas, String ficha) {
	    int filaOrigen = Integer.parseInt(origen.substring(0, 1));
	    int colOrigen = Integer.parseInt(origen.substring(1, 2));
	    int filaDestino = Integer.parseInt(destino.substring(0, 1));
	    int colDestino = Integer.parseInt(destino.substring(1, 2));
	    
	    String movimientos = identificarPiezaParaMover(ficha, origen, casillas);
	    String[] movimientosValidos = movimientos.split(" ");

	    for (String movimiento : movimientosValidos) {
	        if (movimiento.equals(destino)) {
	        	
			    //Este metodo sirve para comprobar si un peon ha llegado a su casilla de coronacion
	        	//Si ha llegado cornona si no, no hace nada
	        	ficha = JugadaEspecialPeon.coronarPeon(filaDestino,ficha);
	        	

	            ImageIcon iconoOriginal = new ImageIcon(
	                TableroAjedrez.class.getResource("/imagesPiezas/" + ficha + ".png"));
	            Image imagen = iconoOriginal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	            
	            //Esta parte solo es para ver si se puede comer al paso con el peon (porque tiene que actualizar otras casillas)
	            if (casillas[filaDestino][colDestino].getText().equals("wJa") && casillas[filaOrigen][colOrigen].getText().equals("wP")) {
	            	casillas[filaDestino-1][colDestino].setText("");
	            	casillas[filaDestino-1][colDestino].setIcon(null);
	            }else if (casillas[filaDestino][colDestino].getText().equals("bJa") && casillas[filaOrigen][colOrigen].getText().equals("bP")) {
	            	casillas[filaDestino+1][colDestino].setText("");
	            	casillas[filaDestino+1][colDestino].setIcon(null);
	            }
	            //Aqui ya sigue con normalidad
	            
	            //Esto es para el enroque
	            // Detectar si es un movimiento de rey de enroque (dos columnas de diferencia)
	            mirarMoverEnroque(casillas,filaOrigen, colDestino, colOrigen);
	            	            
	            casillas[filaDestino][colDestino].setIcon(new ImageIcon(imagen));
	            casillas[filaDestino][colDestino].setText(ficha);
	            casillas[filaDestino][colDestino].setHorizontalTextPosition(JButton.CENTER);
	            casillas[filaDestino][colDestino].setVerticalTextPosition(JButton.CENTER);
	            casillas[filaDestino][colDestino].setForeground(new Color(0, 0, 0, 0));

	            casillas[filaOrigen][colOrigen].setText("");
	            casillas[filaOrigen][colOrigen].setIcon(null);

			    CalculosEnPartida.guardarMovimientos(origen,destino,ficha);
	        		
			    JugadaEspecialPeon.comerAlPaso(filaOrigen,filaDestino,ficha,casillas,colDestino,CalculosEnPartida.getJugadas());
			    
			    JugadasEspecialRey.darJaque(casillas);
	            return true; 
	        }
	    }

	    return false; 
	}
	
	
	
	
	
	private static void mirarMoverEnroque(JButton[][] casillas, int filaOrigen, int colDestino, int colOrigen) {
		if (casillas[filaOrigen][colOrigen].getText().contains("R") && Math.abs(colOrigen - colDestino) == 2) {
            String color = casillas[filaOrigen][colOrigen].getText().substring(0, 1); // "w" o "b"
            int fila = filaOrigen; // Para blancas y negras es la misma fila de origen del rey

            // Determinar si es enroque corto o largo
            boolean enroqueCorto = (colDestino > colOrigen);

            // Coordenadas de las torres y destino según el tipo de enroque
            int torreColOrigen = enroqueCorto ? 7 : 0;
            int torreColDestino = enroqueCorto ? colDestino - 1 : colDestino + 1;

            // Cargar el icono de la torre correspondiente
            String iconoTorre = "/imagesPiezas/" + color + "T.png";
            ImageIcon iconoOriginal = new ImageIcon(TableroAjedrez.class.getResource(iconoTorre));
            Image imagenTorre = iconoOriginal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

            // Mover la torre
            casillas[fila][torreColDestino].setIcon(new ImageIcon(imagenTorre));
            casillas[fila][torreColDestino].setText(color + "T");
            casillas[fila][torreColDestino].setHorizontalTextPosition(JButton.CENTER);
            casillas[fila][torreColDestino].setVerticalTextPosition(JButton.CENTER);
            casillas[fila][torreColDestino].setForeground(new Color(0, 0, 0, 0));

            // Limpiar la casilla original de la torre
            casillas[fila][torreColOrigen].setText("");
            casillas[fila][torreColOrigen].setIcon(null);
        }
	}
	
	
	
	

}

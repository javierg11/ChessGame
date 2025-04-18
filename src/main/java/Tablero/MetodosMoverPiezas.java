package Tablero;

import java.awt.Color;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import Piezas.*;

public class MetodosMoverPiezas {
	public static String identificarPiezaParaMover(String ficha, String posicion, JButton[][] casillas) {
		Piezas pieza;
		if (ficha.contains("T")) {
			pieza = new Torre();
			return pieza.calcularMovimientos(posicion, casillas, ficha); 
		}else if (ficha.contains("P")) {
			pieza = new Peon();
			return pieza.calcularMovimientos(posicion, casillas, ficha);
		}else if (ficha.contains("A")) {
			pieza = new Alfil();
			return pieza.calcularMovimientos(posicion, casillas, ficha);
		}else if (ficha.contains("D")) {
			pieza = new Dama();
			return pieza.calcularMovimientos(posicion, casillas, ficha);
		}else if (ficha.contains("C")) {
			pieza = new Caballo();
			return pieza.calcularMovimientos(posicion, casillas, ficha);
		}else if (ficha.contains("R")) {
			pieza = new Rey();
			return pieza.calcularMovimientos(posicion, casillas, ficha);
		} 
		else
			return "No";
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
	        	
	        	
	        	String jugadaBonita = ficha.substring(1, 2);
	        	if (!casillas[filaDestino][colDestino].getText().equals("")) {
	                jugadaBonita += "x";
	            }
	            char columna = (char) ('a' + colDestino);
	            int fila = 8-filaDestino;
	            jugadaBonita += "" + columna + fila;
	            System.out.println(jugadaBonita); 
	            
	            
	            ImageIcon iconoOriginal = new ImageIcon(
	                TableroAjedrez.class.getResource("/imagesPiezas/" + ficha + ".png"));
	            Image imagen = iconoOriginal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	            casillas[filaDestino][colDestino].setIcon(new ImageIcon(imagen));
	            casillas[filaDestino][colDestino].setText(ficha);
	            casillas[filaDestino][colDestino].setHorizontalTextPosition(JButton.CENTER);
	            casillas[filaDestino][colDestino].setVerticalTextPosition(JButton.CENTER);
	            casillas[filaDestino][colDestino].setForeground(new Color(0, 0, 0, 0));

	            casillas[filaOrigen][colOrigen].setText("");
	            casillas[filaOrigen][colOrigen].setIcon(null);
	            //Esto llama a una funcion que lleva la cuenta del numero de movimientos de la partida
			    CalculosEnPartida.sumarMovimientos();
			    CalculosEnPartida.guardarMovimientos(origen,destino,ficha);
	            return true; 
	        }
	    }

	    return false; 
	}
	
	
	
	public static void intentarMover(String origenPos, String destinoPos, JButton origenBtn, JButton destinoBtn, JButton [][] casillas,
			String fichaSeleccionada) {
	    boolean movimientoExitoso = MetodosMoverPiezas.moverPiezas(origenPos, destinoPos, casillas, fichaSeleccionada);

	    if (movimientoExitoso) {
	        // Solo si el movimiento fue válido, actualizamos la info lógica
	        destinoBtn.setActionCommand(origenBtn.getActionCommand());
	        origenBtn.setActionCommand("");
	    } else {
	        // Si el movimiento es inválido, dejamos la pieza donde estaba
	        origenBtn.setIcon(origenBtn.getIcon());
	        origenBtn.setActionCommand(fichaSeleccionada);
	    }
	}
	
	
	
	

}

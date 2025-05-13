package Tablero;

import javax.swing.JButton;

import Piezas.DetectarJaqueEnPartida;
import Piezas.Piezas;

public class MovimientosPosibles {
	public static synchronized boolean tenerMovimientosPosibles(JButton[][] casillas, boolean color) {
	    // color: true = blancas, false = negras
	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            String ficha = casillas[i][j].getText();
	            if (!ficha.isEmpty()) {
	                // Comprobar si la ficha es del color que le toca mover
	                if ((color && ficha.contains("w")) || (!color && ficha.contains("b"))) {
	                    String posicion = "" + i + j;
	                    Piezas pieza = MetodosMoverPiezas.identificarFuncionPieza(ficha);
	                    String movimientosPosibles = pieza.calcularMovimientos(posicion, casillas, ficha,false);
	                    String movimientosLegales = DetectarJaqueEnPartida.controlJugadasPorJaque(
	                        movimientosPosibles, casillas, ficha, posicion, true
	                    );
	                    if (movimientosLegales.length() > 0) {
	                    	movimientosLegales="";
	                        // Si hay al menos un movimiento legal, devuelve true
	                        return true;
	                    }
	                }
	            }
	        }
	    }
	    
	    // Si ninguna pieza tiene movimientos legales, devuelve false
	    return false;
	}

}

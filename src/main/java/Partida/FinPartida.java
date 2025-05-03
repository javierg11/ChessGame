package Partida;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import Piezas.DetectarJaqueEnPartida;
import Tablero.MovimientosPosibles;

public class FinPartida {
    static String texto="";

	public static void IdentificarFinPartida(JButton[][] casillas) {
		if (!MovimientosPosibles.tenerMovimientosPosibles(casillas, CalculosEnPartida.colorAMover())) {
	        // Crea un Timer que espera 1000 ms (1 segundo) antes de ejecutar la acción
	    	
	    	if (DetectarJaqueEnPartida.mirarReyEnJaque(casillas,(CalculosEnPartida.colorAMover() ? "blanco" : "negro"))) {
	    		texto=    "<html><b>¡Victoria!</b><br>El jugador " + (CalculosEnPartida.colorAMover() ? "blanco" : "negro") + " no tiene movimientos posibles.<br><i>Jaque mate.</i></html>";
	    	}
	    	else {
	    		texto=    "<html><b>¡Tablas!</b><br>El jugador " + (CalculosEnPartida.colorAMover() ? "blanco" : "negro") + " no tiene movimientos posibles.<br><i>Es tablas.</i></html>";
	    	}
	    		
	    		
	        Timer timer = new Timer(10, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Muestra un cuadro de diálogo informando el fin de la partida
	                JOptionPane.showMessageDialog(
	                    null, 
	                    texto,
	                    "Fin de la partida",
	                    JOptionPane.INFORMATION_MESSAGE
	                );
	                // Detiene el Timer para que la acción solo se ejecute una vez
	                ((Timer) e.getSource()).stop();
	            }
	        });
	        // Inicia el Timer
	        timer.start();
	    }
	}
	
	
	
}

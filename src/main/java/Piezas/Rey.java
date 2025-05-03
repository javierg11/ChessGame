package Piezas;

import javax.swing.JButton;

import Partida.CalculosEnPartida;

public class Rey extends Piezas {

	String pos="";
	boolean movimientoEspecial=false;
	@Override
	public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha, boolean movEspecial) {
		inicializarPosicion(posicion);
		pos=conseguirPosicion(posicion);
		movimientoEspecial=movEspecial;
	    movimientosRey(casillas, ficha);
	    
		return jugadasTotales + "\n";
	}

	private void movimientosRey(JButton[][] casillas, String ficha) {
		for (int i = filaActual + 1; i > filaActual - 2; i--) {
			for (int a = columnaActual + 1; a > columnaActual - 2; a--) {
				if (i >= 0 && i < 8 && a >= 0 && a < 8) {
					if (casillas[i][a].getText().isEmpty() || !verPeonesAlPaso(casillas,filaActual,columnaActual)) {
						conseguirJugadasLogicas(i, a);
					} else {
						if (!mismoColor(casillas, i, a, ficha)) {
							conseguirJugadasLogicas(i, a);
							
						}

					}
				}
			}
			//VerconseguirJugadasLogicas();
		}
		 
	    // Añade enroque si es posible
		String a = VerconseguirJugadasLogicas(); // Jugadas normales
		String h = enroqueRey.enroque(ficha, CalculosEnPartida.getJugadas(), casillas, pos, movimientoEspecial);
		jugadasTotales = ""; // Vacía el string de jugadas legales
		// Añade jugadas normales
		String[] jugadas = a.split(" ");
		for (String jugada : jugadas) {
		    if (jugada.length() == 2) {
		        int filaA = Integer.parseInt(jugada.substring(0, 1));
		        int colA = Integer.parseInt(jugada.substring(1, 2));
		        conseguirJugadasLogicas(filaA, colA);
		    }
		}

		// Añade enroque SOLO si es válido
		if (!h.isEmpty()) {
		    System.out.println("Intentando enroque para " + ficha + " resultado: " + h);
		    int filaDestino = Integer.parseInt(h.substring(0, 1));
		    int colDestino = Integer.parseInt(h.substring(1, 2));
		    conseguirJugadasLogicas(filaDestino, colDestino);
		}



	    // Solo una vez, al final
	    //VerconseguirJugadasLogicas();

}
}

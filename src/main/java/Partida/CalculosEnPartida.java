package Partida;

import java.util.HashMap;



public class CalculosEnPartida {
	

	

	private static int jugadasTotales = 0;
	


	private static HashMap<Integer, String> jugadas = new HashMap<Integer, String>();

	public static boolean colorAMover() {
	      
		if (jugadasTotales % 2 == 0) // Mueven blacas
			return true;
		else // Mueven negras
			return false;
	}
	
	
	public static void sumarMovimientos() {
		jugadasTotales++;
	}
	
	public static void setJugadasTotales(int jugadasTotales) {
		CalculosEnPartida.jugadasTotales = jugadasTotales;
	}
	public static int getJugadasTotales() {
		return jugadasTotales;
	}

	public static HashMap<Integer, String> getJugadas() {
		return jugadas;
	}
	
	public static void setJugadas(HashMap<Integer, String> jugadas) {
		CalculosEnPartida.jugadas = jugadas;
	}
	public static void guardarMovimientos(String posInicial, String posDestino, String ficha) {
		sumarMovimientos();
		String jugada = ficha + "-" + posInicial + "-" + posDestino; // Con un .split ya tengo un array del nº de
																		// movimiento, origen, final y ficha
		jugadas.put(jugadasTotales, jugada);
		System.out.println(jugadas);
	}

	

	

}

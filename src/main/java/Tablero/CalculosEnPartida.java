package Tablero;
import java.util.HashMap; 
public class CalculosEnPartida {
	private static int jugadasTotales=0;
	public static HashMap<Integer, String> jugadas = new HashMap<Integer, String>();
	
	public static boolean colorAMover() {
		
		if (jugadasTotales%2==0) //Mueven blacas
			return true;
		else //Mueven negras
			return false;
	}
	public static void sumarMovimientos() {
		jugadasTotales++;
	}
	
	public static void guardarMovimientos(String posInicial, String posDestino, String ficha) {
		String jugada=ficha+"-"+posInicial+"-"+posDestino;
		jugadas.put(jugadasTotales,jugada);
		System.out.println(jugadas);
	}
}

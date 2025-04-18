package Tablero;

public class ContarMovimientos {
	static int movimientosTotales=0;

	
	public static boolean colorAMover() {
		if (movimientosTotales%2==0)
			return true;
		else
			return false;
	}
	public static void sumarMovimientos() {
		movimientosTotales++;
	}
}

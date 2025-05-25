package ProblemasAjedrez;

import java.util.List;


public class ProblemaConseguido {
	public static boolean problemaLogrado(String movimiento, String pieza, int nivelActual) {
	    LeerJSON lector = new LeerJSON();
	    List<SolucionProblema> problemas = lector.leerSolucionProblema("/problemas/solucionProblema.json");
	    for (SolucionProblema p : problemas) {
	        if (p.getMovimiento().equals(movimiento) && p.getPieza().equals(pieza) && p.getNivel()==nivelActual) {
	            return true;
	        }
	    }
	    return false;
	}

}

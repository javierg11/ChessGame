package GuardarPartida;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;

import Partida.PosicionRepetida;
import Tablero.MetodosMoverPiezas;

public class FuncionesCompartidas {
	public static void mostrarTableroHasta(HashMap<Integer, String> jugadas, JButton[][] casillas, int hasta) {
	    // 1. Ordena las claves
	    List<Integer> clavesOrdenadas = new ArrayList<>(jugadas.keySet());
	    Collections.sort(clavesOrdenadas);

	    // 2. Reinicia el tablero
	    JButton[][] tableroInicial = PosicionRepetida.crearTableroInicial();
	    for (int fila = 0; fila < 8; fila++) {
	        for (int columna = 0; columna < 8; columna++) {
	            casillas[fila][columna].setText(tableroInicial[fila][columna].getText());
	            casillas[fila][columna].setIcon(tableroInicial[fila][columna].getIcon());
	        }
	    }

	    // 3. Reaplica jugadas desde la primera hasta la número 'hasta' (inclusive)
	    for (int clave : clavesOrdenadas) {
	        if (clave > hasta) break;
	        String movimiento = jugadas.get(clave);
	        if (movimiento == null) continue;
	        String[] partes = movimiento.split("-");
	        if (partes.length != 3) continue;
	        String ficha = partes[0];
	        String origen = partes[1];
	        String destino = partes[2];
	        MetodosMoverPiezas.moverPiezas(origen, destino, casillas, ficha,false);
	    }
	}
}

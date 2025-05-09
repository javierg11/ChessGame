package Partida;

import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;

import Piezas.DetectarJaqueEnPartida;
import Tablero.MovimientosPosibles;

public class ConvertirAJugadasAceptables implements Runnable{
	
	private String ficha;
    private String fichaOriginal;
    private JButton[][] casillas;
    private String destino;
    private String origen;
    private boolean hayPieza;
	public static HashMap<Integer, String> jugadasBonitas = new HashMap<Integer, String>();

	
	public static void actualizarJugadasEnTablero(JLabel labelDerechaArriba) {
	    int jugadaActual = CalculosEnPartida.getJugadasTotales(); // Empieza en 1
	    String jugada = jugadasBonitas.get(jugadaActual);

	    String texto = labelDerechaArriba.getText();

	    // Encuentra dónde termina la tabla para insertar la nueva fila antes de </table>
	    int idx = texto.lastIndexOf("</table>");
	    if (idx == -1) idx = texto.length() - 7; 

	    // Calcula el número de movimiento
	    int numMov = (jugadaActual + 1) / 2;

	    // Prepara la nueva fila (centrada)
	    String nuevaFila = "";
	    if (jugadaActual % 2 != 0) { // Turno de blancas (impar)
	        // Nueva fila con blancas, negras vacío
	        nuevaFila = "<tr>" +
	            "<td style='text-align:center;'>" + numMov + "</td>" +
	            "<td style='text-align:center;'>" + jugada + "</td>" +
	            "<td style='text-align:center;'></td></tr>";
	        // Inserta la fila antes de </table>
	        texto = texto.substring(0, idx) + nuevaFila + texto.substring(idx);
	    } else { // Turno de negras (par)
	        // Busca la última fila con <td style='text-align:center;'></td></tr> y reemplázala
	        int lastEmptyCell = texto.lastIndexOf("<td style='text-align:center;'></td></tr>");
	        if (lastEmptyCell != -1) {
	            texto = texto.substring(0, lastEmptyCell) +
	                "<td style='text-align:center;'>" + jugada + "</td></tr>" +
	                texto.substring(lastEmptyCell + "<td style='text-align:center;'></td></tr>".length());
	        }
	    }

	    labelDerechaArriba.setText(texto);
	}









	// Constructor para recibir los parámetros
    public ConvertirAJugadasAceptables(String ficha, String fichaOriginal, JButton[][] casillas, String destino, String origen, boolean hayPieza) {
        this.ficha = ficha;
        this.fichaOriginal = fichaOriginal;
        this.casillas = casillas;
        this.destino = destino;
        this.origen = origen;
        this.hayPieza=hayPieza;
    }
	

	private static void pasarJugadasParaGuardar(String ficha, String fichaOriginal, JButton[][] casillas, String destino, String origen, boolean hayPieza) {
		// Detecta si es JAque para poner un "+" en la lista de movimientos
		char colorContrario = ficha.charAt(0) == 'w' ? 'b' : 'w';
		String fichaContraria = "" + colorContrario; // Solo interesa el rey
		String destinoBonito="";
		destinoBonito=destino;
		
		if (!tenerJugadaEnroque(ficha, origen, destino).isEmpty())
			destinoBonito = tenerJugadaEnroque(ficha, origen, destino);
		if (fichaOriginal.contains("P") && !ficha.contains("P")) {
			destinoBonito = destino + "=" + ficha.substring(1, 2);
			ficha = fichaOriginal;
		}
		if (!DetectarJaqueEnPartida.mirarReyEnJaque(casillas, fichaContraria)) {
			destinoBonito += "+";
		}
		if (!MovimientosPosibles.tenerMovimientosPosibles(casillas, !CalculosEnPartida.colorAMover())) {
			destinoBonito += "+";
		}
		generarMovimientosBonitosDesdeJugadas(destinoBonito, ficha,hayPieza, origen);
	}
	
	private static void generarMovimientosBonitosDesdeJugadas(String destino, String ficha,boolean hayPieza, String origen) {
		String jugada = "";

		// Convertir destino a notación algebraica (soporta coronación y jaque/mate)
		String destinoAlgebraico = convertirACoordenadaAlgebraica(destino);
		
		if (ficha.substring(1, 2).equals("P")) {
			jugada = destinoAlgebraico;
		}else if (destino.contains("O-O") || destino.contains("O-O-O")) {
			jugada=destino;
		}else if(destino.contains("=")) {
			jugada = destino;
		}
		else {
			jugada = ficha.substring(1, 2) + destinoAlgebraico;
		}
		if(hayPieza) {
			jugada =ficha.substring(1, 2)+"x"+ destinoAlgebraico;
		}
		if(hayPieza && ficha.substring(1, 2).equals("P")) {
			int col = origen.charAt(1) - '0'; // 0 a 7 (a-h)

			char columnaLetra = (char) ('a' + col);
			System.out.println(destinoAlgebraico);
			jugada =columnaLetra+"x"+ destinoAlgebraico;
		}
		jugadasBonitas.put(CalculosEnPartida.getJugadasTotales(), jugada);
		System.out.println(jugadasBonitas);
	}

	// Convierte "44" a "d4" y "44+" a "d4+"
	private static String convertirACoordenadaAlgebraica(String pos) {
		String coronacion = "";
		String jaque = "";

		// Buscar coronación (ejemplo: "=D", "=C", "=T", "=A")
		int igualIdx = pos.indexOf('=');
		if (igualIdx != -1 && pos.length() > igualIdx + 1) {
			coronacion = pos.substring(igualIdx); // "=D"
			pos = pos.substring(0, igualIdx); // quitar coronación para buscar jaque
		}

		// Buscar jaque o mate (++ o +)
		if (pos.endsWith("++")) {
			jaque = "++";
			pos = pos.substring(0, pos.length() - 2);
		} else if (pos.endsWith("+")) {
			jaque = "+";
			pos = pos.substring(0, pos.length() - 1);
		}


		// Ahora pos debería ser la casilla (dos dígitos)
		if (pos.length() != 2)
			return pos + coronacion + jaque; // Si no es válido, devuelve igual

		int fila = pos.charAt(0) - '0'; // 0 a 7 (0 arriba, 7 abajo)
		int col = pos.charAt(1) - '0'; // 0 a 7 (a-h)

		char columnaLetra = (char) ('a' + col);
		int filaAlgebraica = 8 - fila;

		return "" + columnaLetra + filaAlgebraica + coronacion + jaque;
	}
	
	
	private static String tenerJugadaEnroque(String ficha, String origen, String destino) {
		// Solo interesa si es el rey
		if (!ficha.substring(1, 2).equals("R"))
			return "";

		else {
			// Corto: e1-g1  e8-g8 
			if ((origen.equals("74") && destino.startsWith("76")) || (origen.equals("04") && destino.startsWith("06")))
				return "O-O";
			// Largo: e1-c1  e8-c8 
			if ((origen.equals("74") && destino.startsWith("72")) || (origen.equals("04") && destino.startsWith("02")))
				return "O-O-O";
		}
		// No es enroque
		return "";
	}

	@Override
	public void run() {
		
		pasarJugadasParaGuardar(ficha, fichaOriginal, casillas, destino, origen, hayPieza);
	}
}

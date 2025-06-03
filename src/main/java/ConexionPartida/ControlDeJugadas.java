package ConexionPartida;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.net.SocketException;

import Partida.FinPartida;
import Tablero.FuncionesVisualesTablero;
import Tablero.MetodosMoverPiezas;

public class ControlDeJugadas {
	boolean nezuko=true;
	public void hacerJugadas(BufferedWriter out){
		try {
			
		out.write(MetodosMoverPiezas.datosDeMovimientos.getOrigen() + "\n");

		out.write(MetodosMoverPiezas.datosDeMovimientos.getDestino() + "\n");

		out.write(MetodosMoverPiezas.datosDeMovimientos.getFicha() + "\n");

		out.write(MetodosMoverPiezas.datosDeMovimientos.getMovimientos() + "\n");


		// out.write(MetodosMoverPiezas.sensorDeTurnosDosJugadores + "\n");
		out.flush();
	}catch(SocketException  a) {
		FinPartida.mensajeTerminarPartida("El oponente se ha retirado",Movimientos.getCasillas(),false, false) ;
	} catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	public void escucharJugadas(BufferedReader in){
		try {
			
		FuncionesVisualesTablero.setVerCasillas(false);
		String origen = in.readLine();
		if (origen.equals("FIN")) {
		    nezuko=false;
		    return;
		}

		String destino = in.readLine();

		String ficha = in.readLine();

		String movimientos = in.readLine();		
		MetodosMoverPiezas.moverPiezas(origen, destino, Movimientos.getCasillas(), ficha,
				movimientos,true,true,false);
		FuncionesVisualesTablero.resetColores(Movimientos.getCasillas());
		FuncionesVisualesTablero.setVerCasillas(true);

		}catch(SocketException  a) {
			if (nezuko)
				FinPartida.mensajeTerminarPartida("El oponente se ha retirado",Movimientos.getCasillas(),false, false);
			else
				nezuko=true;
		} catch(Exception e) {
			if (nezuko)
			e.printStackTrace();
		}
	}
}
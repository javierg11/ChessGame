package ConexionPartida;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.net.SocketException;

import Partida.FinPartida;
import Tablero.FuncionesVisualesTablero;
import Tablero.MetodosMoverPiezas;

public class ControlDeJugadas {
	public void hacerJugadas(BufferedWriter out){
		try {
		System.out.println(
				"Enviando origen: " + MetodosMoverPiezas.datosDeMovimientos.getOrigen());
		out.write(MetodosMoverPiezas.datosDeMovimientos.getOrigen() + "\n");

		System.out.println(
				"Enviando destino: " + MetodosMoverPiezas.datosDeMovimientos.getDestino());
		out.write(MetodosMoverPiezas.datosDeMovimientos.getDestino() + "\n");

		System.out.println(
				"Enviando ficha: " + MetodosMoverPiezas.datosDeMovimientos.getFicha());
		out.write(MetodosMoverPiezas.datosDeMovimientos.getFicha() + "\n");

		System.out.println("Enviando movimientos: "
				+ MetodosMoverPiezas.datosDeMovimientos.getMovimientos());
		out.write(MetodosMoverPiezas.datosDeMovimientos.getMovimientos() + "\n");


		// out.write(MetodosMoverPiezas.sensorDeTurnosDosJugadores + "\n");
		out.flush();
	}catch(SocketException  a) {
		FinPartida.mensajeTerminarPartida("El oponente se ha retirado",Movimientos.getCasillas(),false) ;
	} catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	public void escucharJugadas(BufferedReader in){
		try {
		FuncionesVisualesTablero.setVerCasillas(false);
		String origen = in.readLine();

		String destino = in.readLine();

		String ficha = in.readLine();

		String movimientos = in.readLine();
//                 String a = inFinal.readLine();
//                 MetodosMoverPiezas.sensorDeTurnosDosJugadores = Boolean.parseBoolean(a);
		MetodosMoverPiezas.moverPiezas(origen, destino, Movimientos.getCasillas(), ficha,
				movimientos,true,true,false);
		FuncionesVisualesTablero.resetColores(Movimientos.getCasillas());
		FuncionesVisualesTablero.setVerCasillas(true);

		}catch(SocketException  a) {
			FinPartida.mensajeTerminarPartida("El oponente se ha retirado",Movimientos.getCasillas(),false) ;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
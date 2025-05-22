package ConexionPartida;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import Tablero.FuncionesVisualesTablero;
import Tablero.MetodosMoverPiezas;

public class ControlDeJugadas {
	public static void hacerJugadas(BufferedReader in, BufferedWriter out, boolean color){
		 try {
//           Thread.sleep(50);

   	   if (color) {

			if (MetodosMoverPiezas.sensorDeTurnosDosJugadores) {
			out.write(MetodosMoverPiezas.datosDeMovimientos.getOrigen() + "\n");

			out.write(MetodosMoverPiezas.datosDeMovimientos.getDestino() + "\n");

			out.write(MetodosMoverPiezas.datosDeMovimientos.getFicha() + "\n");

			out.write(MetodosMoverPiezas.datosDeMovimientos.getMovimientos() + "\n");
  	     
  	        MetodosMoverPiezas.sensorDeTurnosDosJugadores = false; // Resetea el flag SOLO después de enviar

  	        //out.write(MetodosMoverPiezas.sensorDeTurnosDosJugadores + "\n");
           color=false;
           out.flush();

			}
  	        


			}
            else {
				FuncionesVisualesTablero.setVerCasillas(false);
				String origen = in.readLine();

				String destino = in.readLine();

				String ficha = in.readLine();

				String movimientos = in.readLine();
//               String a = inFinal.readLine();
//               MetodosMoverPiezas.sensorDeTurnosDosJugadores = Boolean.parseBoolean(a);
				MetodosMoverPiezas.moverPiezas(origen, destino, Movimientos.getCasillas(), ficha,
						movimientos);
				FuncionesVisualesTablero.resetColores(Movimientos.getCasillas());
				color = true;
				MetodosMoverPiezas.sensorDeTurnosDosJugadores = false; // Resetea el flag SOLO después de
																		// enviar

			}
			SalaInfo.setColor(color);
			FuncionesVisualesTablero.setVerCasillas(true);

       
   } catch (IOException e) {
		e.printStackTrace();
	} finally {
		try {
			if (out != null)
				out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (in != null)
				in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	}
}

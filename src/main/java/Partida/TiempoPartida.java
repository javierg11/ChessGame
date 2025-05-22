package Partida;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class TiempoPartida {
	private static double tiempoBlancas;
	private static double tiempoNegras;
	private static int incremento;
	private static boolean enPartida;
	private static JLabel label = new JLabel();
	private static Thread hilo;
	static JButton[][] casillas = null;

	public TiempoPartida() {

	}
	public TiempoPartida(JLabel label, double minutosIniciales, JButton[][] casillas, int incremento) {
		TiempoPartida.label = label;
		TiempoPartida.tiempoBlancas = minutosIniciales * 60;
		TiempoPartida.tiempoNegras = minutosIniciales * 60;
		TiempoPartida.enPartida = true;
		TiempoPartida.casillas = casillas;
		TiempoPartida.enPartida = true;
		TiempoPartida.incremento = incremento;
	}

	public static void iniciar() {
		hilo = new Thread(() -> {
			while (enPartida) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					break;
				}

				if (CalculosEnPartida.colorAMover()) {
					if (tiempoBlancas > 0) {
						tiempoBlancas--;
					}
				} else {
					if (tiempoNegras > 0) {
						tiempoNegras--;
					}
				}
				// Decorar el como se ve el tiempo
				SwingUtilities.invokeLater(() -> {
				    boolean blancasJuegan = CalculosEnPartida.colorAMover();

				    String tiempoBlancasStr = tiempoVisual(tiempoBlancas);
				    String tiempoNegrasStr = tiempoVisual(tiempoNegras);

				    String tiempoBlancasHTML, tiempoNegrasHTML;

				    if (blancasJuegan) {
				        tiempoBlancasHTML = "<span style='color: #FFD700; font-size:28px; font-weight:bold;'>" 
				            + tiempoBlancasStr + " <small style='font-size:14px;'>Blancas</small></span>";
				        tiempoNegrasHTML = "<span style='color: #888888; font-size:20px;'>" 
				            + tiempoNegrasStr + " <small style='font-size:12px;'>Negras</small></span>";
				    } else {
				        tiempoBlancasHTML = "<span style='color: #888888; font-size:20px;'>" 
				            + tiempoBlancasStr + " <small style='font-size:12px;'>Blancas</small></span>";
				        tiempoNegrasHTML = "<span style='color: #FFD700; font-size:28px; font-weight:bold;'>" 
				            + tiempoNegrasStr + " <small style='font-size:14px;'>Negras</small></span>";
				    }

				    label.setText(
				        "<html><div style='text-align:center;'>" 
				        + tiempoBlancasHTML + "<br>" + tiempoNegrasHTML + 
				        "</div></html>"
				    );
				});




				if (tiempoBlancas == 0 || tiempoNegras == 0) {
					enPartida = false;
					String texto = "<html><b>¡Victoria!</b><br>El jugador "
							+ (CalculosEnPartida.colorAMover() ? "blanco" : "negro")
							+ " se le ha acabado el tiempo.<br><i>Caida de bandera.</i></html>";
					FinPartida.mensajeTerminarPartida(texto, casillas);
				}
			}
		});
		hilo.start();

	}

	public static void detenerTiempo() {
		enPartida = false;
		if (hilo != null)
			hilo.interrupt();
	}

	public static void iniciarTiempo() {
		iniciar();
	}

	public static void incrementoTiempoPorJugada() {
	    // Aplica el incremento al jugador que acaba de mover:
	    if (!CalculosEnPartida.colorAMover()) {
	        tiempoBlancas += incremento;
	    } else {
	        tiempoNegras += incremento;
	    }
	}
	
	public static String tiempoVisual(double segundosTotales) {
        int horas = (int)segundosTotales / 3600;
        int minutos = (int)(segundosTotales % 3600) / 60;
        int segundos = (int)segundosTotales % 60;
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

}

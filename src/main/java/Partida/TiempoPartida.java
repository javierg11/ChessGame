package Partida;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class TiempoPartida {
    public static double tiempoBlancas;
   

	

	public static double tiempoNegras;
    private int incremento;
    private volatile boolean enPartida;
    private JLabel label;
    private Thread hilo;
    private JButton[][] casillas;
    double minutosIniciales;

    public TiempoPartida(JLabel label, double minutosIniciales, JButton[][] casillas, int incremento) {
        this.label = label;
        TiempoPartida.tiempoBlancas = minutosIniciales * 60;
        TiempoPartida.tiempoNegras = minutosIniciales * 60;
        this.casillas = casillas;
        this.incremento = incremento;
        this.enPartida = false;
        this.minutosIniciales=minutosIniciales;
    }

    public void iniciar() {
        enPartida = true;
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

                SwingUtilities.invokeLater(() -> actualizarLabel());

                comprobarFinPartida();
            }
        });
        hilo.start();
    }

	    public void detenerTiempo() {
	        enPartida = false;
	        if (hilo != null) {
	            hilo.interrupt();
	        }
	    }

    public void aplicarIncremento() {
        // Aplica el incremento al jugador que acaba de mover:
        if (!CalculosEnPartida.colorAMover()) {
            tiempoBlancas += incremento;
        } else {
            tiempoNegras += incremento;
        }
    }

    private void actualizarLabel() {
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
    }

    public static String tiempoVisual(double segundosTotales) {
        int horas = (int)segundosTotales / 3600;
        int minutos = (int)(segundosTotales % 3600) / 60;
        int segundos = (int)segundosTotales % 60;
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    private void comprobarFinPartida() {
        if (tiempoBlancas == 0 || tiempoNegras == 0) {
        	detenerTiempo();
            String texto = "<html><b>¡Victoria!</b><br>El jugador "
                    + (CalculosEnPartida.colorAMover() ? "blanco" : "negro")
                    + " se le ha acabado el tiempo.<br><i>Caida de bandera.</i></html>";
            FinPartida.mensajeTerminarPartida(texto, casillas, true, false);
            
        }
    }
    public static void setTiempoBlancas(double tiempoBlancas) {
    	TiempoPartida.tiempoBlancas = tiempoBlancas;
	}

	public static void setTiempoNegras(double tiempoNegras) {
		TiempoPartida.tiempoNegras = tiempoNegras;
	}
	public static double getTiempoBlancas() {
		return tiempoBlancas;
	}

	public static double getTiempoNegras() {
		return tiempoNegras;
	}
}

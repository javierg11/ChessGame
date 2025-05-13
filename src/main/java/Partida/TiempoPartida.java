package Partida;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class TiempoPartida {
    private static int tiempoBlancas;
    private static int tiempoNegras;
    private static boolean enPartida;
    private static JLabel label = new JLabel();
    private static Thread hilo;
    static JButton [][] casillas =null;

    public TiempoPartida(JLabel label,int minutosIniciales,JButton [][] casillas) {
        TiempoPartida.label = label;
        TiempoPartida.tiempoBlancas = minutosIniciales * 60;
        TiempoPartida.tiempoNegras = minutosIniciales * 60;
        TiempoPartida.enPartida = true;
        TiempoPartida.casillas=casillas;
        TiempoPartida.enPartida = true;
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
                    if (tiempoBlancas > 0) tiempoBlancas--;
                } else {
                    if (tiempoNegras > 0) tiempoNegras--;
                }
                //Decorar el como se ve el tiempo
                SwingUtilities.invokeLater(() -> {
                    label.setText(
                        String.format("%02d:%02d-%02d:%02d",
                            tiempoBlancas / 60, tiempoBlancas % 60,
                            tiempoNegras / 60, tiempoNegras % 60
                        )
                    );
                });

                if (tiempoBlancas == 0 || tiempoNegras == 0) {
                    enPartida = false;
                    String texto = "<html><b>¡Victoria!</b><br>El jugador "
    						+ (CalculosEnPartida.colorAMover() ? "blanco" : "negro")
    						+ " se le ha acabado el tiempo.<br><i>Caida de bandera.</i></html>";
                    FinPartida.mensajeTerminarPartida(texto,casillas);
                }
            }
        });
        hilo.start();

    }

    public static void detenerTiempo() {
        enPartida = false;
        if (hilo != null) hilo.interrupt();
    }
    public static void iniciarTiempo() {
    	iniciar();
    }
    
    public static String a() {
    	return "sd";
    }

    
}

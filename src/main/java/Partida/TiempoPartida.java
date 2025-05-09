package Partida;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class TiempoPartida {
    private volatile int tiempoBlancas;
    private volatile int tiempoNegras;
    private volatile boolean enPartida;
    private final JLabel label;
    private Thread hilo;

    public TiempoPartida(JLabel label, int minutosIniciales) {
        this.label = label;
        this.tiempoBlancas = minutosIniciales * 60;
        this.tiempoNegras = minutosIniciales * 60;
        this.enPartida = true;
    }

    public void iniciar() {
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
                    //Poner fin partida
                }
            }
        });
        hilo.start();
    }

    public void detener() {
        enPartida = false;
        if (hilo != null) hilo.interrupt();
    }

    
}

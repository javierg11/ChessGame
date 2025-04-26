package Piezas;

import javax.swing.JButton;

public class Peon extends Piezas {

    @Override
    public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha, boolean verMovimientos) {
        resetColores(casillas);
        inicializarPosicion(posicion, verMovimientos);

        int direccion = ficha.contains("b") ? 1 : -1; 
        int filaInicial = ficha.contains("b") ? 1 : 6;

        // Movimiento de dos casillas desde la posición inicial
        if (filaActual == filaInicial &&
            dentroTablero(filaActual + direccion, columnaActual) &&
            dentroTablero(filaActual + 2 * direccion, columnaActual) &&
            casillas[filaActual + direccion][columnaActual].getText().isEmpty() &&
            casillas[filaActual + 2 * direccion][columnaActual].getText().isEmpty()) {
            
            resaltarCasilla(filaActual + 2 * direccion, columnaActual, casillas);
            conseguirJugadasLogicas(filaActual + 2 * direccion, columnaActual);
        }

        // Movimiento de una casilla hacia adelante
        if (dentroTablero(filaActual + direccion, columnaActual) &&
            casillas[filaActual + direccion][columnaActual].getText().isEmpty()) {
            
            resaltarCasilla(filaActual + direccion, columnaActual, casillas);
            conseguirJugadasLogicas(filaActual + direccion, columnaActual);
        }

        // Capturas diagonales
        marcarCapturas(filaActual + direccion, columnaActual, casillas, ficha);

        return jugadasTotales + "\n";
    }

    /**
     * Marca las casillas de captura diagonal para el peón.
     */
    static void marcarCapturas(int fila, int columna, JButton[][] casillas, String ficha) {
        // Izquierda diagonal
        if (dentroTablero(fila, columna - 1)) {
            String texto = casillas[fila][columna - 1].getText();
            if (!texto.isEmpty() && !mismoColor(casillas, fila, columna - 1, ficha)) {
                conseguirJugadasLogicas(fila, columna - 1);
                resaltarCasilla(fila, columna - 1, casillas);
            }
            
        }
        // Derecha diagonal
        if (dentroTablero(fila, columna + 1)) {
            String texto = casillas[fila][columna + 1].getText();
            if (!texto.isEmpty() && !mismoColor(casillas, fila, columna + 1, ficha)) {
                conseguirJugadasLogicas(fila, columna + 1);
                resaltarCasilla(fila, columna + 1, casillas);
            }
            
        }
    }

    /**
     * Comprueba si la posición está dentro del tablero.
     */
    static boolean dentroTablero(int fila, int columna) {
        return fila >= 0 && fila < 8 && columna >= 0 && columna < 8;
    }
}

package Piezas;

import javax.swing.JButton;

public class Peon extends Piezas {

    @Override
    public String calcularMovimientos(String posicion, JButton[][] casillas, String ficha, boolean movEspecial) {
        inicializarPosicion(posicion);

        int direccion = ficha.contains("b") ? 1 : -1; 
        int filaInicial = ficha.contains("b") ? 1 : 6;

     // Movimiento de dos casillas desde la posición inicial
        int nuevaFila = filaActual + direccion;
        int nuevaFila2 = filaActual + 2 * direccion;
        int nuevaColumna = columnaActual;

        if (filaActual == filaInicial &&
            dentroTablero(nuevaFila, nuevaColumna) &&
            dentroTablero(nuevaFila2, nuevaColumna) &&
            casillas[nuevaFila][nuevaColumna].getText().isEmpty() &&
            casillas[nuevaFila2][nuevaColumna].getText().isEmpty()) {
                
            conseguirJugadasLogicas(nuevaFila2, nuevaColumna);
        }


     // Movimiento de una casilla hacia adelante

        if (dentroTablero(nuevaFila, nuevaColumna)) {
            if (casillas[nuevaFila][nuevaColumna].getText().isEmpty()) {
                conseguirJugadasLogicas(nuevaFila, nuevaColumna);
            }
        }

        // Capturas diagonales
        // Filtro para evitar ArrayIndexOutOfBoundsException en capturas
        // Diagonal izquierda
        int diagIzqCol = columnaActual - 1;
        if (dentroTablero(nuevaFila, diagIzqCol)) {
            marcarCapturas(nuevaFila, diagIzqCol, casillas, ficha,ficha.contains("w"));
        }
        // Diagonal derecha
        int diagDerCol = columnaActual + 1;
        if (dentroTablero(nuevaFila, diagDerCol)) {
            marcarCapturas(nuevaFila, diagDerCol, casillas, ficha,ficha.contains("w"));
        }


        return jugadasTotales + "\n";
    }

    /**
     * Marca las casillas de captura diagonal para el peón.
     */
    private void marcarCapturas(int fila, int columna, JButton[][] casillas, String ficha, boolean esBlanco) {
        if (dentroTablero(fila, columna)) {
            String texto = casillas[fila][columna].getText();
            if (!texto.isEmpty() && !mismoColor(casillas, fila, columna, ficha)) {
                conseguirJugadasLogicas(fila, columna);
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

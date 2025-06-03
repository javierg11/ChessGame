package ConexionPartida;

import javax.swing.JButton;

public class Movimientos {
    private String origen;
    private String destino;
    private static JButton[][] casillas;
    private String ficha;
    private String movimientos;
    private static Boolean colorAJugar;

    public Movimientos() {
    	
    }

	// Constructor
    public Movimientos(String origen, String destino, String ficha, String movimientos,JButton[][] casillas) {
        this.origen = origen;
        this.destino = destino;
        this.ficha = ficha;
        this.movimientos = movimientos;
        Movimientos.casillas=casillas;
    }

    // Getters
    public String getOrigen() {
        return origen;
    }
    public Boolean isColorAJugar() {
		return colorAJugar;
	}

	
    public String getDestino() {
        return destino;
    }

    public static JButton[][] getCasillas() {
        return casillas;
    }

    public String getFicha() {
        return ficha;
    }

    public String getMovimientos() {
        return movimientos;
    }

    // Setters (opcional)
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
    public static void setColorAJugar(boolean colorAJugar) {
    	Movimientos.colorAJugar = colorAJugar;
	}
    public static void setCasillas(JButton[][] casillas) {
    	Movimientos.casillas = casillas;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public void setMovimientos(String movimientos) {
        this.movimientos = movimientos;
    }

    // Método para mostrar información (opcional)
    public void mostrarInfo() {
        System.out.println("Origen: " + origen);
        System.out.println("Destino: " + destino);
        System.out.println("Ficha: " + ficha);
        System.out.println("Movimientos: " + movimientos);
        // Puedes mostrar más info sobre casillas si lo necesitas
    }
}


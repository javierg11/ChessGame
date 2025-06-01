package ProblemasAjedrez;

public class PosicionPiezas {
    private String pieza;
    private String casilla;

    public PosicionPiezas() {}

    public PosicionPiezas(String pieza, String casilla) {
        this.pieza = pieza;
        this.casilla = casilla;
    }

    public String getPieza() {
        return pieza;
    }

    public void setPieza(String pieza) {
        this.pieza = pieza;
    }

    public String getCasilla() {
        return casilla;
    }

    public void setCasilla(String casilla) {
        this.casilla = casilla;
    }
}

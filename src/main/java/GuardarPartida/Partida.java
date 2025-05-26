package GuardarPartida;

import java.util.List;

public class Partida {
    private int partida;
    private String nombre;
    private List<String> jugadas;

    // Constructor vacío (Jackson lo necesita)
    public Partida() {}

    public Partida(int partida, String nombre, List<String> jugadas) {
        this.partida = partida;
        this.nombre = nombre;
        this.jugadas = jugadas;
    }

    // Getters y setters
    public int getPartida() { return partida; }
    public void setPartida(int partida) { this.partida = partida; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<String> getJugadas() { return jugadas; }
    public void setJugadas(List<String> jugadas) { this.jugadas = jugadas; }
}

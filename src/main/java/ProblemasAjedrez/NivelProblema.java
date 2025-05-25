package ProblemasAjedrez;

import java.util.List;

public class NivelProblema {
    private int nivel;
    private List<PosicionPiezas> piezas;

    public NivelProblema() {}

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public List<PosicionPiezas> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<PosicionPiezas> piezas) {
        this.piezas = piezas;
    }
}

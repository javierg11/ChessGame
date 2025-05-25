package ProblemasAjedrez;

public class SolucionProblema {
    private String movimiento;
    private String pieza;
    private int nivel;
	public SolucionProblema(String movimiento,String pieza,int nivel) {
    	this.movimiento=movimiento;
    	this.pieza=pieza;
    	this.nivel=nivel;
    }
    public SolucionProblema() {}
	
	 public int getNivel() {
		return nivel;
	}


	public void setNivel(int nivel) {
		this.nivel = nivel;
	}


	public String getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(String movimiento) {
		this.movimiento = movimiento;
	}

	public String getPieza() {
		return pieza;
	}

	public void setPieza(String pieza) {
		this.pieza = pieza;
	}


}

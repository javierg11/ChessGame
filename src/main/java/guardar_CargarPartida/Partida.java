package guardar_CargarPartida;

import java.util.List;

public class Partida {
	 private int idPartida;
	    private String nombre;          // Nombre descriptivo de la partida (opcional)
	    private String nombrePropio;    // Nombre del jugador conectado (clave foránea)
	    private String nombreOponente;
	    private char jugadoCon;         // 'B' o 'N'
	    private List<String> jugadas;
		public int getIdPartida() {
			return idPartida;
		}
		
	    public Partida() {
	    }

	    public Partida(int idPartida, String nombre, String nombrePropio, String nombreOponente, char jugadoCon, List<String> partidas) {
	        this.idPartida = idPartida;
	        this.nombre = nombre;
	        this.nombrePropio = nombrePropio;
	        this.nombreOponente = nombreOponente;
	        this.jugadoCon = jugadoCon;
	        this.jugadas = partidas;
	    }
		public void setIdPartida(int idPartida) {
			this.idPartida = idPartida;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getNombrePropio() {
			return nombrePropio;
		}
		public void setNombrePropio(String nombrePropio) {
			this.nombrePropio = nombrePropio;
		}
		public String getNombreOponente() {
			return nombreOponente;
		}
		public void setNombreOponente(String nombreOponente) {
			this.nombreOponente = nombreOponente;
		}
		public char getJugadoCon() {
			return jugadoCon;
		}
		public void setJugadoCon(char jugadoCon) {
			this.jugadoCon = jugadoCon;
		}

		public List<String> getJugadas() {
			return jugadas;
		}

		public void setJugadas(List<String> jugadas) {
			this.jugadas = jugadas;
		}
		
}

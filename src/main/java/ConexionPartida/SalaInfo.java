package ConexionPartida;

public class SalaInfo {
    public String nombre;
    public int puerto, incrementoTiempo;
    public boolean requierePassword;
    public static Boolean color; //Gelow???????????
    public float tiempo; 

    public static Boolean isColor() {
		return color;
	}
    public static void setColor(boolean color) {
		SalaInfo.color = color;
	}
	public SalaInfo(String nombre, int puerto, boolean requierePassword, boolean color, float tiempo, int incrementoTiempo) {
        this.nombre = nombre;
        this.puerto = puerto;
        this.requierePassword = requierePassword;
        SalaInfo.color=color;
        this.tiempo = tiempo;
        this.incrementoTiempo=incrementoTiempo;
    }

   

	@Override
    public String toString() {
        return nombre + "|" + puerto + "|" + requierePassword + "|" +color +"|"+tiempo+"|"+incrementoTiempo;
    }

    public static SalaInfo fromString(String s) {
        String[] parts = s.split("\\|");
        return new SalaInfo(parts[0], 
        		Integer.parseInt(parts[1]), 
        		Boolean.parseBoolean(parts[2]),
        		Boolean.parseBoolean(parts[3]),
        		Float.parseFloat(parts[4]),
        		Integer.parseInt(parts[5]));
        

    }
}

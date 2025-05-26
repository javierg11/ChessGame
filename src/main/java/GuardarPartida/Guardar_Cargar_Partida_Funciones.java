package GuardarPartida;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.InputStream;
import java.util.*;

public class Guardar_Cargar_Partida_Funciones {

	private static final String ARCHIVO_JSON = "GuardarPartida" + File.separator + "partidas.json";

	public static List<Partida> cargarPartidas() {
	    ObjectMapper mapper = new ObjectMapper();
	    List<Partida> listaPartidas = new ArrayList<>();
	    File archivo = new File(ARCHIVO_JSON);

	    try {
	        if (archivo.exists()) {
	            listaPartidas = mapper.readValue(archivo, new TypeReference<List<Partida>>() {});
	        } else {
	        	// Crea la carpeta si no existe
	            File carpeta = archivo.getParentFile();
	            if (carpeta != null && !carpeta.exists()) {
	                carpeta.mkdirs();
	            }
	            // Crea el archivo vacío (lista vacía de partidas)
	            mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, listaPartidas);
	             }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return listaPartidas;
	}


    
	public static void guardarPartida(String nombre, HashMap<Integer, String> jugadas) {
	    try {
	        File carpeta = new File("GuardarPartida");
	        if (!carpeta.exists()) carpeta.mkdirs();

	        ObjectMapper mapper = new ObjectMapper();
	        List<Partida> listaPartidas = cargarPartidas();

	        int nuevoNumero = listaPartidas.size() + 1;
	        List<String> listaJugadas = new ArrayList<>();
	        for (int i = 1; i <= jugadas.size(); i++) {
	            if (jugadas.containsKey(i)) {
	                listaJugadas.add(jugadas.get(i));
	            }
	        }

	        Partida nueva = new Partida(nuevoNumero, nombre, listaJugadas);
	        listaPartidas.add(nueva);

	        File archivo = new File(ARCHIVO_JSON);
	        System.out.println("Guardando en: " + archivo.getAbsolutePath());
	        mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, listaPartidas);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


}

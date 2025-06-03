package guardar_CargarPartida;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;

import ConexionBBDD.CargarPartida_BBDD;
import ConexionBBDD.GuardarPartida_BBDD;
import interfazGrafica.JFrameInicioSesion;

import java.io.File;
import java.util.*;

public class Funcion_Guardar_Cargar_Partida {

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
	        
	    }
	    return listaPartidas;
	}


    
	public static void guardarPartida(String nombre, HashMap<Integer, String> jugadas,String nombreOponente, char color) {
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
	        String nameUser="";
	        if (JFrameInicioSesion.getUsuario()==null)
	        	nameUser="Anonimo";
	        else
	        	nameUser=JFrameInicioSesion.getUsuario();

	        Partida nueva = new Partida(nuevoNumero, nombre, nameUser,nombreOponente,color,listaJugadas);
	        listaPartidas.add(nueva);

	        File archivo = new File(ARCHIVO_JSON);
	        System.out.println("Guardando en: " + archivo.getAbsolutePath());
	        mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, listaPartidas);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    public static void guardarPartidaBBDD(String nombre, String nombreOponente, char jugadoCon, HashMap<Integer, String> partidas) {
    	GuardarPartida_BBDD.guardarPartida(nombre, nombreOponente, jugadoCon, partidas); 
    }
    public static List<Partida> cargarPartidaBBDD() {
    	return CargarPartida_BBDD.cargarPartidasBBDD();
    }
}

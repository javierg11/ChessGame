package ProblemasAjedrez;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class LeerJSON {

    public List<NivelProblema> leerNiveles(String ruta) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getResourceAsStream(ruta);
            return mapper.readValue(is, new TypeReference<List<NivelProblema>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	 public List<PosicionPiezas> leerProblemasJSON(String ruta) {
	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            InputStream is = getClass().getResourceAsStream(ruta);
	            if (is == null) {
	                System.out.println("No se encontró el archivo: " + ruta);
	                return Collections.emptyList();
	            }
	            return mapper.readValue(is, new TypeReference<List<PosicionPiezas>>() {});
	        } catch (Exception e) {
	            e.printStackTrace();
	            return Collections.emptyList();
	        }
	    }
	 
	 public List<SolucionProblema> leerSolucionProblema(String ruta) {
	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            InputStream is = getClass().getResourceAsStream(ruta);
	            if (is == null) {
	                System.out.println("No se encontró el archivo: " + ruta);
	                return Collections.emptyList();
	            }
	            return mapper.readValue(is, new TypeReference<List<SolucionProblema>>() {});
	        } catch (Exception e) {
	            e.printStackTrace();
	            return Collections.emptyList();
	        }
	    }


}

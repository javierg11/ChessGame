package ConexionBBDD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DatosBBDD {
    private static String ip;
    public static String usuarioBaseDatos;
    public static String contraBaseDatos;
    public static String rutaBaseDatos;
    public static final String driverMysql = "com.mysql.jdbc.Driver";

    // Método estático para cargar los datos del archivo
    static {
    	InputStream is = DatosBBDD.class.getResourceAsStream("/DatosBBDD/datos.txt");
    	
        try (
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            ip = br.readLine();                       // Primera línea: IP
            usuarioBaseDatos = br.readLine();         // Segunda línea: usuario
            contraBaseDatos = br.readLine();          // Tercera línea: contraseña

            // Si la contraseña es "" (comillas vacías), la dejamos vacía
            if (contraBaseDatos != null && contraBaseDatos.equals("\"\"")) {
                contraBaseDatos = "";
            }

            // Si la línea está vacía, también la dejamos vacía
            if (contraBaseDatos == null) {
                contraBaseDatos = "";
            }

            rutaBaseDatos = "jdbc:mysql://" + ip + "/TFGChessGame";
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            // Valores por defecto si hay error
            ip = "localhost";
            usuarioBaseDatos = "rooet";
            contraBaseDatos = "";
            rutaBaseDatos = "jdbc:mysql://" + ip + "/TFGChessGame";
        }
    }
}

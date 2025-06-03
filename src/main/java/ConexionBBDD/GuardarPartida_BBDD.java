package ConexionBBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import interfazGrafica.JFrameInicioSesion;

public class GuardarPartida_BBDD extends DatosBBDD {

	public static void guardarPartida(String nombre, String nombreOponente, char jugadoCon, HashMap<Integer, String> partidas) {
	    // Si jugadoCon no es 'B' ni 'N', se usa 'B' por defecto
	    char color = (jugadoCon == 'B' || jugadoCon == 'N') ? jugadoCon : 'B';

	    // Obtener el nombre del usuario actual
	    String nombrePropio = JFrameInicioSesion.getUsuario();

	    // Convertir el HashMap de partidas a un String (por ejemplo, separadas por comas)
	    List<String> listaJugadas = new ArrayList<>();
	    for (int i = 1; i <= partidas.size(); i++) {
	        if (partidas.containsKey(i)) {
	            listaJugadas.add(partidas.get(i));
	        }
	    }
	    String jugadasString = String.join(", ", listaJugadas);

	    // Consulta SQL para insertar la partida
	    String sql = "INSERT INTO Partidas (nombre, nombrePropio, nombreOponente, jugadoCon, partidas) VALUES (?, ?, ?, ?, ?)";

	    // Usar try-with-resources para asegurar que los recursos se cierren
	    try (Connection conn = DriverManager.getConnection(rutaBaseDatos, usuarioBaseDatos, contraBaseDatos);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        // Asignar parámetros
	        stmt.setString(1, nombre);             // Nombre de la partida
	        stmt.setString(2, nombrePropio);       // Clave foránea: usuario actual
	        stmt.setString(3, nombreOponente);
	        stmt.setString(4, String.valueOf(color));
	        stmt.setString(5, jugadasString);      // Jugadas como String

	        // Ejecutar la inserción
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        System.err.println("Error al guardar la partida: " + e.getMessage());
	        throw new RuntimeException("Error al guardar la partida", e); // Opcional: relanzar la excepción
	    }
	}


}

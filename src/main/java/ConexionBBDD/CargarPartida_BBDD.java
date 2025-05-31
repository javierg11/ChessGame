package ConexionBBDD;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import InterfazGrafica.JFrameInicioSesion;
import guardar_CargarPartida.Partida;

public class CargarPartida_BBDD extends DatosBBDD{

	public static List<Partida> cargarPartidasBBDD() {
	    List<Partida> listaPartidas = new ArrayList<>();
	    String nombrePropio = JFrameInicioSesion.getUsuario();

	    String sql = "SELECT Id_partida, nombre, nombreOponente, jugadoCon, partidas FROM Partidas WHERE nombrePropio = ?";

	    try (Connection conn = DriverManager.getConnection(rutaBaseDatos, usuarioBaseDatos, contraBaseDatos);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, nombrePropio);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Partida partida = new Partida();
	                partida.setIdPartida(rs.getInt("Id_partida"));
	                partida.setNombre(rs.getString("nombre"));
	                partida.setNombrePropio(nombrePropio);
	                partida.setNombreOponente(rs.getString("nombreOponente"));
	                partida.setJugadoCon(rs.getString("jugadoCon").charAt(0));

	              	                
	                String jugadasString = rs.getString("partidas");
	                List<String> jugadas = new ArrayList<>();
	                if (jugadasString != null && !jugadasString.trim().isEmpty()) {
	                    String[] partes = jugadasString.split(",\\s*");
	                    for (String parte : partes) {
	                        String[] keyValue = parte.split("=", 2);
	                        if (keyValue.length > 1) {
	                            jugadas.add(keyValue[1]);
	                        }
	                    }
	                }
	                partida.setJugadas(jugadas);
	                

	                listaPartidas.add(partida);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al cargar las partidas: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return listaPartidas;
	}



}

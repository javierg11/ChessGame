package ConexionBBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import InterfazGrafica.JFrameInicioSesion;

public class UsuarioConectado extends DatosBBDD{
	public static boolean usuarioConetado(String usuario) {
		if (JFrameInicioSesion.getUsuario()==null) 
			return false;
	    boolean conectado = false;

	    try {
			Class.forName(driverMysql);
			Connection conexion = DriverManager.getConnection(rutaBaseDatos, usuarioBaseDatos, contraBaseDatos);

	        // Supón que tienes una variable "conexion" ya inicializada
	        String sql = "SELECT cuentaIniciada FROM Jugadores WHERE nombre = ?";
	        PreparedStatement ps = conexion.prepareStatement(sql);
	        ps.setString(1, usuario);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            conectado = rs.getBoolean("cuentaIniciada");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return conectado;
	}
	
	public static void cerrar_Iniciar_Sesion(String usuario, boolean iniciar) {
		if (JFrameInicioSesion.getUsuario()==null) 
			return;
	    try {
	        Class.forName(driverMysql);
	        Connection conexion = DriverManager.getConnection(rutaBaseDatos, usuarioBaseDatos, contraBaseDatos);
	        String sql="";
	        if (iniciar)
	        	sql = "UPDATE Jugadores SET cuentaIniciada = TRUE WHERE nombre = ?";
	        else
	        	sql = "UPDATE Jugadores SET cuentaIniciada = FALSE WHERE nombre = ?";
	        PreparedStatement ps = conexion.prepareStatement(sql);
	        ps.setString(1, usuario);
	        ps.executeUpdate();

	        ps.close();
	        conexion.close();


	    } catch (ClassNotFoundException | SQLException e) {
	        e.printStackTrace();
	    }
	}


}

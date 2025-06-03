package ConexionBBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

import interfazGrafica.EmpezarAJugar;

public class CrearUsuario extends DatosBBDD{
	private static String sql;
	public static void crearUsuario(String usuario, String pass, String correo, JFrame ventana) {
	    Connection conexion = null;
	    PreparedStatement ps = null;
	    try {
	        boolean test = true;
	        Class.forName(driverMysql);
	        conexion = DriverManager.getConnection(rutaBaseDatos, usuarioBaseDatos, contraBaseDatos);

	        if (pass.equals("")) {
	            int n1 = JOptionPane.showConfirmDialog(ventana, "Tu contraseña está vacía ¿Lo aceptas?", "Info",
	                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	            test = (n1 == JOptionPane.YES_OPTION);
	            if (n1 == JOptionPane.CLOSED_OPTION) {
	                System.out.println("Has cerrado");
	            }
	        }

	        String passHash = hashPassword(pass);
	        if (test) {
	            sql = "INSERT INTO Jugadores (nombre, password, email) VALUES (?, ?, ?)";
	            ps = conexion.prepareStatement(sql);
	            ps.setString(1, usuario);
	            ps.setString(2, passHash);
	            ps.setString(3, correo);
	            int filasAfectadas = ps.executeUpdate();

	            if (filasAfectadas > 0) {
	                ventana.dispose();
	                EmpezarAJugar.opcionesDeJuego();
	            } else {
	                JOptionPane.showMessageDialog(ventana,
	                        "Hubo un problema al crear el usuario. Inténtelo más tarde", "Error",
	                        JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    } catch (ClassNotFoundException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(ventana, "No se encontró el driver de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        // Usuario ya existe (MySQL error code 1062)
	        if (ex.getErrorCode() == 1062) {
	            JOptionPane.showMessageDialog(ventana, "Ese nombre de usuario ya existe, prueba con otro nombre",
	                    "Error", JOptionPane.ERROR_MESSAGE);
	        } 
	        // Otros errores SQL
	        else {
	            JOptionPane.showMessageDialog(ventana, "Error en la base de datos",
	                    "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    } finally {
	        try { if (ps != null) ps.close(); } catch (Exception e) {}
	        try { if (conexion != null) conexion.close(); } catch (Exception e) {}
	    }
	}


	
	
	public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}

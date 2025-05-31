package ConexionBBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

import InterfazGrafica.EmpezarAJugar;

public class CrearUsuario extends DatosBBDD{
	private static String sql;
	public static void crearUsuario(String usuario, String pass,String correo, JFrame ventana) {
		try {
			boolean test=true;
			Class.forName(driverMysql);
			Connection conexion = DriverManager.getConnection(rutaBaseDatos, usuarioBaseDatos, contraBaseDatos);

			if (pass.equals("")) {
				int n1 = JOptionPane.showConfirmDialog(ventana, "Tu contraseña esta vacia ¿Lo aceptas?", "Info",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				switch (n1) {
				case JOptionPane.YES_OPTION:
					test = true;
					break;
				case JOptionPane.NO_OPTION:
					test = false;
					break;
				case JOptionPane.CLOSED_OPTION:
					test = false;
					System.out.printf("has cerrado\n");
				}
			}
				
			String passHash=hashPassword(pass);
			if (test) {
				sql = "INSERT INTO Jugadores (nombre, password, email) VALUES (?, ?, ?)";
				PreparedStatement ps = conexion.prepareStatement(sql);
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
				ps.close();
				conexion.close();
			}
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(ventana, "Ese nombre de usuario ya existe, prueba con otro nombre",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}

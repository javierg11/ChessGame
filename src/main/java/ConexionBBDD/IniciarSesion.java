package ConexionBBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

import InterfazGrafica.EmpezarAJugar;
import InterfazGrafica.JFrameInicioSesion;


public class IniciarSesion extends DatosBBDD{
	private static String sql;
	
	public static void iniciarSesion (String usuario, String pass, JFrame ventana) {
		if (UsuarioConectado.usuarioConetado(usuario)) {
			JOptionPane.showMessageDialog(ventana, "Ya has inicado sesión", "Aviso", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {
		    Class.forName(driverMysql);
		    Connection conexion = DriverManager.getConnection(rutaBaseDatos, usuarioBaseDatos, contraBaseDatos);
		    sql = "SELECT password FROM Jugadores WHERE nombre = '" + usuario + "' ";
		    Statement sentencia = conexion.createStatement();
		    sentencia.execute(sql);
		    ResultSet resultado = sentencia.getResultSet();

		    if (resultado.next()) {
		        if (checkPassword(pass, resultado.getString("password"))) {
		            String updateSql = "UPDATE Jugadores SET cuentaIniciada = TRUE WHERE nombre = '" + usuario + "'";
		            sentencia.execute(updateSql);
            		UsuarioConectado.cerrar_Iniciar_Sesion(usuario,true);
		            ventana.dispose();
		            EmpezarAJugar.opcionesDeJuego();
		        } else {
		            JOptionPane.showMessageDialog(ventana, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    } else
		        JOptionPane.showMessageDialog(ventana, "Ese usuario no existe", "Error", JOptionPane.ERROR_MESSAGE);
		    sentencia.close();
		    conexion.close();
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(ventana, "Error bbdd", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(ventana, "Error bbdd", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}

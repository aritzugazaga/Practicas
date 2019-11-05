package ejemplo;

import java.sql.*;

public class Ejemplo {
	
	public static void main(String[] args) {
		try {
			Class.forName("org.sqlite.JDBC");
			
			Connection conn = DriverManager.getConnection("jdbc:sqlite:data/usuario.db");
			
			Statement stmt = conn.createStatement();
			//conexión abierta
			//statement creado
			
			// consultar datos
			ResultSet rs = stmt.executeQuery("SELECT nombre, apellido FROM usuario");
			
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				
				System.out.println("Nombre: " + nombre + "Apellido: " + apellido);
			}
			
			//ya no queremos usarlos mas
			stmt.close();
			conn.close();
			
		}catch (ClassNotFoundException e) {
			System.out.println("No se ha podido guardar el driver");
		} catch (SQLException e) {
			System.out.println("No se ha podido conectar con la BD");
		}
		
		
	}
}

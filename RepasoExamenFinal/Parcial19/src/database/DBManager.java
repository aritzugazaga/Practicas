package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que gestiona la conexión a la base de datos
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public class DBManager {

	private Connection conn;
	
	/**
	 * Método que conecta a un fichero de base de datos SQLIte concreta ("/data/database.db")
	 * @throws DBManagerException se produce si existe algún problema al conectar a la BD.
	 */
	public void connect() throws DBManagerException {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:data/database.db");
		} catch (ClassNotFoundException | SQLException e) {
			throw new DBManagerException("Error connecting to database", e);
		}
	}
	
	/**
	 * Este método permite llevar a cabo la desconexión de la base de datos
	 * @throws DBManagerException se produce si existe algún problema al desconectar con la BD.
	 */
	public void disconnect() throws DBManagerException {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DBManagerException("Error disconnecting from database", e);
		}
	}
}

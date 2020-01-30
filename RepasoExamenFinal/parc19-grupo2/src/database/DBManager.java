package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import analysis.WordCount;

/**
 * Clase que gestiona la conexión a la base de datos
 * 
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public class DBManager {

	private Connection conn;

	/**
	 * Método que conecta a un fichero de base de datos SQLIte concreta
	 * ("/data/database.db")
	 * 
	 * @throws DBManagerException se produce si existe algún problema al conectar a
	 *                            la BD.
	 */
	public void connect() throws DBManagerException {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:data/database.db");
		} catch (ClassNotFoundException | SQLException e) {
			throw new DBManagerException("Error connecting to database", e);
		}
	}

	public void createStatisticsTable() throws DBManagerException {
		String sentSQL = "";
		try (Statement stmt = conn.createStatement();) {
			// Numeric es como long
			sentSQL = "create table if not exist statistics (avgwords real, topwords text, topcount integer, timestamp numeric)";
			stmt.executeUpdate(sentSQL);
		} catch (SQLException e) {
			throw new DBManagerException("Error creating statistics table", e);
		}
	}

	public void insertStatistics(float avgwords, WordCount wordCount) throws DBManagerException {
		String insertSQL = "insert into statistics values(?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

			stmt.setFloat(1, avgwords);;
			stmt.setString(2, wordCount.getWord());
			stmt.setInt(3, wordCount.getCount());
			stmt.setLong(4, System.currentTimeMillis());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DBManagerException("Error insert statistics into database.", e);
		}
	}

	/**
	 * Este método permite llevar a cabo la desconexión de la base de datos
	 * 
	 * @throws DBManagerException se produce si existe algún problema al
	 *                            desconectar con la BD.
	 */
	public void disconnect() throws DBManagerException {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DBManagerException("Error disconnecting from database", e);
		}
	}
}

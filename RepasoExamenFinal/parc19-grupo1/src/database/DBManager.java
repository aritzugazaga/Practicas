package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import analysis.Document;

/**
 * Clase que gestiona la conexiÃ³n a la base de datos
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
	
	public void createDocumentTable() throws DBManagerException {
		String sentSQL = "";
		try (Statement stmt = conn.createStatement();){
			sentSQL = "create table if not exist document (name string, chars integer, words integer, lines integer)";
			stmt.executeUpdate(sentSQL);
		} catch (SQLException e) {
			throw new DBManagerException("Error creating document table", e);
		}
	}
	
	public void insertDocuments(List<Document> documents) throws DBManagerException {
		String insertSQL = "insert into document values(?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
			for (Document document : documents) {
				stmt.setString(1, document.getName());
				stmt.setInt(2	, document.getChars()); 
				stmt.setInt(3, document.getWords());
				stmt.setInt(4, document.getLines());
				
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DBManagerException("Error insert documents into database.", e);
		}
	}

	
	/**
	 * Este mÃ©todo permite llevar a cabo la desconexiÃ³n de la base de datos
	 * @throws DBManagerException se produce si existe algÃºn problema al desconectar con la BD.
	 */
	public void disconnect() throws DBManagerException {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DBManagerException("Error disconnecting from database", e);
		}
	}
		
}
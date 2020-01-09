package examen.ord201512;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {
	private static Connection conn;
	private static Statement stmt;
	
	public synchronized static void conexion() {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:examen.db");
			stmt = conn.createStatement();
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXIST transmision(usuario STRING, numCapturas INTEGER)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized static void insertTrans( String usuario, long numCapturas ) {
		String sent = "insert into transmision values('" + usuario + "', " + numCapturas + ")";
		try {
			stmt.executeUpdate(sent);
		} catch (SQLException e) {
			System.out.println( "ERROR EN SENTENCIA SQL: " + sent);
			e.printStackTrace();
		}
	}
	
	public synchronized static void updateTrans( String usuario, long numCapturas ) {
		String sent = "update transmision set numCapturas=" + numCapturas + " where usuario='" + usuario +"'";
		try {
			stmt.executeUpdate(sent);
		} catch (SQLException e) {
			System.out.println( "ERROR EN SENTENCIA SQL: " + sent);
			e.printStackTrace();
		}
	}
	
	public synchronized static long selectTrans( String usuario ) {
		String sent = "select * from transmision where usuario='" + usuario + "'";
		try {
			ResultSet rs = stmt.executeQuery(sent);
			int numCapturas = -1;  // Si no hay fila para el usuario
			if (rs.next()) numCapturas = rs.getInt("numCapturas");
			rs.close();
			return numCapturas;
		} catch (SQLException e) {
			System.out.println( "ERROR EN SENTENCIA SQL: " + sent);
			e.printStackTrace();
			return -1;
		}
	}
	
	public synchronized static void insertTransHilo( String usuario, long numCapturas ) {
		final String sent = "insert into transmision values('" + usuario + "', " + numCapturas + ")";
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					stmt.executeUpdate(sent);
				} catch (SQLException e) {
					System.out.println( "ERROR EN SENTENCIA SQL: " + sent);
					e.printStackTrace();
				}
			}
		};
		(new Thread(r)).start();
	}
	
	public synchronized static void updateTransHilo( String usuario, long numCapturas ) {
		final String sent = "update transmision set numCapturas=" + numCapturas + " where usuario='" + usuario +"'";
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					stmt.executeUpdate(sent);
				} catch (SQLException e) {
					System.out.println( "ERROR EN SENTENCIA SQL: " + sent);
					e.printStackTrace();
				}
			}
		};
		(new Thread(r)).start();
	}
	
	public synchronized static void finConexion() {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package examen.ext201806.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import examen.ext201806.datos.BD;

public class BD {
private static Exception lastError = null;  // InformaciÃ³n de Ãºltimo error SQL ocurrido
	
	// Inicia conexión con la bas de datos
	public static Connection initBD( String nombreBD ) {
		try {
		    Class.forName("org.sqlite.JDBC");
		    Connection con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
			log( Level.INFO, "Conectada base de datos " + nombreBD, null );
		    return con;
		} catch (ClassNotFoundException | SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en conexiÃ³n de base de datos " + nombreBD, e );
			e.printStackTrace();
			return null;
		}
	}
	
	// Consulta tipo SELECT o las de modificar datos
	public static Statement usarBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			return statement;
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en uso de base de datos", e );
			e.printStackTrace();
			return null;
		}
	}
	
	// Crea tablas, si ya existen las deja tal cual estan
	public static Statement usarCrearTablasBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			statement.executeUpdate("create table if not exists celdas " +
				"(fila integer, columna integer, textoedicion string)");
			log( Level.INFO, "Creada base de datos", null );
			return statement;
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en creaciÃ³n de base de datos", e );
			e.printStackTrace();
			return null;
		}
	}
	
	// Reinicia las tablas (elimina y crea)
	public static Statement reiniciarBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			statement.executeUpdate("drop table if exists celdas");
			log( Level.INFO, "Reiniciada base de datos", null );
			return usarCrearTablasBD( con );
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en reinicio de base de datos", e );
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean celdaUpdate( Statement st, int fila, int columna, String textoedicion ) {
		String sentSQL = "";
		try {
			// Secu se utiliza para escapar parametros extraÃ±os
			// Comilla simple para strings
			sentSQL = "update celdas set textoedicion= '" + secu(textoedicion) + 
					"' where fila= " + fila + " and columna= " + columna;
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD añadida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que aÃƒÂ±adir 1 - error si no
				log( Level.SEVERE, "Error en update de BD. Id debería ser único\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	// Inserta datos en la BD
	public static boolean celdaInsert( Statement st, int fila, int columna, String textoedicion ) {
		String sentSQL = "";
		try {
			// Secu se utiliza para escapar parametros extraños
			// Comilla simple para strings
			sentSQL = "insert into celdas (fila, columna, textoedicion) values(" +
					 fila + ", " +
					columna + ", " +
					"'" + textoedicion + "')";
			int val = st.executeUpdate( sentSQL );
			log( Level.INFO, "BD aÃ±adida " + val + " fila\t" + sentSQL, null );
			if (val!=1) {  // Se tiene que aÃ±adir 1 - error si no
				log( Level.SEVERE, "Error en insert de BD\t" + sentSQL, null );
				return false;  
			}
			return true;
		} catch (SQLException e) {
			log( Level.SEVERE, "Error en BD\t" + sentSQL, e );
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}
	
	// Carga los items que hay en la tabla
	public static String selectCelda(Statement st, int fila, int columna) {
		String sentSQL = "";
		try {
			ResultSet rs = st.executeQuery(sentSQL);
			sentSQL = "select * from celdas where fila= " + fila + " and columna= " + columna;
			// Iteramos sobre la tabla result set
			// El metodo next() pasa a la siguiente fila, y devuelve true si hay más filas
			if (rs.next()) {
				String texto = rs.getString("textoedicion");
				return texto;
			}
			rs.close();
			log(Level.INFO, "BD consultada: " + sentSQL, null);
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			lastError = e;
			e.printStackTrace();
		}
		return null;
	}
	
	// Borrar celda
	public static boolean celdaDelete(Statement st,  int fila, int columna) {
		String sentSQL = "";
		try {
			sentSQL = "delete from celdas where fila= " + fila + " and columna= " + columna;
			int val = st.executeUpdate(sentSQL);
			log(Level.INFO, "BD borrada " + val + "fila\t" + sentSQL, null);
			return (val == 1);
		} catch (SQLException e) {
			log(Level.SEVERE, "Error en BD\t" + sentSQL, e);
			e.printStackTrace();
			return false;
		}

	}
	
	// Cierra la bas de datos abierta
	public static void cerrarBD( Connection con, Statement st ) {
		try {
			if (st!=null) st.close();
			if (con!=null) con.close();
			log( Level.INFO, "Cierre de base de datos", null );
		} catch (SQLException e) {
			lastError = e;
			log( Level.SEVERE, "Error en cierre de base de datos", e );
			e.printStackTrace();
		}
	}
		
	// Gestion de excepciones
	public static Exception getLastError() {
		return lastError;
	}
		
	
	// MÃ©todos privados

	// Devuelve el string "securizado" para volcarlo en SQL:
	// Mantiene solo los caracteres seguros en espaÃ±ol y sustituye ' por ''
	private static String secu( String string ) {
		StringBuffer ret = new StringBuffer();
		for (char c : string.toCharArray()) {
			if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZÃ±Ã‘Ã¡Ã©Ã­Ã³ÃºÃ¼Ã�Ã‰Ã�Ã“ÃšÃš.,:;-_(){}[]-+*=<>'\"Â¿?Â¡!&%$@#/\\0123456789 ".indexOf(c)>=0) ret.append(c);
		}
		return ret.toString().replaceAll( "'", "''" );
	}
		

	// Logging
		
	private static Logger logger = null;
	// MÃ©todo pÃºblico para asignar un logger externo
	/** Asigna un logger ya creado para que se haga log de las operaciones de base de datos
	 * @param logger	Logger ya creado
	 */
	public static void setLogger( Logger logger ) {
		BD.logger = logger;
	}
	// MÃ©todo local para loggear (si no se asigna un logger externo, se asigna uno local)
	private static void log( Level level, String msg, Throwable excepcion ) {
		if (logger==null) {  // Logger por defecto local:
			logger = Logger.getLogger( "BD-server-ejemplocs" );  // Nombre del logger
			logger.setLevel( Level.ALL );  // Loguea todos los niveles
			try {
				// logger.addHandler( new FileHandler( "bd-" + System.currentTimeMillis() + ".log.xml" ) );  // Y saca el log a fichero xml
				logger.addHandler( new FileHandler( "bd.log.xml", true ) );  // Y saca el log a fichero xml
			} catch (Exception e) {
				logger.log( Level.SEVERE, "No se pudo crear fichero de log", e );
			}
		}
		if (excepcion==null)
			logger.log( level, msg );
		else
			logger.log( level, msg, excepcion );
	}
}

package database;

/**
 * Esta excepción representa los problemas que pueden surgir durante el uso de la base de datos
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public class DBManagerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DBManagerException(String message) {
		super(message);
	}

	public DBManagerException(String message, Throwable cause) {
		super(message, cause);
	}

}

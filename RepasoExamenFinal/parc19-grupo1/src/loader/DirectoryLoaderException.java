package loader;

/**
 * Esta excepción se produce cuando hay un problema durante la carga de los ficheros de un directorio.
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public class DirectoryLoaderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DirectoryLoaderException(String message) {
		super(message);
	}
}

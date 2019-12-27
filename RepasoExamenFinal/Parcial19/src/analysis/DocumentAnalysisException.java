package analysis;

/**
 * Esta exception representa los posibles errores que puedan aparecer durante el análisis de un documento.
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public class DocumentAnalysisException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DocumentAnalysisException(String message) {
		super(message);
	}
	
	public DocumentAnalysisException(String message, Throwable e) {
		super(message, e);
	}
}

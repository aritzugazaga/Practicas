package analysis;

/**
 * Interfaz que define una serie de métodos para la notificación del estado del análisis de los documentos. 
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public interface AnalysisProgressCallback {

	/**
	 * Este método notifica el progreso actual (0 - 100) del análisis de documentos.
	 * @param progress recibe el progreso actual de 0 a 100
	 */
	public void setProgress(int progress);
	
	/**
	 * Este método indica que el proceso de análisis a finalizado correctamente.
	 */
	public void analysisFinished();
	
	/**
	 * Este método indica que el proceso de análisis ha sido detenido sin ser completado
	 */
	public void analysisStopped();
}

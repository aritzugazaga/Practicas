package analysis;

import java.util.List;

/**
 * Esta interfaz define la funcionalidad necesaria para calcular estadísticas agregadas sobre los documentos. 
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public interface AggregatedStatistics {

	/**
	 * Obtiene la media de palabras totales entre todos los documentos
	 * @return la media de palabras totales
	 */
	float getAvgWords();

	/**
	 * Obtiene las 10 palabras más usadas entre todos los documentos 
	 * @return una lista con las 10 palabras más usadas
	 */
	List<WordCount> getTopWords();

}
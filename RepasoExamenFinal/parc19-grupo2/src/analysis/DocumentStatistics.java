package analysis;

import java.util.List;

/**
 * Esta clase representa parte de la información obtenida del análisis de un documento
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public class DocumentStatistics {

	private int totalWords;
	private List<WordCount> wordCount;
	
	public DocumentStatistics(int totalWords, List<WordCount> wordCount) {
		this.totalWords = totalWords;
		this.wordCount = wordCount;
	}

	/**
	 * Total de palabras contenidas en el documento analizado
	 * @return total de palabras contenidas en el documento
	 */
	public int getTotalWords() {
		return totalWords;
	}

	/**
	 * Lista de frecuencias de palabras obtenidas tras analizar el documento
	 * @return lista de frecuencias de palabras
	 */
	public List<WordCount> getWordCount() {
		return wordCount;
	}
}

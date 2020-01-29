package analysis;

/**
 * Clase que representa la frecuencia (conteo) de aparición de una palabra determinada en un documento
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public class WordCount {

	private String word;
	private Integer count;
	
	/**
	 * Construye un objeto que representa la frecuencia de una palabra
	 * @param word palabra a la que asocia la frecuencia
	 * @param count frecuencia de la palabra
	 */
	public WordCount(String word, Integer count) {
		this.word = word;
		this.count = count;
	}

	/**
	 * Palabra a la que se refiere la frecuencia asociada
	 * @return la palabra asociada a la frecuencia
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Frecuencia asociada a la palabra
	 * @return frecuencia
	 */
	public Integer getCount() {
		return count;
	}
	
	@Override
	public String toString() {
		return String.format("word=%s, count=%s", word, count);
	}
}

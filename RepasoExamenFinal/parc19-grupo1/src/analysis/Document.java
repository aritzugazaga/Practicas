package analysis;

import java.util.List;

/**
 * Esta interfaz define la funcionalidad de un documento a analizar.
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public interface Document {

	/**
	 * Obtiene el nombre del documento
	 * @return nombre del documento
	 */
	public String getName();

	/**
	 * Obtiene el número de caracteres del documento
	 * @return número de caracteres del documento
	 */
	public int getChars();

	/**
	 * Obtiene el número de lineas en el documento.
	 * @return número de líneas en el documento
	 */
	public int getLines();

	/**
	 * Obtiene el número de palabras en el documento.
	 * @return número de palabras en el documento
	 */
	public int getWords();

	/**
	 * Obtiene la lista de frecuencias (WordCount) del documento ordenada de mayor a menor por frecuencia.
	 * @return lista de Wordcount del documento
	 */
	public List<WordCount> getWordCount();

	/**
	 * Lleva a cabo el análisis del documento y genera toda la información estadística para que pueda ser devuelta
	 * a través de los distintos métodos de la clase.
	 * @param minValue solamente las palabras cuya frecuencia de aparición sea mayor que el valor minValue indicado serán añadidas
	 * a la lista de frecuencias. 
	 * @throws DocumentAnalysisException se produce cuando ha habido un error al analizar el documento.
	 */
	public void analyze(int minValue) throws DocumentAnalysisException;

}
package analysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase lleva a cabo el análisis de la lista de documentos pasada como parámetro.
 * Dicho análisis se lleva a cabo en un nuevo thread para evitar el bloqueo de la aplicación.
 * El proceso de análisis consiste en iterar sobre la lista de documentos recibida y realizar
 * el análisis de cada uno de ellos.
 * Además llama a los métodos de la interfaz AnalysisProgressCallback para notificar a la UI:
 * 		1. El método de análisis indica el progreso del análisis (% procesado) llamando al método setProgress()
 *         pasando el porcentaje procesado correspondiente.
 *      2. Cuando el proceso finaliza correctamente debe notificar este hecho llamando al método analysisFinished()
 *      3. Si el proceso de análisis se detiene sin terminar de procesar (llamada a stop()) se debe notificar este
 *         hecho llamado al método analysisStopped(); 
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public class DocumentAnalyzer implements Runnable {
	
	private static final int MIN_VALUE = 50;
	
	private static final Logger logger = Logger.getLogger(DocumentAnalyzer.class.getName());

	private List<Document> documents;
	private Thread t; 	
	private AnalysisProgressCallback callback;
	
	/** Método constructor que recibe la lista de documentos a analizar y un objeto 
	 * que implementa la interfaz AnalysisProgressCallback para notificar el progreso del análisis
	 * @param documents lista de documentos que deben ser analizados
	 * @param callback interfaz que define una serie de métodos de notificación del progreso del análisis
	 */
	public DocumentAnalyzer(List<Document> documents, AnalysisProgressCallback callback) {
		this.documents = new ArrayList<>(documents); 
		this.callback = callback;
	}
	
	/**
	 * Método que lanza el thread de análisis de documentos
	 */
	public void launchAnalysis() {
		t = new Thread(this);
		t.start();
	}
	
	/**
	 * Método que detiene el hilo aunque no se hayan procesado todos los documentos
	 */
	public void stop() {
		t.interrupt();
	}

	@Override
	public void run() {
		logger.log(Level.INFO, String.format("Starting analysis of %d document(s)", documents.size()));
		
		int total = documents.size();
		int counter = 0;
		
		Iterator<Document> it = documents.iterator();
		while (it.hasNext() && !Thread.interrupted()) {
			Document document = it.next();
			logger.log(Level.INFO, String.format("Analyzing document '%s'", document.getName()));
			try {
				document.analyze(MIN_VALUE);
				counter++;
				int value = (int) ((counter / (float)total) * 100);
				callback.setProgress(value);
			} catch (DocumentAnalysisException e) {
				logger.log(Level.WARNING, "Error. Document '%s could not be analyzed", document.getName());
			}
		}
		
		if (!it.hasNext()) {
			logger.log(Level.INFO, "Analysis finished");
			callback.analysisFinished();
		} else {
			logger.log(Level.INFO, "Analysis stopped");
			callback.analysisStopped();
		}
	}
}

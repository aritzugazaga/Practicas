package loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import analysis.Document;
import analysis.FileDocument;

/**
 * Cargador de los ficheros de texto de un directorio.
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public class DirectoryLoader {

	private File directory;
	
	public DirectoryLoader(File file) throws DirectoryLoaderException {
		if (!file.isDirectory())
			throw new DirectoryLoaderException(
				String.format("Error creating document loader. %s is not a valid directory", file)
			);
		this.directory = file;
	}
	
	public List<Document> load() {
		List<Document> documents = new ArrayList<>();
		
		for (File file : directory.listFiles()) {
			if (file.getName().contains(".txt")) {
				// Para que no de fallo implementar la interfaz Document en FileDocument
				documents.add(new FileDocument(file));
			}
		}
		
		return documents; 
	}
}

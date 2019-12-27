package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import analysis.Document;

/**
 * Modelo de datos utilizado por la lista de documentos cargados a procesar.
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public class DocumentListModel extends AbstractListModel<Document> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final List<Document> documents = new ArrayList<>();
	
	public void addAll(List<Document> documents) {
		this.documents.addAll(documents);
		fireContentsChanged(this, 0, documents.size());
	}
	
	public void clear() {
		documents.clear();
		fireContentsChanged(this, 0, documents.size());
	}
	
	public List<Document> getDocuments() {
		return documents;
	}
	
	@Override
	public Document getElementAt(int index) {
		return documents.get(index);
	}

	@Override
	public int getSize() {
		return documents.size();
	}

}

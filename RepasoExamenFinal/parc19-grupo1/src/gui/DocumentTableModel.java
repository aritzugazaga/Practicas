package gui;

import javax.swing.table.AbstractTableModel;

import analysis.Document;

/**
 * Modelo de datos utilizado por la tabla que visualiza las frecuencias de palabras.
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public class DocumentTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String[] columnNames = { "Word", "Count" };
	private final Class<?>[] columnClasses = { String.class, Integer.class };
	
	private Document document = null;
	
	public DocumentTableModel() {
	
	}
	
	public DocumentTableModel(Document document) {
		this.document = document;
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnClasses[columnIndex];
	}

	@Override
	public int getRowCount() {
		return document == null ? 0 : document.getWordCount().size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (column == 0) {
			return document.getWordCount().get(row).getWord();
		} else {
			return document.getWordCount().get(row).getCount();
		}
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}

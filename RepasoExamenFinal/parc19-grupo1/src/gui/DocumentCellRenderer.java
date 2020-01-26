package gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import analysis.Document;

public class DocumentCellRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		JLabel defaultComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		Document document = (Document) value;
		defaultComponent.setText(document.getName());
		return defaultComponent;
	}
 
}

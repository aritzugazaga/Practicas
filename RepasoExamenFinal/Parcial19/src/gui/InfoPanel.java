package gui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import analysis.Document;

/**
 * Panel de inforamción del programa analizador de textos
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public class InfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel numDocumentsLabel;
	private JLabel charsLabel;
	private JLabel wordsLabel;
	private JLabel linesLabel;

	public InfoPanel() {
		numDocumentsLabel = new JLabel();
		
		Box documentsBox = new Box(BoxLayout.X_AXIS);
		documentsBox.add(numDocumentsLabel);

		charsLabel = new JLabel("Chars: -");
		wordsLabel = new JLabel("Words: -");
		linesLabel = new JLabel("Lines: -");
		
		Box infoBox = new Box(BoxLayout.X_AXIS);
		infoBox.add(charsLabel);
		infoBox.add(Box.createRigidArea(new Dimension(50, 0)));
		infoBox.add(wordsLabel);
		infoBox.add(Box.createRigidArea(new Dimension(50, 0)));
		infoBox.add(linesLabel);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		mainBox.add(documentsBox);
		mainBox.add(Box.createRigidArea(new Dimension(0, 50)));
		mainBox.add(infoBox);
		mainBox.add(Box.createRigidArea(new Dimension(0, 50)));
		
		add(mainBox);
		
		setLoadedDocuments(0);
	}
	
	public void setLoadedDocuments(int numDocuments) {
		numDocumentsLabel.setText("Loaded documents: " + numDocuments);
	}
	
	public void setDocumentInfo(Document document) {
		charsLabel.setText("Chars: " + document.getChars());
		wordsLabel.setText("Words: " + document.getWords());
		linesLabel.setText("Lines: "  + document.getLines());
	}
}

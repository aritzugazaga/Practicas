package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import analysis.AnalysisProgressCallback;
import analysis.Document;
import analysis.DocumentAnalyzer;
import database.DBManager;
import database.DBManagerException;
import loader.DirectoryLoader;
import loader.DirectoryLoaderException;

/**
 * Ventana principal del programa analizador de textos
 * @author Unai Aguilera <unai.aguilera@deusto.es>
 *
 */
public class MainWindow extends JFrame implements AnalysisProgressCallback {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JList<Document> documentList;
	private DocumentListModel listModel;
	private InfoPanel infoPanel;
	private JProgressBar progressBar;
	private JMenu analysisMenu;
	private JMenuItem startAnalysisItem;
	private JMenuItem stopAnalysisItem;
	
	private DocumentAnalyzer documentAnalyzer;
	
	public MainWindow() {
		setTitle("Text Analyzer 0.1");
		setSize(640, 480);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		listModel = new DocumentListModel();
		documentList = new JList<>(listModel);
		documentList.setEnabled(false);
		documentList.setCellRenderer(new DocumentCellRenderer());
		documentList.setFixedCellWidth(150);
		documentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane listScrollPane = new JScrollPane(documentList);
		
		JPanel mainPanel = new JPanel(new BorderLayout());		
		JPanel centerPanel = new JPanel(new BorderLayout());
		
		infoPanel = new InfoPanel();
		centerPanel.add(infoPanel, BorderLayout.NORTH);
		
		JTable dataTable = new JTable();
		dataTable.setAutoCreateRowSorter(true);
		dataTable.setModel(new DocumentTableModel());
		
		documentList.addListSelectionListener(new ListSelectionListener() {
					
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					Document document = documentList.getSelectedValue();
					infoPanel.setDocumentInfo(document);
					dataTable.setModel(new DocumentTableModel(document));
				}
			}
		});
		
		JScrollPane tableScrollPane = new JScrollPane(dataTable);
		centerPanel.add(tableScrollPane, BorderLayout.CENTER);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
				
		progressBar = new JProgressBar(0, 100);
		mainPanel.add(progressBar, BorderLayout.SOUTH);
		
		add(listScrollPane, BorderLayout.WEST);
		add(mainPanel, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem loadMenuItem = new JMenuItem("Load dir...");
		fileMenu.add(loadMenuItem);
	
		MainWindow parent = this;
		loadMenuItem.addActionListener(new ActionListener() { 
			
			@Override
			public void actionPerformed(ActionEvent event) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.setMultiSelectionEnabled(false);
				
				int returnVal = fileChooser.showOpenDialog(parent);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File selectedDir = fileChooser.getSelectedFile();
					try {
						documentList.setEnabled(false);
						progressBar.setValue(0);
						
						loadDirectory(selectedDir);
						
						if (!listModel.getDocuments().isEmpty()) {
							analysisMenu.setEnabled(true);
							startAnalysisItem.setEnabled(true);
							stopAnalysisItem.setEnabled(false);
						} else {							
							JOptionPane.showMessageDialog(
								parent, 
								"No files were loaded from selected directory", 
								"Data Loading Error", 
								JOptionPane.ERROR_MESSAGE
							);
						}
					} catch (DirectoryLoaderException e) {
						JOptionPane.showMessageDialog(
							parent, 
							"Could not load data from selected directory", 
							"Data Loading Error", 
							JOptionPane.ERROR_MESSAGE
						);
					}
				}
			}
		});
		
		analysisMenu = new JMenu("Analysis");
		menuBar.add(analysisMenu);
		analysisMenu.setEnabled(false);
		
		startAnalysisItem = new JMenuItem("Start");
		analysisMenu.add(startAnalysisItem);
		startAnalysisItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startAnalysisItem.setEnabled(false);
				stopAnalysisItem.setEnabled(true);
				documentAnalyzer = new DocumentAnalyzer(listModel.getDocuments(), parent);
				documentAnalyzer.launchAnalysis();
			}
		});
		
		stopAnalysisItem = new JMenuItem("Stop");
		stopAnalysisItem.setEnabled(false);
		analysisMenu.add(stopAnalysisItem);
		stopAnalysisItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				documentAnalyzer.stop();
				stopAnalysisItem.setEnabled(false);
			}
		});
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		
		exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		setJMenuBar(menuBar);
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				
				if (documentAnalyzer != null) {
					documentAnalyzer.stop();
				}
			}
		});
		
		setVisible(true);
	} 
	
	private void loadDirectory(File directory) throws DirectoryLoaderException {
		listModel.clear(); 
		DirectoryLoader directoryLoader = new DirectoryLoader(directory);
		List<Document> documents = directoryLoader.load();
		listModel.addAll(documents);
		infoPanel.setLoadedDocuments(documents.size());
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainWindow();
			}
			
		});
	}

	@Override
	public void setProgress(int progress) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				progressBar.setValue(progress);
			}
		});
	}

	@Override
	public void analysisFinished() {
		JFrame parent = this;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() { 
				JOptionPane.showMessageDialog(
					parent, 
					"Data correctly analysed",
					"Analysing Data", 
					JOptionPane.NO_OPTION
				);
				documentList.setEnabled(true);
				startAnalysisItem.setEnabled(true);
				stopAnalysisItem.setEnabled(false);
				
				saveDocuments(listModel.getDocuments());
			}

		});
	}
	
	@Override
	public void analysisStopped() {
		JOptionPane.showMessageDialog(
			this, 
			"Data analysis stopped",
			"Analysing Data", 
			JOptionPane.NO_OPTION
		);
		documentList.setEnabled(false);
		startAnalysisItem.setEnabled(true);
		stopAnalysisItem.setEnabled(false);
	}
	
	private void saveDocuments(List<Document> documents) { //Ejecuta lo creado en la clase DBManager
		DBManager dbManager = new DBManager();
		try {
			dbManager.connect();
			
			dbManager.createDocumentTable();
			dbManager.insertDocumentTable(documents);
			
			dbManager.disconnect();
		} catch (DBManagerException e) {
			e.printStackTrace();
		}
	}
}

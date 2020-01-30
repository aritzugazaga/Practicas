package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import orders.Order;
import orders.OrderSerializationException;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
		
	private OrderTableModel tableModel = new OrderTableModel();
	private JTable orderTable = new JTable(tableModel);
	private Order currentOrder;

	public MainWindow() {
		setSize(640, 480);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		JMenu fileMenu = new JMenu("Pedido");
		JMenuItem newOrderItem = new JMenuItem("Nuevo pedido");
		fileMenu.add(newOrderItem);
		
		JMenuItem saveOrderItem = new JMenuItem("Guardar pedido");
		fileMenu.add(saveOrderItem);
		
		JMenuItem loadOrderItem = new JMenuItem("Cargar pedido");
		fileMenu.add(loadOrderItem);
		
		JMenuItem exitItem = new JMenuItem("Salir");
		fileMenu.add(exitItem);

		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		
		setJMenuBar(menuBar);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
				
		JScrollPane tableScrollPane = new JScrollPane(orderTable);
		mainPanel.add(tableScrollPane, BorderLayout.CENTER);
		
		add(mainPanel, BorderLayout.CENTER);
		
		JPanel buttonsPanel = new JPanel();
		
		JTextField quantityField = new JTextField(10);
		quantityField.setText("0");
		quantityField.setHorizontalAlignment(SwingConstants.RIGHT);
		buttonsPanel.add(quantityField);
		
		JButton addItemButton = new JButton("Añadir item");
		buttonsPanel.add(addItemButton);
		
		JButton removeItemButton = new JButton("Eliminar item");
		buttonsPanel.add(removeItemButton);
			
		add(buttonsPanel, BorderLayout.SOUTH);
		
		createNewOrder();
		
		newOrderItem.addActionListener(new ActionListener() { 
					
			@Override
			public void actionPerformed(ActionEvent e) {
				createNewOrder();
				updateTable();
			}
		});
		
		MainWindow parent = this;
		
		saveOrderItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Ficheros binarios", "bin");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showSaveDialog(parent);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	try {
						saveOrder(currentOrder, chooser.getSelectedFile());
					} catch (OrderSerializationException e1) {
						JOptionPane.showMessageDialog(parent, "No se pudo guardar el pedido actual", "Error", JOptionPane.ERROR_MESSAGE);
					}
			    }
			}
		});
		
		loadOrderItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Ficheros binarios", "bin");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(parent);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	try {
						currentOrder = loadOrder(chooser.getSelectedFile());
						updateTable();
					} catch (OrderSerializationException e1) {
						JOptionPane.showMessageDialog(parent, "No se pudo cargar el pedido desde el fichero", "Error", JOptionPane.ERROR_MESSAGE);
					}
			    }
			}
		});
	}
	
	private void updateTable() {
		if (currentOrder != null) {
			orderTable.setModel(new OrderTableModel(currentOrder));
		}
	}
	
	private void createNewOrder() {
//		currentOrder = new MyOrder();
	}
	
	private void saveOrder(Order order, File file) throws OrderSerializationException {
		// 
	}
	
	private Order loadOrder(File file) throws OrderSerializationException {
		throw new OrderSerializationException("Not implemented"); // quitar al implementar
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MainWindow();
			}
		});

	}

}

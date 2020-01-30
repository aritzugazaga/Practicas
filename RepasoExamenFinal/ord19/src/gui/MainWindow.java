package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
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

import database.DBManager;
import orders.Item;
import orders.MyOrder;
import orders.Order;
import orders.OrderSerializationException;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private OrderTableModel tableModel = new OrderTableModel();
	private JTable orderTable = new JTable(tableModel);
	private Order currentOrder;
	private JList<Item> lista = new JList<>();

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

		JScrollPane listScrollPane = new JScrollPane(lista);
		mainPanel.add(listScrollPane, BorderLayout.WEST);
		lista.setModel(new DefaultListModel<Item>());

		JPanel buttonsPanel = new JPanel();

		JTextField quantityField = new JTextField(10);
		quantityField.setText("0");
		quantityField.setHorizontalAlignment(SwingConstants.RIGHT);
		buttonsPanel.add(quantityField);

		JButton addItemButton = new JButton("AÃ±adir item");
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

		parent.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				Connection conn = DBManager.initBD("database.db");
				Statement st = DBManager.usarBD(conn);
				List<Item> items = DBManager.loadItems(st);
				DefaultListModel<Item> model = (DefaultListModel<Item>) lista.getModel();
				for (Item item : items) {
					model.addElement(item);
				}
				DBManager.cerrarBD(conn, st);
			}

		});

		saveOrderItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Ficheros binarios", "bin");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showSaveDialog(parent);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						saveOrder(currentOrder, chooser.getSelectedFile());
					} catch (OrderSerializationException e1) {
						JOptionPane.showMessageDialog(parent, "No se pudo guardar el pedido actual", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		loadOrderItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Ficheros binarios", "bin");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(parent);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						currentOrder = loadOrder(chooser.getSelectedFile());
						updateTable();
					} catch (OrderSerializationException e1) {
						JOptionPane.showMessageDialog(parent, "No se pudo cargar el pedido desde el fichero", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		addItemButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (lista.getSelectedValue() != null) {
					Item item = lista.getSelectedValue();
					String cantidad = quantityField.getText();
					try {

						int cantidadNum = Integer.parseInt(cantidad);
						currentOrder.addItem(item, cantidadNum);
						updateTable();
					} catch (NumberFormatException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		removeItemButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (lista.getSelectedValue() != null) {
					Item item = lista.getSelectedValue();
					String cantidad = quantityField.getText();
					try {

						int cantidadNum = Integer.parseInt(cantidad);
						currentOrder.removeItem(item, cantidadNum);;
						updateTable();
					} catch (NumberFormatException ex) {
						ex.printStackTrace();
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
		currentOrder = new MyOrder();
	}

	private void saveOrder(Order order, File file) throws OrderSerializationException {
		// Guarda fichero binario
		try {
			// Output se utiliza para escribir
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(order);
			// Cerrar en orden
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// Lanzar la excepcion que pide el enunciado
			throw new OrderSerializationException("Error en la serialización del objeto", e);
		}
	}

	@SuppressWarnings("resource")
	private Order loadOrder(File file) throws OrderSerializationException {
		// Input se utiliza para escribir
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object object = ois.readObject();
			// Cerrar en orden
			ois.close();
			fis.close();
			return (Order) object;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// Lanzar la excepcion que pide el enunciado
			throw new OrderSerializationException("Error en la serialización del objeto", e);
		} catch (ClassNotFoundException e) {
			// Lanzar la excepcion que pide el enunciado
			throw new OrderSerializationException("Error en la serialización del objeto", e);
		}

		return null;

	}

	public static void main(String[] args) {
		demoFicheros();

		Connection con = DBManager.initBD("database.db");

		Statement st = DBManager.usarCrearTablasBD(con);
		boolean ok = DBManager.itemInsert(st, "a", 1, 1);
		if (!ok) {
			System.out.println("No se ha insertado item");
		} else {
			List<Item> items = DBManager.loadItems(st);
			for (Item item : items) {
				System.out.println(item);
			}
		}

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainWindow();
			}
		});
	}

	private static void demoFicheros() {
		MainWindow window = new MainWindow();
		MyOrder order = new MyOrder();
		order.addItem(new Item(1, "a", (float) 1), 1);

		// Guardar orden en fichero
		try {
			window.saveOrder(order, new File("order.bin"));
		} catch (OrderSerializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Leer orden del fichero
		try {
			Order orderLeida = window.loadOrder(new File("order.bin"));
			System.out.println(orderLeida);
		} catch (OrderSerializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

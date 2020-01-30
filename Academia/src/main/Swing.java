package main;

public class Swing {
	JScrollPane listScrollPane = new JScrollPane(lista);
	mainPanel.add(listScrollPane, BorderLayout.WEST);
	lista.setModel(new DefaultListModel<Item>());
	
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

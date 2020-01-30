package gui;

import java.util.List;
import java.util.Map.Entry;

import javax.swing.table.AbstractTableModel;

import orders.Item;
import orders.Order;

public class OrderTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnIdentifiers = {"Name", "Total"};
	
	private Order order;
	private List<Entry<Item, Integer>> entries;

	public OrderTableModel() {

	}
	
	public OrderTableModel(Order order) {
		this.order = order;
		this.entries = order.getSortedList();
	}

	@Override
	public int getColumnCount() {
		return columnIdentifiers.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnIdentifiers[column];
	}

	@Override
	public int getRowCount() {
		return order == null ? 0 : entries.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (column == 0) {
			return entries.get(row).getKey().getName();
		} else {
			return entries.get(row).getValue();
		}
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}
}

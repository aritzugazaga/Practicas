package examen.ord202001;

import static org.junit.Assert.*;
import javax.swing.JLabel;
import javax.swing.JTable;

import org.junit.Test;

public class T1 {

	// T4 
	@Test
	public void testRenderer4Cuartiles() {
	}

	// Método de utilidad: devuelve el JLabel que se está usando para hacer el render de la celda fila, col de la JTable indicada
	private JLabel getRenderer( JTable jTable, int fila, int col ) {
		return (JLabel) jTable.prepareRenderer( jTable.getCellRenderer( fila, col ), fila, col );
	}
}

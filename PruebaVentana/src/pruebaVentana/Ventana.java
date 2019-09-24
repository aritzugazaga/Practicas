package pruebaVentana;

import javax.swing.JFrame;

public class Ventana extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Ventana() {
		setTitle("Mi primera ventana");
		setSize(320,240);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		Ventana ventana = new Ventana();
		ventana.setVisible(true);
	}
}

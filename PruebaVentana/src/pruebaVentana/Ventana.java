package pruebaVentana;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Ventana extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Ventana() {
		
		boolean stop = false;
		
		JPanel bPanel = new JPanel();
		add(bPanel, BorderLayout.SOUTH);
		
		JButton button = new JButton("Click");
		bPanel.add(button);
		
		JProgressBar progressBar = new JProgressBar(0, 100);
		add(progressBar, BorderLayout.NORTH);
		
		progressBar.setValue(75);
		
		button.addActionListener(new ActionListener() {
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					for (long i = 0; i < 1000000 && !stop; i++) {
						System.out.println(i);
					}
					
				}
			});
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop = true;
				}
				
		});
		
		setTitle("Ventana 2");
		setSize(640,480);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		Ventana ventana = new Ventana();
		ventana.setVisible(true);
	}
}

package examen.ord201512.captura;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/** JLabel para gráfico, que ajusta su gráfico al tamaño del label 
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */
@SuppressWarnings("serial")
public class JLabelAjusta extends JLabel {
	private ImageIcon i;
	
	/** Construye un nuevo label partiendo de un gráfico
	 * @param i	Gráfico a incluir
	 */
	public JLabelAjusta( ImageIcon i ) {
		super( i );
		this.i = i;
	}
	
	/** Cambia el gráfico del label
	 * @param icon	Nuevo gráfico
	 */
	public void setImageIcon(ImageIcon icon) {
		i = icon;
		// setIcon(i);
		repaint();
	};
	
	@Override
	protected void paintComponent(Graphics g) {
		if (i!=null) {
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage( i.getImage(), 0, 0, getWidth(), getHeight(), null );
		}
	}
}

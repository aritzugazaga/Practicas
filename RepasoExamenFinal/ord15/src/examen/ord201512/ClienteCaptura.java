package examen.ord201512;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;

import examen.ord201512.captura.JLabelAjusta;

/** Cliente de captura de pantalla basado en Swing.
 * Tiene una ventana principal para introducir el usuario, lanzar la conexión
 * y recibir las pantallas hasta que se cierre.
 */
public class ClienteCaptura {

    private ObjectInputStream in;
    private PrintWriter out;
    private VentanaCliente v;
    private JTextField tfUsuario, tfIP, tfNumPant, tfXIni, tfYIni, tfAnchura, tfAltura;
    private JTextArea taMensajes;
    private JButton bLanzar;
    private JButton bGuardar;
    private JLabelAjusta laPantalla;
    private Socket socket;
    private boolean funcionando = false;
    private int numCapturas = 0;
    private ImageIcon ultimaCaptura = null;  // Ultima pantalla capturada
    private String nomUsuario = ""; // Usuario identificado en el cliente

    /** Construye el cliente, muestra el GUI
     */
    public ClienteCaptura() {
        try {
			SwingUtilities.invokeAndWait( new Runnable() {
				@Override
				public void run() {
			        v = new VentanaCliente( "Cliente de transmisión de captura de pantalla" );
			        v.setVisible(true);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
        bLanzar.requestFocusInWindow();
    }

    /** Lógica de conexión: se conecta al servidor y realiza la comunicación con el mismo. 
     * Recoge pantallas y las actualiza en la ventana, y se queda haciendo eso hasta que se acaba la comunicación,
     * o hasta que la ventana se cierra por parte del usuario.
     * @param ip	IP del servidor
     * @param usuario	Nombre del usuario que se conecta
     * @param numPant	Número de la pantalla que se quiere capturar
     * @param xIni	Coordenada x de la esquina superior izquierda
     * @param yIni	Coordenada y de la esquina superior izquierda
     * @param anchura	Anchura del rectángulo a capturar (-1 si se quiere capturar la pantalla completa)
     * @param altura	Altura del rectángulo a capturar (-1 si se quiere capturar la pantalla completa)
     * @throws IOException	Error producido si surge cualquier problema en la conexión
     */
    public void lanzaConexion( String ip, String usuario, int numPant, int xIni, int yIni, int anchura, int altura ) throws IOException {
        // Pide la dirección del servidor
        if (!ip.equals("")) {
			try {
	        	funcionando = true;
		        // Realiza la conexión e inicializa los flujos de comunicación
		        socket = new Socket(ip, 9898);
		        in = new ObjectInputStream( socket.getInputStream() );
		        out = new PrintWriter(socket.getOutputStream(), true);
		        // Consume el Ack (confirmación = acknowledge) del servidor
		        Object respuesta = in.readObject();
		        if (!"ACK".equals(respuesta)) throw new IOException( "Conexión errónea: respuesta del servidor inesperada: " + respuesta );
		        // Envía el usuario
		        nomUsuario = usuario;
		        out.println( nomUsuario );
		        // Espera el Ack
		        respuesta = in.readObject();
		        if (!"ACK".equals(respuesta)) throw new IOException( "Conexión errónea: " + respuesta );
		        while (funcionando) {
			        out.println( numPant + "," + xIni + "," + yIni + "," + anchura + "," + altura );
			        respuesta = in.readObject();
			        if (respuesta instanceof ImageIcon) {  // Leída captura
			        	ultimaCaptura = (ImageIcon)respuesta;
			        	laPantalla.setImageIcon( ultimaCaptura );
			        	numCapturas++;
			        } else {
						throw new IOException( "Conexión errónea: lectura de elemento incorrecto desde el servidor: " + respuesta );
			        }
		        }
		        out.println( "END" );
		        respuesta = in.readObject();
		        if (!"ACK".equals(respuesta)) throw new IOException( "Conexión errónea: respuesta del servidor inesperada: " + respuesta );
		        socket.close();
			} catch (ClassNotFoundException e) {  // Error en lectura de objeto
				throw new IOException( "Conexión errónea: lectura de elemento incorrecto desde el servidor" );
			}
        }
    }
    
    /** Guarda la última pantalla capturada, si la hay, a fichero, en formato jpg
     * @param fic	Fichero en el que guardar la pantalla (preferiblemente con extensión .jpg)
     */
    public void guardaCapturaAFichero( File fic ) {
    	Image img = ultimaCaptura.getImage();
    	BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
    	Graphics2D g2 = bi.createGraphics();
    	g2.drawImage(img, 0, 0, null);
    	g2.dispose();
    	try {
			ImageIO.write(bi, "jpg", fic );
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /** Devuelve el número de capturas de pantalla realizadas por el cliente hasta este momento
     * @return
     */
    public int getCapturas() {
    	return numCapturas;
    }

    /** Clase de ventana principal del cliente
     */
    @SuppressWarnings("serial")
	private class VentanaCliente extends JFrame {
    	public VentanaCliente( String titulo ) {
    		setTitle( titulo );
	        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
	        setSize( 1000, 750 );
	        // Componentes
	        tfUsuario = new JTextField("guest",15); tfIP = new JTextField("127.0.0.1",25);
	        tfNumPant = new JTextField("0",3); tfXIni = new JTextField("0",3); tfYIni = new JTextField("0",3);
	        tfAnchura = new JTextField("-1",5); tfAltura = new JTextField("-1",5);
	        laPantalla = new JLabelAjusta( null );
	        bLanzar = new JButton( "Lanza captura!" );
	        bGuardar = new JButton( "Guardar JPG" );
	        taMensajes = new JTextArea(8, 60);
	        taMensajes.setEditable(false);
	        JPanel pSuperior = new JPanel();
	        JPanel pSuperior1 = new JPanel();
	        JPanel pSuperior2 = new JPanel();
	        JPanel pSuperior3 = new JPanel();
	        pSuperior1.add( new JLabel("Introduce dirección IP del servidor (127.0.0.1 misma máquina):" ));
	        pSuperior1.add( tfIP );
	        pSuperior2.add( new JLabel( "Introduce usuario:" ) );
	        pSuperior2.add( tfUsuario );
	        pSuperior2.add( bLanzar ); pSuperior2.add( bGuardar );
	        pSuperior3.add( new JLabel( "Nº pantalla" ) );
	        pSuperior3.add( tfNumPant );
	        pSuperior3.add( new JLabel( " - X:" ) );
	        pSuperior3.add( tfXIni );
	        pSuperior3.add( new JLabel( "Y:" ) );
	        pSuperior3.add( tfYIni );
	        pSuperior3.add( new JLabel( "Ancho:" ) );
	        pSuperior3.add( tfAnchura );
	        pSuperior3.add( new JLabel( "Alto" ) );
	        pSuperior3.add( tfAltura );
	        pSuperior.setLayout( new BoxLayout( pSuperior, BoxLayout.Y_AXIS ));
	        pSuperior.add( pSuperior1); pSuperior.add( pSuperior2 ); pSuperior.add( pSuperior3 );
	        getContentPane().add( pSuperior, BorderLayout.NORTH );
	        getContentPane().add( laPantalla, BorderLayout.CENTER );
	        getContentPane().add( new JScrollPane(taMensajes), BorderLayout.SOUTH );
	        // Escuchadores
    		addWindowListener( new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					ClienteCaptura.this.fin();
				}
			} );
    		bLanzar.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					bLanzar.setEnabled( false );
					Thread t = new Thread() {
						@Override
						public void run() {
					        try {
					        	int nP = 0; int x = 0; int y = 0; int anc = -1; int alt = -1;
					        	try {
					        		nP = Integer.parseInt(tfNumPant.getText());
					        		x = Integer.parseInt(tfXIni.getText());
					        		y = Integer.parseInt(tfYIni.getText());
					        		anc = Integer.parseInt(tfAnchura.getText());
					        		alt = Integer.parseInt(tfAltura.getText());
					        	} catch (NumberFormatException e) {}
								lanzaConexion( tfIP.getText(), tfUsuario.getText(), nP, x, y, anc, alt );
							} catch (IOException e) {
								mensaje( "ERROR: " + e.getMessage() );
								mensaje( "Cerrando por error en conexión con servidor..." );
								try { Thread.sleep(3000); } catch (InterruptedException e1) {}
								fin();
							}
						}
					};
					t.start();
				}
			});
    		try {
				flogCapturasGuardadas = new PrintStream(new FileOutputStream("capturasguardadas.txt", true));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		bGuardar.addActionListener(new ActionListener() {
				// Para saber la captura que numero es
    			private int numero = 0;
    			// Para saber la fecha
    			private SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if (ultimaCaptura != null) {
						File fJpg = null;
						
						do {
							numero++;
							fJpg = new File("captura" + String.format("%04d", numero)+ ".jpg");
						} while (fJpg.exists());
						guardaCapturaAFichero(fJpg);
						
						if(flogCapturasGuardadas != null)
							flogCapturasGuardadas.println(nomUsuario + "\t" + f.format(new Date()) + "\t" + fJpg.getName());
					}
				}
			});
    	}
    }
    
    PrintStream flogCapturasGuardadas = null;

    /** Cierra el cliente
     */
    public void fin() {
    	if (funcionando)
    		funcionando = false;
    	else {
			try {
		    	if (socket!=null) socket.close();
			} catch (IOException e) { }
    	}
		try { Thread.sleep(2000); } catch (InterruptedException e1) {}
    	if (v!=null) v.dispose();
    }
    
    /** Visualiza mensaje
     * @param mens	Mensaje a visualizar, en consola y en la ventana si existe
     */
    private void mensaje( String mens ) {
    	System.out.println( mens );
    	if (v!=null) {
    		taMensajes.append( mens + "\n" );
    		if (taMensajes.getLineCount()>200) {  // Elimina líneas si hay demasiadas
    			try {
					taMensajes.select( taMensajes.getLineStartOffset(0), taMensajes.getLineStartOffset(100) );
					taMensajes.replaceSelection( "" );
				} catch (BadLocationException e) {}
    		}
    		taMensajes.select( taMensajes.getText().length(), taMensajes.getText().length() );  // Se pone al final del textarea
    	}
    }

    /**
     * Ejecuta la aplicación cliente
     */
    public static void main(String[] args) {
        new ClienteCaptura();
    }
    
}
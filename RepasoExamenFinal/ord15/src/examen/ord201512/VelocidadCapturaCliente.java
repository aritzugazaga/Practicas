package examen.ord201512;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import javax.swing.*;
import javax.swing.text.BadLocationException;

import examen.ord201512.captura.JLabelAjusta;

/** Cliente de captura de pantalla basado en Swing.
 * Lanza conexiones con usuario e IPs definidos en constantes globales
 * y recibir capturas de pantallas hasta que se cierre,
 * indicando la velocidad de refresco en número de gráficos por segundo.
 */
public class VelocidadCapturaCliente {

    private static final String NOM_USUARIO = "guest"; // Usuario identificado para el test de velocidad
    private static final String IP = "127.0.0.1";      // IP para el test de velocidad
    private ObjectInputStream in;
    private PrintWriter out;
    private VentanaCliente v;
    private JTextArea taMensajes;
    private JLabelAjusta laPantalla;
    private Socket socket;
    private boolean funcionando = false;
    private int numCapturas = 0;
    private ImageIcon ultimaCaptura = null;  // Ultima pantalla capturada

    /** Construye el cliente, muestra el GUI
     */
    public VelocidadCapturaCliente() {
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
    }

    /** Lógica de conexión: se conecta al servidor y realiza la comunicación con el mismo. 
     * Recoge pantallas y las actualiza en la ventana, y se queda haciendo eso en el número de capturas indicadas
     * o hasta que la ventana se cierra por parte del usuario.
     * @param numMaxCapturas	Número de capturas que se hacen
     * @param ip	IP del servidor
     * @param usuario	Nombre del usuario que se conecta
     * @param numPant	Número de la pantalla que se quiere capturar
     * @param xIni	Coordenada x de la esquina superior izquierda
     * @param yIni	Coordenada y de la esquina superior izquierda
     * @param anchura	Anchura del rectángulo a capturar (-1 si se quiere capturar la pantalla completa)
     * @param altura	Altura del rectángulo a capturar (-1 si se quiere capturar la pantalla completa)
     * @return	Milisegundos empleados en la comunicación de las capturas, -1 si hay error
     * @throws IOException	Error producido si surge cualquier problema en la conexión
     */
    public long lanzaConexionTest( int numMaxCapturas, String ip, String usuario, int numPant, int xIni, int yIni, int anchura, int altura ) throws IOException {
    	long tiempo;
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
		        out.println( usuario );
		        // Espera el Ack
		        respuesta = in.readObject();
		        if (!"ACK".equals(respuesta)) throw new IOException( "Conexión errónea: " + respuesta );
		        tiempo = System.currentTimeMillis(); 
		        while (funcionando && numMaxCapturas>0) {
			        out.println( numPant + "," + xIni + "," + yIni + "," + anchura + "," + altura );
			        respuesta = in.readObject();
			        if (respuesta instanceof ImageIcon) {  // Leída captura
			        	ultimaCaptura = (ImageIcon)respuesta;
			        	laPantalla.setImageIcon( ultimaCaptura );
			        	numCapturas++;
			        } else {
						throw new IOException( "Conexión errónea: lectura de elemento incorrecto desde el servidor: " + respuesta );
			        }
			        numMaxCapturas--;
		        }
		        tiempo = System.currentTimeMillis() - tiempo;
		        out.println( "END" );
		        respuesta = in.readObject();
		        if (!"ACK".equals(respuesta)) throw new IOException( "Conexión errónea: respuesta del servidor inesperada: " + respuesta );
		        socket.close();
		        return tiempo;
			} catch (ClassNotFoundException e) {  // Error en lectura de objeto
				throw new IOException( "Conexión errónea: lectura de elemento incorrecto desde el servidor" );
			}
        }
        return -1; // Error
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
	        laPantalla = new JLabelAjusta( null );
	        taMensajes = new JTextArea(8, 60);
	        taMensajes.setEditable(false);
	        getContentPane().add( laPantalla, BorderLayout.CENTER );
	        getContentPane().add( new JScrollPane(taMensajes), BorderLayout.SOUTH );
	        // Escuchadores
    		addWindowListener( new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					VelocidadCapturaCliente.this.fin(true);
				}
			} );
    	}
    }

    /** Cierra el cliente
     */
    public void fin(boolean cerrarVentana) {
    	if (funcionando)
    		funcionando = false;
    	else {
			try {
		    	if (socket!=null) socket.close();
			} catch (IOException e) { }
    	}
		try { Thread.sleep(2000); } catch (InterruptedException e1) {}
    	if (cerrarVentana && v!=null) v.dispose();
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

    private static void testVelocidad() {
		long tiempo = 0;
		int anchuraTest = 0;
		int alturaTest = 0;
        vcc = new VelocidadCapturaCliente();
        try {
    		// Prueba de 100 capturas de 1000x750
        	anchuraTest = 1000; alturaTest = 750;
        	tiempo = vcc.lanzaConexionTest( 100, IP, NOM_USUARIO, 0, 0, 0, anchuraTest, alturaTest );
    		vcc.fin(false);  // Acaba pero no cierra aún la ventana 
    		vcc.mensaje( "Velocidad de transferencia de " + anchuraTest + "x" + alturaTest + " = " + 100.0 / tiempo * 1000 + " gráficos/segundo." );
    		// Fin prueba 100 capturas
    		
    		// Prueba de 100 capturas de 10x10
        	anchuraTest = 10; alturaTest = 10;
        	tiempo = vcc.lanzaConexionTest( 100, IP, NOM_USUARIO, 0, 0, 0, anchuraTest, alturaTest );
    		vcc.mensaje( "Velocidad de transferencia de " + anchuraTest + "x" + alturaTest + " = " + 100.0 / tiempo * 1000 + " gráficos/segundo." );
    		try { Thread.sleep(3000); } catch (InterruptedException e1) {}
    		vcc.fin(true);  // Acaba cerrando ya la ventana 
    		// Fin prueba 100 capturas
    		
		} catch (IOException e) {
			vcc.mensaje( "ERROR: " + e.getMessage() );
			vcc.mensaje( "Cerrando por error en conexión con servidor..." );
			try { Thread.sleep(3000); } catch (InterruptedException e1) {}
		}
    }
    
    	private static VelocidadCapturaCliente vcc = null;
    /**
     * Ejecuta la aplicación cliente y testea la velocidad
     */
    public static void main(String[] args) {
        testVelocidad();
    }
    
}
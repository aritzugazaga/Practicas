package examen.ord201512;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import examen.ord201512.captura.CapturaPantalla;

import java.awt.*;
import java.awt.event.*;

/**
 * Programa servidor que acepta peticiones de clientes para recibir capturas de pantalla.
 * Cuando un cliente se conecta, se inicia un hilo para gestionar un diálogo con el
 * cliente en el cual el cliente solicita una captura, y el servidor se la entrega.
 */
public class ServidorCaptura {

	private VentanaCaptura v = null;
    private int numCliente = 0;  // Número correlativo de cliente conectado
    private ServerSocket servidor = null;  // servidor de sockets
    private boolean funcionando = false;  // Lógica de funcionamiento del servidor
    private ArrayList<Conexion> listaClientesActivos = new ArrayList<Conexion>();  // Lista de clientes activos

    /** Crea un servidor de captura
     */
    public ServidorCaptura() {
    }

    /** Activa la ventana visual del servidor
     */
    public void activaVentana() {
    	v = new VentanaCaptura();
    	v.setVisible( true );
    }
    
    /** Inicia el servidor en un hilo independiente. A partir de este punto el servidor 
     * se queda activo en ese hilo, escuchando a todos los clientes que se conecten,
     * hasta que se cierre con {@link #fin()} 
     */
    public void inicio() {
    	(new Thread() {
    		@Override
    		public void run() {
    	        try {
    	        	funcionando = true;
    	        	servidor = new ServerSocket(9898);
    	        	while (funcionando) {
    	                Conexion con = new Conexion( servidor.accept(), numCliente++ );
    	                con.start();
    	                listaClientesActivos.add( con );
    	            }
    	        } catch (Exception e) {
    	        } finally {
    	            try {
    	            	if (servidor!=null) servidor.close();
    				} catch (IOException e) {}
    	        }
    		}
    	}).start();
    }
    
    /** Cierra el servidor
     */
    public void fin() {
    	funcionando = false;
    	if (servidor!=null)
	     	try {
				servidor.close();  // Cierra el servidor abierto
			} catch (IOException e) {}  
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
    		taMensajes.select( taMensajes.getText().length(), taMensajes.getText().length() );
    	}
    }

    /** Hilo para gestionar peticiones de cliente de capturas de pantalla
     */
    private class Conexion extends Thread {
        private Socket socket;
        private int numCliente;

        public Conexion(Socket socket, int numCliente) {
            this.socket = socket;
            this.numCliente = numCliente;
            mensaje( "Nueva conexión con cliente #" + numCliente + " en puerto " + socket );
        }

        /** Método de servicio. Envía mensaje acknowledge 
         * lee mensajes con la información de captura y luego envía la pantalla capturada
         */
        public void run() {
        	String usuario = "";
            try {
            	listaClientesActivos.add( this );  // Añade la conexión a la lista de clientes activos
            	// Streams para recibir caracteres y no solo bytes, y para enviar objetos
                BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()) );
                ObjectOutputStream out = new ObjectOutputStream( socket.getOutputStream() ); 
                // Envía mensaje de comunicación establecida
                out.writeObject( "ACK" );
                // Recibe nombre de usuario del cliente
                usuario = in.readLine();
                mensaje( "Cliente #" + numCliente + " conectado con el usuario " + usuario );
                out.writeObject( "ACK" );
                // Toma mensajes del cliente, línea a línea. Cada línea es la información de la pantalla
                // que debe ser capturada, con el formato numPant,xIni,yIni,anch,alt  (todo enteros válidos)
                // Si el mensaje es nulo o "END" se acaba el proceso.
    	        while (funcionando) {
                    String input = in.readLine();
                    mensaje( "Comando recibido de cliente #" + numCliente + ": " + input );
                    if (input == null || input.equals("END")) {
                        out.writeObject( "ACK" );
                        break;
                    } else {
                    	int numP = 0; int iniX = 0; int iniY = 0; int anch = -1; int alt = -1;
                    	try {
                    		StringTokenizer st = new StringTokenizer( input, "," );
                    		numP = Integer.parseInt( st.nextToken() );
                    		iniX = Integer.parseInt( st.nextToken() );
                    		iniY = Integer.parseInt( st.nextToken() );
                    		anch = Integer.parseInt( st.nextToken() );
                    		alt = Integer.parseInt( st.nextToken() );
                    	} catch (Exception e) { }
                    	// Realizar captura y enviársela al cliente
                    	if (anch==-1 || alt==-1)
                    		out.writeObject( new ImageIcon( CapturaPantalla.capturaPantalla(0) ) );
                    	else
                    		out.writeObject( new ImageIcon( CapturaPantalla.capturaPantalla(numP,iniX,iniY,anch,alt) ) );
                        mensaje( "  Enviada imagen a cliente #" + numCliente );
                        out.reset();  // Evita que se almacenen todas las referencias en memoria (eventualmente heap overflow)
                    }
                }
            } catch (IOException e) {
            	e.printStackTrace( System.out );
            	mensaje( "Error en el cliente #" + numCliente + ": " + e );
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                	mensaje( "Comunicación con cliente " + numCliente + " no ha podido cerrarse correctamente." );
                }
                mensaje( "Conexión con cliente " + numCliente + " cerrada." );
            }
        	listaClientesActivos.remove( this );  // Quita a la conexión de la lista de clientes activos
        }
    }
    
    

    private JTextArea taMensajes = null;
    /** Clase de ventana visual del servidor
     */
    @SuppressWarnings("serial")
	private class VentanaCaptura extends JFrame {
    	public VentanaCaptura() {
    		setTitle( "Captura de pantalla - Servidor" );
    		setSize( 600, 400 );
    		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
    		// Componentes
    		taMensajes = new JTextArea( 8, 10 );
	        taMensajes.setEditable(false);
    		getContentPane().add( new JScrollPane(taMensajes), BorderLayout.SOUTH );
    		// Escuchadores
    		addWindowListener( new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					ServidorCaptura.this.fin();
				}
			} );
    	}
    }
    
    /** Ejecuta el servidor, que se ejecuta hasta que se cierra la ventana.
     * El servidor escucha el puerto de comunicaciones 9898
     * y espera a clientes que piden conexión. 
     */
    public static void main(String[] args) {
    	ServidorCaptura s = new ServidorCaptura();
        s.inicio();
        s.activaVentana();
        try { Thread.sleep(10); } catch (InterruptedException e) {}
        s.mensaje( "Servidor de captura funcionando." );
    }
    
}


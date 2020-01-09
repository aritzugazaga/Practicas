package examen.ord201512;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ClienteCapturaTest {
	
	private ClienteCaptura c1, c2;
	
	@Test
	public void testLanzaConexion() {
		ServidorCaptura servidorCaptura = new ServidorCaptura();
		
		servidorCaptura.inicio();
		
		try {
			Thread.sleep(500); //Para esperar 500 milisegundos
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		c1 = new ClienteCaptura();
		c2 = new ClienteCaptura();	
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					c1.lanzaConexion("127.0.0.1", "test1", 0, 0, 0, -1, -1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// 0, 0, 0, -1, -1 es para la pantalla completa
				
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					c2.lanzaConexion("127.0.0.1", "test2", 0, 0, 0, -1, -1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Cerrar los dos clientes y el servidor
		c1.fin();
		c2.fin();
		servidorCaptura.fin();
		
		assertTrue(c1.getCapturas() >= 10);
		assertTrue(c2.getCapturas() >= 10);
	}

}

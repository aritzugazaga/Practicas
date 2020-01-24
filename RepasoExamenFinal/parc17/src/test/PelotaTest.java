package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import examen.parc201711.Pelota;
import examen.parc201711.utils.VentanaGrafica;

public class PelotaTest {

	@Test
	public void testCaidaPelota() {
		// TODO TAREA 1 - Chequear caida vertical
		VentanaGrafica vg = new VentanaGrafica(100, 100, "Test");
		Pelota pelota1 = new Pelota();
		Pelota pelota2 = new Pelota();
		pelota1.setY(0);
		pelota2.setY(0);
		double alturaAuxiliar = pelota1.getY();
		for (int i = 0; i < 100; i++) {
			pelota1.mueveUnPoco(vg, 1, false);
			// Comprobar que la vaya creciendo la velocidad cada vez que recorre el for
			assertTrue(alturaAuxiliar < pelota1.getY());
			alturaAuxiliar = pelota1.getY();
		}
		pelota2.mueveUnPoco(vg, 100, false);
		
		assertEquals(pelota1.getY(), pelota2.getY(), 0.5);
	}
	@Test
	public void testSubidaYCaidaPelota() {
		// TODO TAREA 1 - Chequear subida y caída
		VentanaGrafica vg = new VentanaGrafica(100, 100, "Test");
		Pelota pelota = new Pelota();
		pelota.setVelocidad(0, -20000);
		pelota.setY(0);
		double velocidadAuxiliar = pelota.getVelocidadY();
		int contador = 0;
		while (pelota.getVelocidadY() <= 20000) {
			pelota.mueveUnPoco(vg, 1, false);
			assertTrue("La velocidad tiene que cambiar", velocidadAuxiliar < pelota.getVelocidadY());
			velocidadAuxiliar = pelota.getVelocidadY();
			
			if (pelota.getVelocidadY() <= 0) {
				contador++;
			} else {
				contador--;
			}
		}
		assertEquals(pelota.getVelocidadY(), 20000, 5);
		assertEquals(0, pelota.getY(), 10);
		assertEquals(0, contador, 2);
		
	}

}

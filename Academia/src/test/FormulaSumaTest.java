package test;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import examen.ext201806.datos.FormulaSuma;
import examen.ext201806.datos.Numero;
import examen.ext201806.datos.RefCelda;
import examen.ext201806.datos.TablaDatos;
import examen.ext201806.datos.Texto;
import examen.ext201806.datos.ValorCelda;

public class FormulaSumaTest {
	private static TablaDatos tabla;
	private static double[] randomNumbers;
	
	@BeforeClass
	public static void inicializarClase() {
		randomNumbers = new double[3];
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < randomNumbers.length; i++) {
			randomNumbers[i] = r.nextDouble();
		}
		
		tabla = new TablaDatos(10, 10);
		tabla.set(1, 1, new Numero(randomNumbers[0]));
		tabla.set(1, 2, new Numero(randomNumbers[1]));
		tabla.set(1, 3, new Numero(randomNumbers[2]));
		tabla.set(2, 3, new FormulaSuma(new RefCelda(1, 1), new RefCelda(1, 3), tabla));
	}
	
	@Test
	public void formulaSumaTest() {
		// Test 1
		//double suma = randomNumbers[0] + randomNumbers[2] + randomNumbers[3];
		double suma = 0;
		for (double d : randomNumbers) {
			suma += d;
		}
		ValorCelda celda =  tabla.get(2, 3);
		FormulaSuma fs = (FormulaSuma) celda;
		if (celda instanceof FormulaSuma) {
			assertEquals(suma, fs.getValor(), 0);
		} else {
			fail();
		}
		
		// Test 2
		tabla.set(1, 2, new Texto("Hola"));
		fs.recalculaValor();
		assertEquals(randomNumbers[0] + randomNumbers[2], fs.getValor(), 0);
		
		// Test 3
		// Opcion 1
		fs.actualizaTexto("=SUMA(B2:F2)");
		assertEquals(randomNumbers[0] + randomNumbers[2], fs.getValor(), 0);
		
		// Opcion 2
		
	}

}

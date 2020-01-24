package test;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import examen.ext201806.datos.FormulaSuma;
import examen.ext201806.datos.Numero;
import examen.ext201806.datos.RefCelda;
import examen.ext201806.datos.TablaDatos;

public class FormulaSumaTest {
	private static TablaDatos tablaDatos;
	private static double[] randomNumbers;
	
	@BeforeClass
	public static void inicializarClase() {
		randomNumbers = new double[3];
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < randomNumbers.length; i++) {
			randomNumbers[i] = r.nextDouble();
		}
		
		tablaDatos = new TablaDatos(10, 10);
		tablaDatos.set(1, 1, new Numero(randomNumbers[0]));
		tablaDatos.set(1, 1, new Numero(randomNumbers[1]));
		tablaDatos.set(1, 1, new Numero(randomNumbers[2]));
		tablaDatos.set(2, 3, new FormulaSuma(new RefCelda(1, 1), new RefCelda(1, 3), tablaDatos));
	}
	
	@Test
	public void test() {
		
	}

}

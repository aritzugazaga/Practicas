import static org.junit.Assert.*;

import org.junit.Test;

import jUnit.CalculadoraImpl;

public class CalculadoraImplTest {

	@Test
	public void testSumar() {
		CalculadoraImpl c = new CalculadoraImpl();
		assertEquals(5, c.Sumar(2, 3));
	}
	
	@Test
	public void testRestar() {
		CalculadoraImpl c = new CalculadoraImpl();
		assertEquals(1, c.Restar(3, 2));
	}
	
	@Test
	public void testMultiplicar() {
		CalculadoraImpl c = new CalculadoraImpl();
		assertEquals(6, c.Multiplicar(2, 3));
	}
	
	@Test
	public void testEsPar() {
		CalculadoraImpl c = new CalculadoraImpl();
		assertEquals(false, c.esPar(5));
		assertEquals(true, c.esPar(4));
	}
}

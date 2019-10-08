import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import jUnit.CalculadoraImpl;

public class CalculadoraImplTest {
	
	private CalculadoraImpl c;
	
	@Before
	public void setUp() {
		c = new CalculadoraImpl();
	}

	@Test
	public void testSumar() {
		assertEquals(5, c.Sumar(2, 3));
	}
	
	@Test
	public void testRestar() {
		assertEquals(1, c.Restar(3, 2));
	}
	
	@Test
	public void testMultiplicar() {
		assertEquals(6, c.Multiplicar(2, 3));
	}
	
	@Test
	public void testEsPar() {
		assertEquals(false, c.esPar(5));
		assertEquals(true, c.esPar(4));
	}
}

package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import academia.Calculadora;

public class CalculadoraTest {
	private static Calculadora calculadora;
	
	@BeforeClass
	public static void inicializarClase() {
		calculadora = new Calculadora();
	}
	
	@Before
	public void inicializarMetodo() {
		// Crear datos en BDD o Fichero
	}
	
	@AfterClass
	public static void disposearClase() {
		calculadora = null;
	}
	
	@After
	public void borrarDatosMetodo() {
		// Borra datos en BDD o Fichero
	}
	
	@Test
	public void shouldReturnPossitiveResult() {
		double result = Calculadora.dividir(2, 4);
		assertEquals(0.5, result, 0.001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentException() {
		Calculadora.dividir(2, 0);
	}

}

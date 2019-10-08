package testPersona;
import static org.junit.Assert.*;

import org.junit.Test;

import persona.Persona;

public class PersonaTest {

	@Test
	public void testGetNombre() {
		Persona p = new Persona("Unai", "Aguilera");
		assertEquals("Unai", p.getNombre());
	}
	
	@Test
	public void testGetApellido() {
		Persona p = new Persona("Unai", "Aguilera");
		assertEquals("Aguilera", p.getApellido());
	}
	
	@Test
	public void testGetNombreCompleto() {
		Persona p = new Persona("Unai", "Aguilera");
		assertEquals("Aguilera, Unai", p.getNombreCompleto());
	}

}

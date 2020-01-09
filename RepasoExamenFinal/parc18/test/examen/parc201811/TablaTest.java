package examen.parc201811;

import static org.junit.Assert.*;

import org.junit.Test;

public class TablaTest {
	private String[][] datosTest1 = {
			{"1", "1", "1", "1"},
			{"2", "2", "2", "2"},
			{"4", "4", "4", "4"},
			{"6", "6", "6", "6"},
	};
	
	@Test
	public void test() {
		try {
			Tabla tabla = Tabla.processCSV(TablaTest.class.getResource("testTabla1.csv").toURI().toURL());
			assertTrue(tablaOk(tabla, datosTest1));
			tabla = Tabla.processCSV(TablaTest.class.getResource("testTabla2.csv").toURI().toURL());
			System.out.println(tabla);
			assertTrue(tablaOk(tabla, datosTest1));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Excepción no esperada.");
		}
	}
	
	private boolean tablaOk(Tabla tabla, String[][] datos) {
		assertEquals(tabla.size(), datos.length);
		assertEquals(tabla.getWidth(), datos[0].length);
		
		try { // Si no hay fallos (que la tabla y la matriz sean de las mismas medidas)
			for (int fila = 0; fila < tabla.size(); fila++) { //Recorrer la tabla por lineas
				for (int col = 0; col < tabla.getWidth(); col++) { // Recorre la tabla por columnas
					if (!tabla.get(fila, col).equals(datos[fila][col])) {
						return false; //Si no son iguales los datos nos devuelve false
					}
				}
			}
			return true;
		} catch (IndexOutOfBoundsException e) { //Si hay algun fallo
			e.printStackTrace();
			return false;
		}
	}

}

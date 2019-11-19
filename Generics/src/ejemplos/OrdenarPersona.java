package ejemplos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrdenarPersona {

	public static void main(String[] args) {
		List<Persona> personas = new ArrayList<Persona>();
		
		personas.add(new Persona("Juan", "Garcia", 20));
		personas.add(new Persona("AAAA", "CCCC", 36));
		personas.add(new Persona("BBBB", "DDDD", 17));
		
		System.out.println("Orden Original: ");
		imprimirLista(personas);
		
		//Collections.sort(personas);
		
		Collections.sort(personas, new ComparadorPersonaApellido());
		
		System.out.println("Despues de ordenar por Apellido: ");
		imprimirLista(personas);
		
		Collections.sort(personas, new ComparadorPersonaEdad());
		
		System.out.println("Despues de ordenar por Edad: ");
		imprimirLista(personas);
	}
public static void imprimirLista(List<Persona> personas) {
		for (Persona p : personas) {
			System.out.println(p);
		}
	}

}

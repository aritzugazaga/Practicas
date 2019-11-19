package ejemplos;

import java.util.Comparator;

public class ComparadorPersonaEdad implements Comparator<Persona> {

	@Override
	public int compare(Persona p1, Persona p2) {
		if (p1.getEdad() < p2.getEdad())
			return -1;
					
		if (p1.getEdad() == p2.getEdad())
			return 0;
		
		return 1;
	}
	
}

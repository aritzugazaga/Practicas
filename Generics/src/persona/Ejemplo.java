package persona;

import java.util.ArrayList;
import java.util.List;

public class Ejemplo {
	
	public static void getNombreApellido(List<? extends Persona> ps) {
		for (Persona persona : ps) {
			System.out.println(persona.getApellido() + ", " + persona.getNombre());
		}
		
	}

	public static void main(String[] args) {

		Alumno a = new Alumno();
		Persona p = new Persona();
		
		//Se puede hacer lo siguinete, debido a la herencia
		Persona b = new Alumno();
		
		List<Alumno> alumnos = new ArrayList<Alumno>();
		List<Persona> personas = new ArrayList<Persona>();
		
		List<? extends Persona> ps = alumnos;
		
		//Metemos los datos
		getNombreApellido(personas);
		getNombreApellido(alumnos);
	}

}

package ejemplos;

public class Persona implements Comparable<Persona>{
	
	private String nombre;
	private String apellido;
	private int edad;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Persona(String nombre, String apellido, int edad) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + "]";
	}
	
	//Para ordenar Descendente por edad
//	@Override
//	public int compareTo(Persona p) {
//		if (this.edad > p.edad)
//			return -1;
//					
//		if (this.edad == p.edad)
//			return 0;
//		
//		return 1;
//	}
	
	//Para ordenar Ascendente por apellido
	@Override
	public int compareTo(Persona p) {
		//String ya implementa compareTo con otros strings
		return this.apellido.compareTo(p.apellido);
	}
	
}

package ejemplos;

import java.util.HashMap;
import java.util.Map;

public class CompararPersona {
	public static void main(String[] args) {
		Persona a = new Persona("Nombre 1", "Apellido1", 20);
		Persona b = new Persona("Nombre 2", "Apellido2", 25);
		Persona c = new Persona("Nombre 1", "Apellido1", 20);
		
		
		//Comparacion de instancias en memoria
		
		System.out.println(" a == a " + (a == a));  //TRUE
		System.out.println(" a == b " + (a == b));  //FALSE
		System.out.println(" a == c " + (a == c));  //FASLE
		
		//Equals hace lo mismo a no ser que definamos nosotros el metodo
		//hemos definido un equals(Object o) en Persona...
		
		System.out.println("a.equals(a):" + a.equals(a)); //TRUE
		System.out.println("a.equals(a):" + a.equals(b)); //FALSE
		System.out.println("a.equals(a):" + a.equals(c)); //TRUE
		
		System.out.println("a.equals(null):" + a.equals(null));
		
		System.out.println("a.hashCode(): " + a.hashCode());  //1259475182
		System.out.println("b.hashCode(): " + b.hashCode());  //1300109446
		System.out.println("c.hashCode(): " + c.hashCode());  //1020371697
		
		Map<Persona, Integer> notas = new HashMap<>();
		notas.put(a, 7);
		notas.put(b, 8);
		
		System.out.println(notas.get(a));
		System.out.println(notas.get(b));
		System.out.println(notas.get(c));

	}

}


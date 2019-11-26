package ejemplos;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class EjemploTreeMap {

	public static void main(String[] args) {
		
		// Se ordena por el orden natural de la clave
		Map<String, Integer> poblaciones = new TreeMap<>();
		poblaciones.put("Bilbao", 100000);
		poblaciones.put("vitorioa", 50000);
	}

}

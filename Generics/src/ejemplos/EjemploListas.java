package ejemplos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EjemploListas {

	public static void main(String[] args) {

		//Poner siempre List para definirlo, para poder cambiar el tipo de lisyta más rapido
		List<Integer> enteros = new ArrayList<Integer>();
		
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			int n = r.nextInt();
			enteros.add(n);
		}
		
		imprimirLista(enteros);
		
		//si quiereo que la clase sea ordenable tiene que implementar comparable		
		//sort de la clase collections
		Collections.sort(enteros);
		
		imprimirLista(enteros);
	}
	
	public static void imprimirLista(List<Integer> enteros) {
		for (Integer e : enteros) {
			System.out.println(e + " ");
		}
		System.out.println();
	}

}

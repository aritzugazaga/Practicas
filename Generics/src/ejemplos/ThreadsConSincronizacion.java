package ejemplos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadsConSincronizacion {
	public static void main(String[] args) {
		List<Integer> enteros = new ArrayList<>();
		enteros.add(8);
		enteros.add(20);
		enteros.add(2);
		
		imprimir(enteros);
		
		Collections.shuffle(enteros);
		
		imprimir(enteros);
		
		Collections.fill(enteros, 0);
		
		imprimir(enteros);
		
		Collections.swap(enteros, 0, 2);
		
		imprimir(enteros);
		
		Collections.reverse(enteros);
		
		imprimir(enteros);
		
		
		/*
		 Cuando creamos la otraLista es la misma lista que la de enteros porque no hemos echo un new del objeto
		 */
		System.out.println(enteros.size());
		List<Integer> otraLista = enteros;
		otraLista.add(2);
		otraLista.addAll(enteros);
		System.out.println(enteros.size());
		
		/*
		 Podemos crear otra Lista (Lista3) que referencie los mismos objetos que en enteros
		 */
		List<Integer> lista3 = new ArrayList<>(enteros);
		for (Integer integer : lista3) {
			System.out.println(integer);
		}
		
		//Crear Lista que no se puede modificar
		List<Integer> noModificable = Collections.unmodifiableList(enteros);
		noModificable.add(6);
		for (Integer integer : noModificable) {
			System.out.println(integer);
		}
		
		
	}
	
	/*
	Metodo para representar una lista vacia y que no cree un nuevo objeto 
	(no se ha encontrado nada en la base de datos)
	SI SE QUIERE AÑADIR ELEMENTOS NO USAR ESTO
	*/
	public List<Integer> crearListaVacia(){
		return Collections.emptyList();
	}
	
	public static void imprimir(List<Integer> enteros) {
		for (Integer integer : enteros) {
			System.out.println(integer);
		}
	}

}

package ejemplos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

public class EjemploPila {

	public static void main(String[] args) {
		/*
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(3);
		stack.push(2);
		stack.push(5);
		
		while(!stack.isEmpty()) {
			System.out.println(stack.pop());
		}
		*/
		
		List<Integer> enteros = new ArrayList<Integer>();
		
		enteros.add(1);
		enteros.add(3);
		enteros.add(7);
		
		imprimir(enteros);		
		darLaVuelta(enteros);
		
		
		Map<String, Integer> poblaciones = new HashMap<>();
		poblaciones.put("Bilbao", 100000);
		poblaciones.put("vitorioa", 50000);
		poblaciones.put("Madrid", 9999);
		
		//Comprobar que el hashmap contiene bilbao
		if(poblaciones.containsKey("Bilbao")) {
			System.out.println("Bilbao EXISTE!!!");
		}
		
		
		//Recorrer hashMap con las dos propiedades
		for (Entry<String, Integer> entry : poblaciones.entrySet()) {
			System.out.println("Ciudad = "+ entry.getKey() + ", Poblacion = " + entry.getValue());
		}
		
	}
	
	
	
	public static void darLaVuelta(List<Integer> integers) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.addAll(integers);
		while(!stack.isEmpty()) {
			System.out.println(stack.pop());
		}
	}
	
	public static void imprimir(List<Integer> integers) {
		for (Integer integer : integers) {
			System.out.println(integer);
		}
	}
	
}

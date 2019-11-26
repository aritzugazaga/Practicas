package ejemplos;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CompararCodigoPostal {
	
	public static void main(String[] args) {
		Map<CodigoPostal, Integer> numPersonas = new HashMap<CodigoPostal, Integer>();
		
		numPersonas.put(new CodigoPostal(40000), 5000);
		numPersonas.put(new CodigoPostal(40001), 300);
		
		imprimir(numPersonas);
	}
	
	
	public static void imprimir(Map<CodigoPostal, Integer> numPersonas) {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Introduce el codigo postal:");
			int codPostal = sc.nextInt();
			//Hay que crear un HashCode() para que cuando lo busque en el HashMap coincida con el objeto anterior
			CodigoPostal cp = new CodigoPostal(codPostal);
			System.out.println(numPersonas.get(cp));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

package ejemplos;

public class EjemploEnumYVarArgs {
	
	public enum Estacion {PRIMAVERA, VERANO, OTOÑO, INVIERNO}
	
	private static Estacion estacion;
	
	public static void main(String[] args) {
		for (Estacion e : Estacion.values()) {
			System.out.println(e);
		}
		
		Estacion e = Estacion.valueOf("INVIERNO");
		
		//Parte Var args
		
		sumar(3, 5 ,7);
		
	}
	
	public static int sumar(Integer... enteros) {
		int suma = 0;
		
		for (Integer e : enteros) {
			suma += e;
		}
		
		return suma;
	}

}

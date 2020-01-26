package main;

public class Calculadora {
	
	public static double dividir(double a, double b) {
		if (b == 0)
			throw new IllegalArgumentException("Can not divide by 0");
		return a/b;
	}
	
	public Calculadora() {
		
	}
	
}

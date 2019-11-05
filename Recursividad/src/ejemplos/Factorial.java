package ejemplos;

public class Factorial {
	
	public static void main(String[] args) {
		imprimir(0);
		System.out.println(factorialRecursivo(5));
		System.out.println(factorialRecursivo(-5));
	}
	
	public static void imprimir(int n) {
		if (n < 10) {
			imprimir(++n);
		}
		System.out.println(n);
	}
	
	public static int factorial(int n) throws Exception {
		if (n < 0) {
			throw new Exception("Error.");
		}
		
		return factorialRecursivo(n);
	}
	
	public static int factorialRecursivo(int n) {
		if(n == 0) {
			return 1;
		} else {
			return factorialRecursivo(n - 1) * n;
		}
	}
}

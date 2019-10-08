package jUnit;

public class CalculadoraImpl implements Calculadora{

	@Override
	public int Sumar(int a, int b) {
		// TODO Auto-generated method stub
		return a + b;
	}

	@Override
	public int Restar(int a, int b) {
		// TODO Auto-generated method stub
		return a - b;
	}

	@Override
	public int Multiplicar(int a, int b) {
		// TODO Auto-generated method stub
		return a * b;
	}

	@Override
	public boolean esPar(int a) {
		// TODO Auto-generated method stub
		if (a % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}

}

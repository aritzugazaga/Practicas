package ejemplos;

public class EjemploStringBuilder {

	public static void main(String[] args) {
		// Se usa para unir Strings
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < 100; i++) {
			sb.append(i);
		}
		
		String s = sb.toString();
	}

}

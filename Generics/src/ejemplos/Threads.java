package ejemplos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Threads {

	public static void main(String[] args) {
		List<Integer> enteros = new ArrayList<>();
		enteros.add(3);
		
		List<Integer> noModificable = Collections.unmodifiableList(enteros);
		noModificable.add(3);
	}
	
	public List<Integer> hacerAlgo() {
		return Collections.emptyList();
	}

}

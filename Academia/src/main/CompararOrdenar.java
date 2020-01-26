package main;

import analysis.WordCount;

public class CompararOrdenar {
	// Para ordenar de mayor a menor dos objetos segun un int (hacerlo en la misma clase, ej. -)
		public int compareTo(WordCount wordCount)  {
			return this.count.compareTo(wordCount.count);
		}
}

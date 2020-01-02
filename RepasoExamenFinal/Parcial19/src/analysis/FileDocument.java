package analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class FileDocument implements Document {
	private File file = null;
	private int chars = 0;
	private int words = 0;
	private int lines = 0;
	
	private List<WordCount> wordCount = new ArrayList<>();
	
	// Para poder hacer el test
	private boolean analyzed = false;

	public FileDocument(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	// Para hacer getName
	public String getName() {
		return file.getName();
	}
	public int getChars() {
		return chars;
	}
	public int getLines() {
		return lines;
	}
	public int getWords() {
		return words;
	}
	public List<WordCount> getWordCount() {
		return wordCount;
	}
	public boolean isAnalyzed() {
		return analyzed;
	}
	
	public void analyze(int minValue) throws DocumentAnalysisException{
		Map<String, Integer> wordMap = new HashMap<>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = null;
			 
			while((line = reader.readLine()) != null) {
				lines++;
				
				chars += line.length();
				
				words += countWords(line, wordMap);
				
			}
			
			analyzed = true;
			
			// Recorrer el mapa
			for (Entry<String, Integer> entry : wordMap.entrySet()) {
				if (entry.getValue() >= minValue) {
					wordCount.add(new WordCount(entry.getKey(), entry.getValue()));
					
				}
			}
			
			// Ordenar las cuenta de las palabras
			Collections.sort(wordCount, Collections.reverseOrder());
			
		} catch (IOException e){
			throw new DocumentAnalysisException("Error reading data from file", e);
			
		}
	}
	
	// Cuenta el numero de las palabras iguales que hay en el texto 
	private int countWords(String line, Map<String, Integer> wordCount) {
		int totalWords = 0;
		StringTokenizer stringTokenizer = new StringTokenizer(line);
		
		while(stringTokenizer.hasMoreTokens()) {
			String word = stringTokenizer.nextToken();
			
			// Para poner todas en minuscula y que no cuente los signos como palabras
			word = word.replaceAll("[.!?\\-]", "").toLowerCase();
			
			if(wordCount.containsKey(word)) {
				Integer value = wordCount.get(word);
				
				wordCount.put(word, value++);
				
			} else {
				wordCount.put(word, 1);
				
			}
			totalWords += 1;
			
		}
		return totalWords;
	}

	@Override
	public String toString() {
		return String.format(
				"name=%s, path=%s, chars=%d, words=%d, lines=%d", 
				getName(),
				getFile(),
				getChars(),
				getWords(),
				getLines()
		);
	}
}

package analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class FileDocument implements Document{
	private File file = null;
	
	private int chars = 0;
	private int words = 0;
	private int lines = 0;
	
	private List<WordCount> wordCount = new ArrayList<>();

	private BufferedReader bf;
	
	private boolean analized = false;

	public FileDocument(File file) {
		super();
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public int getChars() {
		return chars;
	}

	@Override
	public int getLines() {
		return lines;
	}

	@Override
	public int getWords() {
		// TODO Auto-generated method stub
		return words;
	}

	@Override
	public List<WordCount> getWordCount() {
		return wordCount;
	}

	@Override
	public void analyze(int minValue) throws DocumentAnalysisException {
		Map<String, Integer> wordMap = new HashMap<>();
		file = new File("data.txt");
		FileReader fr;
		try {
			fr = new FileReader(file);
			bf = new BufferedReader(fr);
			String line = null;
			while ((line = bf.readLine()) != null) {
				lines ++;
				chars += line.length();
				words += countWords(line, wordMap);
			}
			
			analized = true;
			
			// Recorre el mapa de wordMap
			for (Entry<String, Integer> entry : wordMap.entrySet()) {
				if (entry.getValue() >= minValue) {
					wordCount.add(new WordCount(entry.getKey(), entry.getValue()));
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Añade a wordCount nuevas palabras o suma en 1 al valor de esa palabra si ya existe
	private int countWords(String line, Map<String, Integer> wordCount) {
		int totalWords = 0;
		StringTokenizer st = new StringTokenizer(line, " ");
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			word = word.replaceAll("[.!?\\-]", "");
			if (wordCount.containsKey(word)) {
				Integer value = wordCount.get(word);
				wordCount.put(word, value + 1);
			} else {
				wordCount.put(word, 1);
			}
			totalWords ++;
		}
		
		return totalWords;
	}

	public boolean isAnalized() {
		return analized;
	}

}

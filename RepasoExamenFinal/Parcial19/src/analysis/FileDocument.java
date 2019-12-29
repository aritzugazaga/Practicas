package analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FileDocument {
	private File file = null;
	private int chars = 0;
	private int words = 0;
	private int lines = 0;

	public FileDocument(File file) {
		this.file = file;
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
			
			for (Entry<String, Integer> entry : wordMap.entrySet()) {
				if (entry.getValue() >= minValue) {
					wordCount.add(new WordCount(entry.getKey(), entry.getValue()));
					
				}
			}
			
		} catch (IOException e){
			throw new DocumentAnalysisException("Error reading data from file", e);
			
		}
	}
}

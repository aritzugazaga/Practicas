package analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
			
			for
			
		} catch (IOException e){
			throw new DocumentAnalysisException("Error reading data from file", e);
			
		}
	}
}

package analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileDocument implements Document{
	private File file = null;
	
	private int chars = 0;
	private int words = 0;
	private int lines = 0;
	
	private List<WordCount> wordCount = new ArrayList<>();

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
		// TODO Auto-generated method stub
		return wordCount;
	}

	@Override
	public void analyze(int minValue) throws DocumentAnalysisException {
		file = new File("data.txt");
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader bf = new BufferedReader(fr);
			String line = null;
			while ((line = bf.readLine()) != null) {
				lines ++;
				chars += line.length();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

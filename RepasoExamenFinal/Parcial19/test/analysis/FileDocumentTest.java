package analysis;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import analysis.FileDocument;

class FileDocumentTest {

	private FileDocument notAnalyzed;
	private FileDocument analyzed;
	
	public void setUp() throws DocumentAnalysisException {
		File testFile = new File("test/data/test1.txt");
		notAnalyzed = new FileDocument(testFile);
		
		analyzed = new FileDocument(testFile);
		analyzed.analyze(0);
	}
	
	public void testNotAnalized() throws DocumentAnalysisException {
		assertFalse(notAnalyzed.isAnalyzed());
		assertEquals(new File("test/data/test1.txt"), notAnalyzed.getFile());
		assertEquals("test1.txt", notAnalyzed.getName());
		assertEquals(0, notAnalyzed.getChars());
		assertEquals(0, notAnalyzed.getLines());
		assertEquals(0, notAnalyzed.getWords());
		assertTrue(notAnalyzed.getWordCount().isEmpty());
		
		// Metodo toString tambien
		assertEquals("name=test1.txt, path=test/data/test1.txt, chars=0, words=0, lines=0", notAnalyzed.toString());
	}
	
	public void testAnalized() {
		assertTrue(analyzed.isAnalyzed());
		assertEquals(new File("test/data/test1.txt"), analyzed.getFile());
		assertEquals("test1.txt", analyzed.getName());
		assertEquals(33, analyzed.getChars());
		assertEquals(3, analyzed.getLines());
		assertEquals(6, analyzed.getWords());
		
		//En el que se analiza hay que probar el metodo wordCount ya que se utiliza
		List<WordCount> wordCount = analyzed.getWordCount();
		assertEquals(3, wordCount.size());
		
		assertEquals("linea", wordCount.get(0).getWord());
		assertEquals(3, wordCount.get(0).getCount().intValue());
		
		assertEquals("otra", wordCount.get(1).getWord());
		assertEquals(2, wordCount.get(1).getCount().intValue());
		
		assertEquals("primera", wordCount.get(2).getWord());
		assertEquals(1, wordCount.get(2).getCount().intValue());
		
		//Metodo toString
		assertEquals("name=test1.txt, path=test/data/test1.txt, chars=33, words=6, lines=3", analyzed.toString());
	}

}

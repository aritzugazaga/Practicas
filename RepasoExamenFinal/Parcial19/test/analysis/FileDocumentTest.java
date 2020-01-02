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
		
		List<WordCount> wordCount = analyzed.getWordCount();
		assertEquals(3, analyzed.size());
	}

}

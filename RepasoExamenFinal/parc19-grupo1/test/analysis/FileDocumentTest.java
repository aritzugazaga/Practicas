package analysis;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileDocumentTest {
	private static FileDocument notAnalyzed;
	private static FileDocument analyzed;
	
	@BeforeClass
	public static void setUp() throws DocumentAnalysisException {
		File file = new File("test\\data\\datos1.txt");
		notAnalyzed = new FileDocument(file);
		
		analyzed = new FileDocument(file);
		analyzed.analyze(0);
		
	}
	@Test
	public void notAnalyzedTest() {
		assertFalse(notAnalyzed.isAnalized());
		assertEquals("test\\data\\datos1.txt", notAnalyzed.getFile().toString());
		assertEquals("datos1.txt", notAnalyzed.getName());
		assertEquals(0, notAnalyzed.getChars());
		assertEquals(0, notAnalyzed.getWords());
		assertEquals(0, notAnalyzed.getLines());
		assertTrue(notAnalyzed.getWordCount().isEmpty());

		assertEquals("name=datos1.txt, path=test\\data\\datos1.txt, chars=0, words=0, lines=0", notAnalyzed.toString());
	}
	
	@Test
	public void analyzedtest() {
		assertTrue(analyzed.isAnalized());
		assertEquals("test\\data\\datos1.txt", analyzed.getFile().toString());
		assertEquals("datos1.txt", analyzed.getName());
		assertEquals(33, analyzed.getChars());
		assertEquals(6, analyzed.getWords());
		assertEquals(3, analyzed.getLines());

		List<WordCount> wordCount = analyzed.getWordCount();
		assertEquals(3, wordCount.size());
		
		// Si da fallo, probar en que posicion de la lista esta la palabra
		assertEquals("linea", wordCount.get(0).getWord());
		assertEquals(3, wordCount.get(0).getCount().intValue());
		
		assertEquals("otra", wordCount.get(1).getWord());
		assertEquals(2, wordCount.get(1).getCount().intValue());
		
		assertEquals("primera", wordCount.get(2).getWord());
		assertEquals(1, wordCount.get(2).getCount().intValue());
		
		assertEquals("name=datos1.txt, path=test\\data\\datos1.txt, chars=33, words=6, lines=3", analyzed.toString());
	}

}

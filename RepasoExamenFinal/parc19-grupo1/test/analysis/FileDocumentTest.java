package analysis;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class FileDocumentTest {
	
	private FileDocument fileAnalized;
	private FileDocument fileNotAnalized;
	
	@Before
	public void setUp() {
		File datos = new File("datos.txt");
		fileNotAnalized = new FileDocument(datos);
		
		fileAnalized = new FileDocument(datos);
		try {
			fileAnalized.analyze(0);
		} catch (DocumentAnalysisException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFileNotAnalized() {
		fail("Not yet implemented");
	}

}

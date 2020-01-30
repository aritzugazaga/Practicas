package analysis;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class CollectionStatisticsTest {
	private static CollectionStatistics collectionStatistics;
	private static List<DocumentStatistics> allStatistics;
	
	@BeforeClass
	public static void setUp() {
		List<WordCount> count1 = new ArrayList<>();
		count1.add(new WordCount("casa", 100));
		count1.add(new WordCount("perro", 50));
		count1.add(new WordCount("sol", 10));
		
		DocumentStatistics documentStatistics1 = new DocumentStatistics(3001, count1);
		
		List<WordCount> count2 = new ArrayList<>();
		count2.add(new WordCount("casa", 100));
		count2.add(new WordCount("perro", 20));
		count2.add(new WordCount("mesa", 5));
		
		DocumentStatistics documentStatistics2 = new DocumentStatistics(1500, count2);
		
		allStatistics = new ArrayList<>();
		allStatistics.add(documentStatistics1);
		allStatistics.add(documentStatistics2);
		
		collectionStatistics = new CollectionStatistics(allStatistics);
	}

	@Test
	public void getAvgWordsTest() {
		assertEquals(2250.5, collectionStatistics.getAvgWords(), 0.1);
	}
	
	@Test
	public void getTopWordsTest() {
		List<WordCount> wordCounts = collectionStatistics.getTopWords();
		assertEquals(4, wordCounts.size());
		
		// La lista se ordena del reves
		assertEquals("mesa", wordCounts.get(3).getWord());
		assertEquals(5, wordCounts.get(3).getCount().intValue());
		
		assertEquals("sol", wordCounts.get(2).getWord());
		assertEquals(10, wordCounts.get(2).getCount().intValue());
		
		assertEquals("perro", wordCounts.get(1).getWord());
		assertEquals(70, wordCounts.get(1).getCount().intValue());
		
		assertEquals("casa", wordCounts.get(0).getWord());
		assertEquals(200, wordCounts.get(0).getCount().intValue());
	}

}

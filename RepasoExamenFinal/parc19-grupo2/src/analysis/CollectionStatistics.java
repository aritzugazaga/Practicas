package analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import javax.xml.crypto.dom.DOMURIReference;

import org.w3c.dom.ls.LSInput;

public class CollectionStatistics implements AggregatedStatistics{
	// Metodo comentado es el de la academia el cual el getTopWords no funciona bien
	/*
	private List<DocumentStatistics> documentStatistics;
	private List<WordCount> topWords;
	*/
	private float avgWord;
	private List<WordCount> topWords;
	
	/*
	public CollectionStatistics(List<DocumentStatistics> documentStatistics) {
		super();
		this.documentStatistics = documentStatistics;
	}
	*/
	
	public CollectionStatistics(List<DocumentStatistics> allStatistics) {		
		Map<String, Integer> words = new HashMap<String, Integer>();
		int total = 0;
		for (DocumentStatistics documentStats : allStatistics) {
			total += documentStats.getTotalWords();
			
			for(WordCount wordCount : documentStats.getWordCount()) {
				if (words.containsKey(wordCount.getWord())) {
					Integer value = words.get(wordCount.getWord());
					words.put(wordCount.getWord(), value + wordCount.getCount());
				} else {
					words.put(wordCount.getWord(), wordCount.getCount());
				}
			}
		}
		
		avgWord = total / (float) allStatistics.size();
		
		topWords = new ArrayList<WordCount>();
		for (Entry<String, Integer> word : words.entrySet()) {
			topWords.add(new WordCount(word.getKey(), word.getValue()));
		}
		
		Collections.sort(topWords, Collections.reverseOrder());
		topWords = topWords.subList(0, topWords.size() < 10 ? topWords.size() : 10);
	}

	@Override
	public float getAvgWords() {
		/*
		float sumaPalabras = 0;
		for (DocumentStatistics documentStatistic : documentStatistics) {
			sumaPalabras += documentStatistic.getTotalWords();
		}
		return sumaPalabras / documentStatistics.size();
		*/
		return avgWord;
	}

	@Override
	public List<WordCount> getTopWords() {
		/*
		TreeSet<WordCount> listaOrdenada = new TreeSet<>();
		for (DocumentStatistics documentStatistic : documentStatistics) {
			for (WordCount wordCount : documentStatistic.getWordCount()) {
				listaOrdenada.add(wordCount);
			}
		}
		ArrayList<WordCount> wordCountArray = new ArrayList<>(listaOrdenada);
		return wordCountArray.subList(0, wordCountArray.size() >= 10 ? 10 : wordCountArray.size() - 1);
		*/
		return topWords;
	}

}

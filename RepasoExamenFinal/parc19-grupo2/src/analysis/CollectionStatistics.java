package analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.xml.crypto.dom.DOMURIReference;

import org.w3c.dom.ls.LSInput;

public class CollectionStatistics implements AggregatedStatistics{
	private List<DocumentStatistics> ds;

	public CollectionStatistics(List<DocumentStatistics> ds) {
		super();
		this.ds = ds;
	}

	@Override
	public float getAvgWords() {
		float sumaPalabras = 0;
		for (DocumentStatistics documentStatistics : ds) {
			sumaPalabras += documentStatistics.getTotalWords();
		}
		return sumaPalabras / ds.size();
	}

	@Override
	public List<WordCount> getTopWords() {
		TreeSet<WordCount> listaOrdenada = new TreeSet<>();
		for (DocumentStatistics documentStatistics : ds) {
			for (WordCount wordCount : documentStatistics.getWordCount()) {
				listaOrdenada.add(wordCount);
			}
		}
		ArrayList<WordCount> wordCountArray = new ArrayList<>(listaOrdenada);
		return wordCountArray.subList(0, wordCountArray.size() >= 10 ? 10 : wordCountArray.size() - 1);
	}

}

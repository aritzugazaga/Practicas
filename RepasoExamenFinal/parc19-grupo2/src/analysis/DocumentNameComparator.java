package analysis;

import java.util.Comparator;

public class DocumentNameComparator implements Comparator<Document>{
	private boolean desc;

	public DocumentNameComparator(boolean desc) {
		super();
		this.desc = desc;
	}

	@Override
	public int compare(Document documentA, Document documentB) {
		if (desc) {
			return documentB.getName().compareTo(documentA.getName());
		} else {
			return documentA.getName().compareTo(documentB.getName());
		}
	}

	
	
}

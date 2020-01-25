package orders;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class MyOrder implements Order{
	private HashMap<Item, Integer> mapaI;
	
	 public MyOrder() {
		mapaI = new HashMap<Item, Integer>();
	}
	
	@Override
	public void addItem(Item item, int quantity) {
		if (this.mapaI.containsKey(item)) {
			this.mapaI.put(item, quantity);
		} else {
			Integer cantidadExistente = this.mapaI.get(item);
			this.mapaI.put(item, cantidadExistente + quantity);
		}
	}

	@Override
	public void removeItem(Item item, int quantity) {
		if (mapaI.containsKey(item)) {
			Integer cantidadExistente = this.mapaI.get(item);
			int cantidadRestante = cantidadExistente - quantity;
			
			if(cantidadRestante > 0) {
				mapaI.put(item, cantidadRestante);
			} else {
				mapaI.remove(item);
			}
		}
	}
	
	// Para saber cuantos items tenemos en etse momento
	public Integer getItem(Item item) {
		return mapaI.get(item);
	}

	@Override
	public float getTotalTime() {
		float result = 0;
		for (Entry<Item, Integer> e : mapaI.entrySet()) {
			result += (e.getKey().getTimePerUnit() * e.getValue());
		}
		return result;
	}

	@Override
	public int getDistinctItems() {
		return mapaI.size();
	}

	// Como ordenar una lista
	@Override
	public List<Entry<Item, Integer>> getSortedList() {
		List<Entry<Item, Integer>> result = new ArrayList<Entry<Item,Integer>>(mapaI.entrySet());
		
		Comparator<Entry<Item, Integer>> comparador = new Comparator<Entry<Item,Integer>>() {
			
			@Override
			public int compare(Entry<Item, Integer> o1, Entry<Item, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		};
		result.sort(comparador);
		
		return result;
	}
	
	public void removeAll() {
		mapaI.clear();
	}
}

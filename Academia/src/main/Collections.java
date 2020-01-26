package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;



public class Collections {
	// Para poder comprarar dos objetos entre ellos hay que en la clase que queremos comparar:
	// Source --> Generate hashcode() and equals()
	private HashMap<Item, Integer> mapaI;

	  public MyOrder() {
	    mapaI = new HashMap<>();
	  }

	  public void addItem(Item item, int cantidad) {
	    if (!this.mapaI.containsKey(item)) {
	      this.mapaI.put(item, cantidad);
	    } else {
	      Integer cantidadExistente = this.mapaI.get(item);
	      this.mapaI.put(item, cantidadExistente + cantidad);
	    }
	  }

	  public void removeItem(Item item, int cantidad) {
	    if (mapaI.containsKey(item)) {
	      Integer cantidadExistente = this.mapaI.get(item);
	      int cantidadRestante = cantidadExistente - cantidad;
	      if (cantidadRestante > 0) {
	        mapaI.put(item, cantidadRestante);
	      } else {
	        mapaI.remove(item);
	      }
	    }
	  }

	  public void removeAll() {
	    mapaI.clear();
	  }

	  public Integer getItem(Item item) {
	    return mapaI.get(item);
	  }

	  public float getTotalTime() {
	    float result = 0;

	    for (Entry<Item, Integer> e : mapaI.entrySet()) {
	      result += (e.getKey().getTimePerUnit() * e.getValue());
	    }
	    return result;
	  }

	  public int getDistinctItems() {
	    return mapaI.size();
	  }

	  public List<Entry<Item, Integer>> getSortedList() {
	    List<Entry<Item, Integer>> result = new ArrayList<Entry<Item, Integer>>(mapaI.entrySet());
	    Comparator<Entry<Item, Integer>> comparador = new Comparator<Entry<Item, Integer>>() {

	      @Override
	      public int compare(Entry<Item, Integer> o1, Entry<Item, Integer> o2) {
	        if (o1.getValue() == o2.getValue()) {
	          return o1.getKey().getId().compareTo(o2.getKey().getId());
	        }
	        return o2.getValue().compareTo(o1.getValue());
	      }
	    };

	    result.sort(comparador);

	    return result;
	  }
}

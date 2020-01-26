package main;

import java.util.Map.Entry;

import orders.Item;

public class toString {
	 @Override
	 public String toString() {
		 // Devuelve un String con toda la informacion de todos los items del mapa
		 String items = "";
		 for (Entry<Item, Integer> e : mapaI.entrySet()) {
			 // .concat para concatenar
			 items = items.concat("Item: " + e.getKey() + " Unidades: " + e.getValue());
		 }
		 return items;
	 }
}

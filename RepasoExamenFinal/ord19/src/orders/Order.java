package orders;

import java.util.List;
import java.util.Map.Entry;

/**
 * Esta interfaz define la funcionalidad que debe proporcionar toda Orden
 *
 */
public interface Order {

	/**
	 * Añade un nuevo artículo (Item) a esta orden en la cantidad indicada.
	 * @param item el artículo que debe ser añadido
	 * @param quantity número de artículos de este tipo que se añaden a la orden de fabricación
	 */
	public void addItem(Item item, int quantity);

	/**
	 * Elimina el artículo indicado de la orden en la cantidad indicada. Si el número de artículos llega a cero
	 * el artículo debe ser eliminado de la orden.
	 * @param item el artículo que debe ser eliminado de la orden
	 * @param quantity número de artículos de este tipo que deben ser eliminados de la orden 
	 */
	public void removeItem(Item item, int quantity);

	/**
	 * Calcula el tiempo total que llevará la fabricación de dicha orden a partir de los artículos incluidos en la misma,
	 * el número de unidades de cada uno y el tiempo de fabricación de cada unidad.
	 * @return el tiempo total de fabricación de todo el pedido
	 */
	public float getTotalTime();

	/**
	 * Obtiene el número de artículos (Item) distintos que hay en la orden
	 * @return el número de artículos distintos que hay en la orden de fabricación
	 */
	public int getDistinctItems();

	/**
	 * Obtiene la lista de artículos y su cantidad asociada ordenada por el número de artículos
	 * @return la lista de artículos contenida en el pedido ordenado por la cantidad solicitada
	 */
	public List<Entry<Item, Integer>> getSortedList();
}
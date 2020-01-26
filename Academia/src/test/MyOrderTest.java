package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import orders.Item;
import orders.MyOrder;

public class MyOrderTest {
	  private static MyOrder myOrder;


	  @BeforeClass
	  public static void setUp() {
	    myOrder = new MyOrder();
	  }

	  @AfterClass
	  public static void setDown() {
	    myOrder.removeAll();
	  }

	  // Antes de cada test creamos y añadimos los items
	  @Before
	  public void addItemsBeforeTest() {
	    Item a1 = new Item(1, "a", 1.0f);
	    Item b1 = new Item(2, "b", 2.0f);
	    Item c1 = new Item(3, "c", 3.0f);
	    Item c2 = new Item(3, "c", 3.0f);

	    myOrder.addItem(a1, 1);
	    myOrder.addItem(c1, 1);
	    myOrder.addItem(c2, 1);
	    myOrder.addItem(b1, 1);

	  }

	  // Despues de cada test eliminamos los items del mapa
	  @After
	  public void removeItemsAfterTest() {
	    myOrder.removeAll();
	  }

	  @Test
	  public void getDistinctItemsTest() {
	    assertEquals("Tiene que haber 3 tipos de items que se crean en el before", 3,
	        myOrder.getDistinctItems());
	  }

	  @Test
	  public void getTotalTimeTest() {
	    assertEquals("El tiempo total es la suma del tiempo de los items añadidos en el before", 9,
	        myOrder.getTotalTime(), 0);
	  }

	  @Test
	  public void removeItemTest() {
	    // Eliminamos item del tipo 1 (a1)
	    myOrder.removeItem(new Item(1, "a1", 1.0f), 1);
	    assertEquals("Tiene que haber 2 tipos de items", 2, myOrder.getDistinctItems());
	  }


	  @Test
	  public void getSortedListTest() {

	    Item d1 = new Item(4, "d", 1.0f);
	    myOrder.addItem(d1, 3);

	    List<Entry<Item, Integer>> listaOrdenada = myOrder.getSortedList();
	    assertEquals("El número de items tiene que coincidir con el mapa", 4,
	        myOrder.getDistinctItems(), 0);
	    Integer cantidadAuxiliar = null;
	    for (Entry<Item, Integer> entry : listaOrdenada) {
	      System.out.println("Item: " + entry.getKey().toString() + " Cantidad:" + entry.getValue());
	      if (cantidadAuxiliar != null) {
	        assertTrue("Los elementos están ordenados en orden descendente",
	            cantidadAuxiliar >= entry.getValue());
	      }
	      cantidadAuxiliar = entry.getValue();
	    }
	    
	  }

	}

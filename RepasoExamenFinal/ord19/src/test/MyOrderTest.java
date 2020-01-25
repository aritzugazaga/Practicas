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
	public static MyOrder myOrder;
	
	@BeforeClass
	public static void setUp() {
		myOrder = new MyOrder();
	}
	
	@AfterClass
	public static void setDown() {
		myOrder.removeAll();
	}
	
	@Before
	public void addItemsBeforeTest() {
		Item a1 = new Item(1, "a", (float) 1);
		Item a2 = new Item(2, "b", (float) 1);
		Item a3 = new Item(3, "c", (float) 1);
		Item a4 = new Item(4, "d", (float) 1);
		Item a5 = new Item(5, "e", (float) 1);
		
		myOrder.addItem(a1, 1);
		myOrder.addItem(a2, 1);
		myOrder.addItem(a3, 1);
		myOrder.addItem(a4, 1);
		myOrder.addItem(a5, 1);
	}
	
	@After
	public void removeItemsTest () {
		myOrder.removeAll();
	}
	
	// AddItem no hace falta testear por que ya lo usamos
	
	@Test
	public void removeItemTest() {
		myOrder.removeItem(new Item(1, "a", (float) 1), 1);
		assertEquals(4, myOrder.getDistinctItems());
	}
	
	@Test
	public void getTotalTimeTest() {
		assertEquals(5, myOrder.getTotalTime(), 0);
	}
	
	@Test
	public void getDistinctItemsTest() {
		assertEquals(5, myOrder.getDistinctItems());
	}
	
	@Test
	public void getSortedListTest( ) {
		Item d1 = new Item(5, "e", (float)1);
		myOrder.addItem(d1, 3);
		
		List<Entry<Item, Integer>> listaOrdenada = myOrder.getSortedList();
	}

}

package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Serializacion {
	// Lectura/escritura en ficheros binarios
	// Antes de nada implementar serializable en las clases que usamos
	private void saveOrder(Order order, File file) throws OrderSerializationException {
		// Guarda fichero binario
		try {
			// Output se utiliza para escribir
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(order);
			// Cerrar en orden
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// Lanzar la excepcion que pide el enunciado
			throw new OrderSerializationException("Error en la serialización del objeto", e);
		}
	}
	
	@SuppressWarnings("resource")
	private Order loadOrder(File file) throws OrderSerializationException {
			// Input se utiliza para escribir
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object object = ois.readObject();
				// Cerrar en orden
				ois.close();
				fis.close();
				return (Order) object;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// Lanzar la excepcion que pide el enunciado
				throw new OrderSerializationException("Error en la serialización del objeto", e);
			} catch (ClassNotFoundException e) {
				// Lanzar la excepcion que pide el enunciado
				throw new OrderSerializationException("Error en la serialización del objeto", e);
			}
			
			return null;
			
	}
	// Si hay que escribir dos o mas objetos (Uso el mismo ejemplo)
	private void saveOrders(List<Order> ordenes, File file) throws OrderSerializationException {
		// Guarda fichero binario
		try {
			// Output se utiliza para escribir
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			for (Order orden : ordenes) {
				oos.writeObject(orden);
			}
			// Cerrar en orden
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// Lanzar la excepcion que pide el enunciado
			throw new OrderSerializationException("Error en la serialización del objeto", e);
		}
	}
	// Si hay que leer mas de un objeto
	@SuppressWarnings("resource")
	private List<Order> loadOrders(File file) throws OrderSerializationException {
			// Input se utiliza para escribir
			List<Order> ordenes = new ArrayList<>();
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object object = ois.readObject();
				while (object != null) {
					ordenes.add((Order) object);
					object = ois.readObject();
				}
				// Cerrar en orden
				ois.close();
				fis.close();
				return (Order) object;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// Lanzar la excepcion que pide el enunciado
				throw new OrderSerializationException("Error en la serialización del objeto", e);
			} catch (ClassNotFoundException e) {
				// Lanzar la excepcion que pide el enunciado
				throw new OrderSerializationException("Error en la serialización del objeto", e);
			}
			
			return null;
			
	}
}

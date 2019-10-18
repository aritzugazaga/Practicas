package datosVariablesYHeterogeneos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class EjemploLectura {

	public static void main(String[] args) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("datos.ini")))){
			Alumno a = (Alumno) ois.readObject();
			System.out.println(a.getNombre());
			System.out.println(a.getApellido());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

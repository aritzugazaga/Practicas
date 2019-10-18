package datosVariablesYHeterogeneos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class EjemploTry {
	public static void main(String[] args) {
		try (FileInputStream fis = new FileInputStream(new File(config.ini))) {
			java.util.Properties p = new Properties();
			p.load(fis);
			System.out.println(p.getProperty("usuario"));
		} catch (FileNotFoundException e){
			System.out.println("No se ha podido encontrar el fichero");
		} catch (IOException e) {
			System.out.println();
		}
	}
}

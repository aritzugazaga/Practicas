package examen.ext201806.datos;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@SuppressWarnings("serial")
public class Fecha extends ValorCelda{
	private String fecha;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public Fecha(String fecha) {
		actualizaTexto(fecha);
	}

	@Override
	public String getTextoEdicion() {
		// TODO Auto-generated method stub
		return fecha;
	}

	@Override
	public boolean actualizaTexto(String nuevoValor) {
		try {
			sdf.parse(nuevoValor);
			fecha = nuevoValor;
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return fecha;
	}
	
}

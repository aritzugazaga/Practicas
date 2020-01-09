package examen.ord201801.item;

import java.awt.Color;
import examen.ord201801.EdicionZonasGPS;
import examen.ord201801.PuntoGPS;

public class Arbol extends ItemEnMapa {
	int edadAproximadaEnAnyos;
	Color color;  // Color de dibujado de árbol (gris por defecto)

	/** Crea un árbol representable en un mapa GPS
	 * @param punto	Punto GPS del árbol
	 * @param nombre	Especie del árbol
	 * @param edadAproximada	Edad aproximada del árbol en años
	 */
	public Arbol(PuntoGPS punto, String nombre, int edadAproximada ) {
		super(punto, nombre);
		this.edadAproximadaEnAnyos = edadAproximada;
		color = Color.GRAY;
	}

	/** Devuelve la edad del árbol
	 * @return La edad aproximada del árbol en años
	 */
	public int getEdadAproximada() {
		return edadAproximadaEnAnyos;
	}

	/**	Cambia la edad del árbol
	 * @param edadAproximada La edad del árbol aproximada en años
	 */
	public void setEdadAproximada(int edadAproximada ) {
		this.edadAproximadaEnAnyos = edadAproximada;
	}
	
	/** Devuelve el color utilizado para dibujar el árbol
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/** Cambia el color de dibujado de árbol
	 * @param color nuevo color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/** Dibuja el ítem en un mapa gráfico de la ventana
	 * @param ventana	Ventana en la que dibujar
	 * @param pintaEnVentana	true si se quiere pintar inmediatamente en el mapa, false si se pinta en el objeto gráfico pero no se muestra aún en pantalla
	 */
	@Override
	public void dibuja(EdicionZonasGPS ventana, boolean pintaEnVentana) {
		ventana.dibujaCirculo( punto.getLongitud(), punto.getLatitud(), 4, color, EdicionZonasGPS.stroke2m, true );
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Arbol) {
			Arbol a2 = (Arbol) obj;
			return punto.equals( a2.getPunto() ) && nombre.equals( a2.getNombre() );
		}
		return false;
	}
	
}

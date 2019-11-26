package ejemplos;

public class CodigoPostal {
	
	private int codigo;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	
	
	public CodigoPostal(int codigo) {
		super();
		this.codigo = codigo;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof CodigoPostal)) {
			return false;
		}
		
		CodigoPostal c = (CodigoPostal) o;
		return this.codigo == c.codigo;
		
	}
	
	public int hashCode() {
		return this.codigo;
	}
}

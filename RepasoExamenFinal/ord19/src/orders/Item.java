package orders;

public class Item {
	
	private Integer id;
	private String name;
	private Float timePerUnit;
	
	public Item(Integer id, String name, Float timePerUnit) {
		this.id = id;
		this.name = name;
		this.timePerUnit = timePerUnit;
	}
	
	public Float getTimePerUnit() {
		return timePerUnit;
	}
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	// Source --> Generate hashcode() and equals()
	@Override
	public int hashCode() {
		/*
		 * Hecho por Eclipse automaticamente.
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
		*/
		//Hecho por el profesor
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}

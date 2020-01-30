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
}

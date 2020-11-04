package cars.database.beans;

public class CarModel {
	private int id;
	private String name;
	private String yearStart;
	private String yearEnd;
	
	public CarModel() {}
	
	public CarModel(String name, String yearStart, String yearEnd) {
		this.name=name;
		this.yearStart=yearStart;
		this.yearEnd=yearEnd;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getYearStart() {
		return yearStart;
	}
	
	public void setYearStart(String yearStart) {
		this.yearStart = yearStart;
	}
	
	public String getYearEnd() {
		return yearEnd;
	}
	
	public void setYearEnd(String yearEnd) {
		this.yearEnd = yearEnd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}
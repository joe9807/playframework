package cars.database.beans;

import java.util.Date;

public class CarModel {
	private int id;
	private String name;
	private Date yearStart;
	private Date yearEnd;
	
	public CarModel() {}
	
	public CarModel(String name, Date yearStart, Date yearEnd) {
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
	
	public Date getYearStart() {
		return yearStart;
	}
	
	public void setYearStart(Date yearStart) {
		this.yearStart = yearStart;
	}
	
	public Date getYearEnd() {
		return yearEnd;
	}
	
	public void setYearEnd(Date yearEnd) {
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
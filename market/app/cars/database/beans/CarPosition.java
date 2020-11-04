package cars.database.beans;

import cars.database.beans.CarKind;
import cars.database.beans.CarModel;

public class CarPosition {
	private int id;
	private CarKind kind;
	private CarModel model;
	private String yearIssue;
	private int od;
	private int price;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public CarKind getKind() {
		return kind;
	}
	
	public void setKind(CarKind kind) {
		this.kind = kind;
	}
	
	public CarModel getModel() {
		return model;
	}
	
	public void setModel(CarModel model) {
		this.model = model;
	}
	
	public String getYearIssue() {
		return yearIssue;
	}
	
	public void setYearIssue(String yearIssue) {
		this.yearIssue = yearIssue;
	}

	public int getOd() {
		return od;
	}

	public void setOd(int od) {
		this.od = od;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public String toString() {
		return kind.toString()+"; "+model.toString()+"; "+yearIssue+"; "+od+"; "+price;
	}
}
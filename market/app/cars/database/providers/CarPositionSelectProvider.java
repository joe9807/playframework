package cars.database.providers;

import cars.database.beans.CarPosition;

public class CarPositionSelectProvider {

	public static String getCarPositions(CarPosition carPositions) {
		return "SELECT * from market.carposition";
	}
}

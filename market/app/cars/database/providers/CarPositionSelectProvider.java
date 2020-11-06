package cars.database.providers;

import cars.database.beans.CarPosition;

public class CarPositionSelectProvider {
	private static final String SELECT_ALL = "SELECT * from market.carposition as entity where entity.id=entity.id";

	public static String getCarPositions(CarPosition carPosition) {
		String result = SELECT_ALL;
		if (carPosition == null) return result;

		if (carPosition.getOd() != 0) result+=" and od = "+carPosition.getOd();
		if (carPosition.getPrice() != 0) result+=" and price = "+carPosition.getPrice();
		if (carPosition.getYearIssue() != null) result+=" and yearIssue = "+carPosition.getYearIssue();
		
		return result;
	}
}

package cars.database.providers;

import cars.database.beans.CarPosition;

public class CarPositionSelectProvider {
	public static String getCarPositions(CarPosition carPosition) {
		String result = "SELECT * from market.carposition where";

		if (carPosition.getKind() != null && carPosition.getKind().getName() != null) result+=" kindId = (SELECT id FROM market.carkind WHERE name=#{kind.name}) and";
		if (carPosition.getModel() != null && carPosition.getModel().getName() != null) result+=" modelId = (SELECT id FROM market.carmodel WHERE name=#{model.name}) and";
		if (carPosition.getOd() != 0) result+=" od = #{od} and";
		if (carPosition.getPrice() != 0) result+=" price = #{price} and";
		if (carPosition.getYearIssue() != null) result+=" yearIssue = #{yearIssue} and";
		
		result = result.replaceAll("(and)$", "").trim();
		result = result.replaceAll("(where)$", "").trim();
		return result;
	}
}

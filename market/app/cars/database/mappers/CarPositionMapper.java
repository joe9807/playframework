package cars.database.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import cars.database.beans.CarPosition;
import cars.database.providers.CarPositionSelectProvider;

@Mapper
public interface CarPositionMapper {
	@Insert("INSERT into market.carposition(kindId, modelId, yearIssue, od, price) VALUES(SELECT id FROM market.carkind WHERE name=#{kind.name}, "
			+ "SELECT id FROM market.carmodel WHERE name=#{model.name}, "
			+ "#{yearIssue}, #{od}, #{price})")
	void addCarToMarket(CarPosition carposition);
	
	@Results({
        @Result(property = "kind", column = "kindId",  javaType=cars.database.beans.CarKind.class, one=@One(select = "cars.database.mappers.CarKindMapper.getKindById")),
        @Result(property = "model", column = "modelId", javaType=cars.database.beans.CarModel.class, one=@One(select = "cars.database.mappers.CarModelMapper.getModelById"))
      })
	@SelectProvider(type=CarPositionSelectProvider.class, method="getCarPositions")
	List<CarPosition> getCarPositions(CarPosition carPosition);
	
	@Delete("DELETE from market.carposition")
	void deleteAll();
	
	@Delete("DELETE from market.carposition where id = #{id}")
	void deleteCarPositionById(int id);
}

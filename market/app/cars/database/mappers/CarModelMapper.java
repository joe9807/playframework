package cars.database.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cars.database.beans.CarModel;

@Mapper
public interface CarModelMapper {
	@Insert("INSERT into market.carmodel(name, yearStart, yearEnd) VALUES(#{name}, #{yearStart}, #{yearEnd})")
	void addCarModel(CarModel carModel);
	
	@Update("UPDATE market.carmodel set name=#{name}, yearStart=#{yearStart}, yearEnd=#{yearEnd} where id = #{id}")
	void setCarModel(CarModel carModel);
	
	@Select("SELECT * from market.carmodel where id = #{id}")
	CarModel getModelById(int id);
	
	@Delete("DELETE from market.carmodel")
	void deleteAll();
	
	@Delete("DELETE from market.carmodel where id = #{id}")
	void deleteCarModelById(int id);
	
	@Select("SELECT * from market.carmodel")
	List<CarModel> getCarModels();
}

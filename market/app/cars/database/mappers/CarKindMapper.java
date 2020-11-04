package cars.database.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cars.database.beans.CarKind;

@Mapper
public interface CarKindMapper {
	@Insert("INSERT into market.carkind(name, country) VALUES(#{name}, #{country})")
	void addCarKind(CarKind carKind);
	
	@Select("SELECT name, country from market.carkind where id = #{id}")
	CarKind getKindById(int id);
	
	@Delete("DELETE from market.carkind")
	void deleteAll();
	
	@Delete("DELETE from market.carkind where id = #{id}")
	void deleteCarKindById(int id);
	
	@Select("SELECT * from market.carkind")
	List<CarKind> getCarKinds();
}

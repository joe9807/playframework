package cars.database;

import java.util.List;

import cars.database.GenericDatabase;
import cars.database.H2Database;
import cars.database.beans.CarKind;
import cars.database.beans.CarModel;
import cars.database.beans.CarPosition;
import cars.database.mappers.CarKindMapper;
import cars.database.mappers.CarModelMapper;
import cars.database.mappers.CarPositionMapper;

public class H2Database extends GenericDatabase{
	private static H2Database instance;

	public static synchronized H2Database getInstance(String dbDefaultUrl) {
		if (instance == null) {	
			instance = new H2Database();
			instance.open(dbDefaultUrl);
		}
		return instance;
	}
	
	public void addCarModel(CarModel carModel) {
		doInTransaction(session->{
			session.getMapper(CarModelMapper.class).addCarModel(carModel);
		});
	}

	public void addCardKind(CarKind carKind) {
		doInTransaction(session->{
			session.getMapper(CarKindMapper.class).addCarKind(carKind);
		});
	}
	
	public void addCarToMarket(CarPosition carPosition) {
		doInTransaction(session->{
			session.getMapper(CarPositionMapper.class).addCarToMarket(carPosition);
		});
	}
	
	public List<CarKind> getCarKinds() {
		return doInTransactionWithResult(session->{
			return session.getMapper(CarKindMapper.class).getCarKinds();
		});
	}
	
	public List<CarModel> getCarModels() {
		return doInTransactionWithResult(session->{
			return session.getMapper(CarModelMapper.class).getCarModels();
		});
	}
	
	public List<CarPosition> getCarPositions(CarPosition searchCarPosition) {
		return doInTransactionWithResult(session->{
			return session.getMapper(CarPositionMapper.class).getCarPositions(searchCarPosition);
		});
	}
	
	public void deleteAllCarPositions() {
		doInTransaction(session->{
			session.getMapper(CarPositionMapper.class).deleteAll();
		});
	}
	
	public void deleteAllCarModels() {
		doInTransaction(session->{
			session.getMapper(CarModelMapper.class).deleteAll();
		});
	}
	
	public void deleteAllCarKinds() {
		doInTransaction(session->{
			session.getMapper(CarKindMapper.class).deleteAll();
		});
	}
	
	public void deleteCarKindById(int id) {
		doInTransaction(session->{
			session.getMapper(CarKindMapper.class).deleteCarKindById(id);
		});
	}
	
	public void deleteCarModelById(int id) {
		doInTransaction(session->{
			session.getMapper(CarModelMapper.class).deleteCarModelById(id);
		});
	}
	
	public void deleteCarPositionById(int id) {
		doInTransaction(session->{
			session.getMapper(CarPositionMapper.class).deleteCarPositionById(id);
		});
	}
}
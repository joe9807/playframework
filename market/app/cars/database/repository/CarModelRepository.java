package cars.database.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.typesafe.config.Config;

import cars.database.DatabaseExecutionContext;
import cars.database.H2Database;
import cars.database.beans.CarModel;
import cars.database.dao.CarModelDAO;

public class CarModelRepository implements CarModelDAO {
	private final DatabaseExecutionContext executionContext;
	private H2Database database;
	
	@Inject
	public CarModelRepository(DatabaseExecutionContext executionContext, Config config) {
		this.executionContext=executionContext;
		this.database=H2Database.getInstance(config.getString("db.default.url"));
	}

	@Override
	public CompletionStage<Void> add(CarModel carModel) {
		return CompletableFuture.runAsync(() -> {
    		database.addCarModel(carModel);
    	}, executionContext);
	}

	@Override
	public CompletionStage<List<CarModel>> get() {
		return CompletableFuture.supplyAsync(() -> {
    		return database.getCarModels();
    	}, executionContext);
	}

	@Override
	public CompletionStage<Void> set(CarModel carModel) {
		return CompletableFuture.runAsync(() -> {
    		database.setCarModel(carModel);
    	}, executionContext);
	}

	@Override
	public CompletionStage<Void> delete(CarModel carModel) {
		return CompletableFuture.runAsync(() -> {
    		database.deleteCarModelById(carModel.getId());
    	}, executionContext);
	}
}
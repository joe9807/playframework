package cars.database.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import cars.database.DatabaseExecutionContext;
import cars.database.H2Database;
import cars.database.beans.CarPosition;
import cars.database.dao.CarPositionDAO;
import play.db.Database;

public class CarPositionRepository implements CarPositionDAO {
	private final DatabaseExecutionContext executionContext;
	private H2Database database;
	
	@Inject
	public CarPositionRepository(DatabaseExecutionContext executionContext, Database database) {
		this.executionContext=executionContext;
		this.database=H2Database.getInstance(database.getUrl());
	}

	@Override
	public CompletionStage<Void> add(CarPosition carPosition) {
		return CompletableFuture.runAsync(() -> {
    		database.addCarPosition(carPosition);
    	}, executionContext);
	}

	@Override
	public CompletionStage<List<CarPosition>> get(CarPosition carPosition) {
		return CompletableFuture.supplyAsync(() -> {
    		return database.getCarPositions(carPosition);
    	}, executionContext);
	}

	@Override
	public CompletionStage<Void> set(CarPosition carPosition) {
		return CompletableFuture.runAsync(() -> {
    		database.setCarPosition(carPosition);
    	}, executionContext);
	}

	@Override
	public CompletionStage<Void> delete(CarPosition carPosition) {
		return CompletableFuture.runAsync(() -> {
    		database.deleteCarPositionById(carPosition.getId());
    	}, executionContext);
	}
}
package cars.database.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.typesafe.config.Config;

import cars.database.DatabaseExecutionContext;
import cars.database.H2Database;
import cars.database.beans.CarKind;
import cars.database.dao.CarKindDAO;

public class CarKindRepository implements CarKindDAO {
	private final DatabaseExecutionContext executionContext;
	private H2Database database;
	
	@Inject
	public CarKindRepository(DatabaseExecutionContext executionContext, Config config) {
		this.executionContext=executionContext;
		this.database=H2Database.getInstance(config.getString("db.default.url"));
	}

	@Override
	public CompletionStage<Void> add(CarKind carKind) {
		return CompletableFuture.runAsync(() -> {
    		database.addCarKind(carKind);
    	}, executionContext);
	}

	@Override
	public CompletionStage<List<CarKind>> get() {
		return CompletableFuture.supplyAsync(() -> {
    		return database.getCarKinds();
    	}, executionContext);
	}

	@Override
	public CompletionStage<Void> set(CarKind carKind) {
		return CompletableFuture.runAsync(() -> {
    		database.setCarKind(carKind);
    	}, executionContext);
	}

	@Override
	public CompletionStage<Void> delete(CarKind carKind) {
		return CompletableFuture.runAsync(() -> {
    		database.deleteCarKindById(carKind.getId());
    	}, executionContext);
	}
}
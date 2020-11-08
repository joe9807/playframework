package cars.database.dao;

import java.util.List;
import java.util.concurrent.CompletionStage;

import com.google.inject.ImplementedBy;

import cars.database.beans.CarPosition;
import cars.database.repository.CarPositionRepository;

@ImplementedBy(CarPositionRepository.class)
public interface CarPositionDAO {
	public CompletionStage<Void> add(CarPosition carPosition);
	public CompletionStage<List<CarPosition>> get(CarPosition carPosition);
	public CompletionStage<Void> set(CarPosition carPosition);
	public CompletionStage<Void> delete(CarPosition carPosition);
}
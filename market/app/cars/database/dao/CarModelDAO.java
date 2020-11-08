package cars.database.dao;

import java.util.List;
import java.util.concurrent.CompletionStage;

import com.google.inject.ImplementedBy;

import cars.database.beans.CarModel;
import cars.database.repository.CarModelRepository;

@ImplementedBy(CarModelRepository.class)
public interface CarModelDAO {
	public CompletionStage<Void> add(CarModel carModel);
	public CompletionStage<List<CarModel>> get();
	public CompletionStage<Void> set(CarModel carModel);
	public CompletionStage<Void> delete(CarModel carModel);
}
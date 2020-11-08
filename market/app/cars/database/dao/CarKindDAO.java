package cars.database.dao;

import java.util.List;
import java.util.concurrent.CompletionStage;

import com.google.inject.ImplementedBy;

import cars.database.beans.CarKind;
import cars.database.repository.CarKindRepository;

@ImplementedBy(CarKindRepository.class)
public interface CarKindDAO {
	public CompletionStage<Void> add(CarKind carKind);
	public CompletionStage<List<CarKind>> get();
	public CompletionStage<Void> set(CarKind carKind);
	public CompletionStage<Void> delete(CarKind carKind);
}
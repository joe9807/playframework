package controllers;

import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.inject.Inject;

import cars.database.DatabaseExecutionContext;
import cars.database.beans.CarModel;
import cars.database.repository.CarModelRepository;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class CarModelController extends Controller {
	private final FormFactory formFactory;
	private final DatabaseExecutionContext customContext;
	private CarModelRepository repository;

	@Inject
    public CarModelController(FormFactory formFactory, DatabaseExecutionContext customContext, CarModelRepository repository) {
		this.formFactory=formFactory;
		this.customContext=customContext;
		this.repository=repository;
    }
	
    public CompletionStage<Result> addCarModel(final Http.Request request) {
    	CarModel carModel = formFactory.form(CarModel.class).bindFromRequest(request).get();
    	return repository.add(carModel).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
    
    public CompletionStage<Result> getCarModels(final Http.Request request) {
    	return repository.get().thenApplyAsync(carModels -> ok(play.libs.Json.toJson(carModels.stream().collect(Collectors.toList()))), customContext.current());
    }
    
    public CompletionStage<Result> setCarModel(final Http.Request request) {
    	CarModel carModel = formFactory.form(CarModel.class).bindFromRequest(request).get();
    	return repository.set(carModel).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
    
    public CompletionStage<Result> deleteCarModel(final Http.Request request) {
    	CarModel carModel = formFactory.form(CarModel.class).bindFromRequest(request).get();
    	return repository.delete(carModel).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
}
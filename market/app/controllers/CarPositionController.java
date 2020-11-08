package controllers;

import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.inject.Inject;

import cars.database.beans.CarPosition;
import cars.database.repository.CarPositionRepository;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class CarPositionController extends Controller {
	private final FormFactory formFactory;
	private final ControllerExecutionContext customContext;
	private final CarPositionRepository repository;

	@Inject
    public CarPositionController(FormFactory formFactory, ControllerExecutionContext customContext, CarPositionRepository repository) {
		this.formFactory=formFactory;
		this.customContext=customContext;
		this.repository=repository;
    }
    
    public CompletionStage<Result> addCarPosition(final Http.Request request) {
    	CarPosition carPosition = formFactory.form(CarPosition.class).bindFromRequest(request).get();
    	return repository.add(carPosition).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
    
    public CompletionStage<Result> getCarPositions(final Http.Request request) {
    	CarPosition carPosition = formFactory.form(CarPosition.class).bindFromRequest(request).get();
    	return repository.get(carPosition).thenApplyAsync(carPositions -> ok(play.libs.Json.toJson(carPositions.stream().collect(Collectors.toList()))), customContext.current());
    }
    
    public CompletionStage<Result> setCarPosition(final Http.Request request) {
    	CarPosition carPosition = formFactory.form(CarPosition.class).bindFromRequest(request).get();
    	return repository.set(carPosition).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
	
    public CompletionStage<Result> deleteCarPosition(final Http.Request request) {
    	CarPosition carPosition = formFactory.form(CarPosition.class).bindFromRequest(request).get();
    	return repository.delete(carPosition).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
}
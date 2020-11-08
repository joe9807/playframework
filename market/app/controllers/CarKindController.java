package controllers;

import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.inject.Inject;

import cars.database.DatabaseExecutionContext;
import cars.database.beans.CarKind;
import cars.database.repository.CarKindRepository;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class CarKindController extends Controller {
	private final FormFactory formFactory;
	private final DatabaseExecutionContext customContext;
	private final CarKindRepository repository;

	@Inject
    public CarKindController(FormFactory formFactory, DatabaseExecutionContext customContext, CarKindRepository repository) {
		this.formFactory=formFactory;
		this.customContext=customContext;
		this.repository=repository;
    }
	
    public CompletionStage<Result> addCarKind(final Http.Request request) {
    	CarKind carKind = formFactory.form(CarKind.class).bindFromRequest(request).get();
    	return repository.add(carKind).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
    
    public CompletionStage<Result> getCarKinds(final Http.Request request) {
    	return repository.get().thenApplyAsync(carKinds -> ok(play.libs.Json.toJson(carKinds.stream().collect(Collectors.toList()))), customContext.current());
    }
    
    public CompletionStage<Result> setCarKind(final Http.Request request) {
    	CarKind carKind = formFactory.form(CarKind.class).bindFromRequest(request).get();
    	return repository.set(carKind).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
    
    public CompletionStage<Result> deleteCarKind(final Http.Request request) {
    	CarKind carKind = formFactory.form(CarKind.class).bindFromRequest(request).get();
    	return repository.delete(carKind).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
}
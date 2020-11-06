package controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.typesafe.config.Config;

import cars.database.DatabaseExecutionContext;
import cars.database.H2Database;
import cars.database.beans.CarKind;
import cars.database.beans.CarModel;
import cars.database.beans.CarPosition;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecution;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class MarketController extends Controller {
	private final FormFactory formFactory;
	private final DatabaseExecutionContext customContext;
	private H2Database database;

	@Inject
    public MarketController(FormFactory formFactory, DatabaseExecutionContext customContext, Config config) {
		this.formFactory=formFactory;
		this.customContext=customContext;
		this.database=H2Database.getInstance(config.getString("db.default.url"));
    }
	
    public Result index() {
        return ok(views.html.index.render());
    }
    
    //search api
    public CompletionStage<Result> searchCarPositions(final Http.Request request) {
    	CarPosition carPosition = formFactory.form(CarPosition.class).bindFromRequest(request).get();
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.runAsync(() -> {
    		List<CarPosition> carPositions = database.getCarPositions(carPosition);
    		System.out.println(carPositions);
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customExecutor);
    }
    
    //Car Positions
    private CarPosition bindCarPosition(final Http.Request request) {
    	CarKind carKind = new CarKind();
    	CarModel carModel = new CarModel();
    	
    	carKind.setName(formFactory.form().bindFromRequest(request).field("carKindsSelect").value().get());
    	carModel.setName(formFactory.form().bindFromRequest(request).field("carModelsSelect").value().get());

    	CarPosition carPosition = formFactory.form(CarPosition.class).bindFromRequest(request).get();
    	carPosition.setKind(carKind);
    	carPosition.setModel(carModel);
    	
    	return carPosition;
    }
    
    public CompletionStage<Result> deleteCarPosition(final Http.Request request) {
    	CarPosition carPosition = formFactory.form(CarPosition.class).bindFromRequest(request).get();
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.runAsync(() -> {
    		database.deleteCarPositionById(carPosition.getId());
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customExecutor);
    }

    public CompletionStage<Result> addCarPosition(final Http.Request request) {
    	CarPosition carPosition = bindCarPosition(request);
    	
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.supplyAsync(() -> {
    		database.addCarToMarket(carPosition);
    		
    		return carPosition;
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customExecutor);
    }
    
    public CompletionStage<Result> getCarPositions(final Http.Request request) {
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.supplyAsync(() -> {
    		return database.getCarPositions(null);
    	}, customContext).thenApplyAsync(carKinds -> ok(play.libs.Json.toJson(carKinds.stream().collect(Collectors.toList()))), customExecutor);
    }
    
    //Car Kinds
    public CompletionStage<Result> deleteCarKind(final Http.Request request) {
    	CarKind carKind = formFactory.form(CarKind.class).bindFromRequest(request).get();
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.runAsync(() -> {
    		database.deleteCarKindById(carKind.getId());
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customExecutor);
    }

    public CompletionStage<Result> addCarKind(final Http.Request request) {
    	CarKind carKind = formFactory.form(CarKind.class).bindFromRequest(request).get();
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.supplyAsync(() -> {
    		database.addCardKind(carKind);
    		
    		return carKind;
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customExecutor);
    }
    
    public CompletionStage<Result> getCarKinds(final Http.Request request) {
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.supplyAsync(() -> {
    		return database.getCarKinds();
    	}, customContext).thenApplyAsync(carKinds -> ok(play.libs.Json.toJson(carKinds.stream().collect(Collectors.toList()))), customExecutor);
    }
    
    //Car Models
    public CompletionStage<Result> addCarModel(final Http.Request request) {
    	CarModel carModel = formFactory.form(CarModel.class).bindFromRequest(request).get();
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.supplyAsync(() -> {
    		database.addCarModel(carModel);
    		
    		return carModel;
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customExecutor);
    }
    
    public CompletionStage<Result> getCarModels(final Http.Request request) {
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.supplyAsync(() -> {
    		return database.getCarModels();
    	}, customContext).thenApplyAsync(carModels -> ok(play.libs.Json.toJson(carModels.stream().collect(Collectors.toList()))), customExecutor);
    }
    
    public CompletionStage<Result> deleteCarModel(final Http.Request request) {
    	CarModel carKind = formFactory.form(CarModel.class).bindFromRequest(request).get();
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.runAsync(() -> {
    		database.deleteCarModelById(carKind.getId());
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customExecutor);
    }
}

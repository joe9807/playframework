package controllers;

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
	private final Config config;

	@Inject
    public MarketController(FormFactory formFactory, DatabaseExecutionContext customContext, Config config) {
		this.formFactory=formFactory;
		this.customContext=customContext;
		this.config=config;
    }
    public Result index() {
        return ok(views.html.index.render());
    }
    
    public CompletionStage<Result> deleteCarModel(final Http.Request request) {
    	CarModel carKind = formFactory.form(CarModel.class).bindFromRequest(request).get();
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.runAsync(() -> {
    		H2Database.getInstance(config.getString("db.default.url")).deleteCarModelById(carKind.getId());
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customExecutor);
    }
    
    public CompletionStage<Result> deleteCarKind(final Http.Request request) {
    	CarKind carKind = formFactory.form(CarKind.class).bindFromRequest(request).get();
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.runAsync(() -> {
    		H2Database.getInstance(config.getString("db.default.url")).deleteCarKindById(carKind.getId());
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customExecutor);
    }

    public CompletionStage<Result> addCarKind(final Http.Request request) {
    	CarKind carKind = formFactory.form(CarKind.class).bindFromRequest(request).get();
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.supplyAsync(() -> {
    		H2Database.getInstance(config.getString("db.default.url")).addCardKind(carKind);
    		
    		return carKind;
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customExecutor);
    }
    
    public CompletionStage<Result> addCarModel(final Http.Request request) {
    	CarModel carModel = formFactory.form(CarModel.class).bindFromRequest(request).get();
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.supplyAsync(() -> {
    		H2Database.getInstance(config.getString("db.default.url")).addCarModel(carModel);
    		
    		return carModel;
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customExecutor);
    }
    
    public CompletionStage<Result> getCarKinds(final Http.Request request) {
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.supplyAsync(() -> {
    		return H2Database.getInstance(config.getString("db.default.url")).getCarKinds();
    	}, customContext).thenApplyAsync(carKinds -> ok(play.libs.Json.toJson(carKinds.stream().collect(Collectors.toList()))), customExecutor);
    }
    
    public CompletionStage<Result> getCarModels(final Http.Request request) {
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.supplyAsync(() -> {
    		return H2Database.getInstance(config.getString("db.default.url")).getCarModels();
    	}, customContext).thenApplyAsync(carModels -> ok(play.libs.Json.toJson(carModels.stream().collect(Collectors.toList()))), customExecutor);
    }
}

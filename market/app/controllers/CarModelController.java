package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.typesafe.config.Config;

import cars.database.DatabaseExecutionContext;
import cars.database.H2Database;
import cars.database.beans.CarModel;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class CarModelController extends Controller {
	private final FormFactory formFactory;
	private final DatabaseExecutionContext customContext;
	private final H2Database database;

	@Inject
    public CarModelController(FormFactory formFactory, DatabaseExecutionContext customContext, Config config) {
		this.formFactory=formFactory;
		this.customContext=customContext;
		this.database=H2Database.getInstance(config.getString("db.default.url"));
    }
	
    public CompletionStage<Result> addCarModel(final Http.Request request) {
    	CarModel carModel = formFactory.form(CarModel.class).bindFromRequest(request).get();
    	
    	return CompletableFuture.runAsync(() -> {
    		database.addCarModel(carModel);
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
    
    public CompletionStage<Result> getCarModels(final Http.Request request) {
    	return CompletableFuture.supplyAsync(() -> {
    		return database.getCarModels();
    	}, customContext).thenApplyAsync(carModels -> ok(play.libs.Json.toJson(carModels.stream().collect(Collectors.toList()))), customContext.current());
    }
    
    public CompletionStage<Result> deleteCarModel(final Http.Request request) {
    	CarModel carKind = formFactory.form(CarModel.class).bindFromRequest(request).get();
    	
    	return CompletableFuture.runAsync(() -> {
    		database.deleteCarModelById(carKind.getId());
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
}
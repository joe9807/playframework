package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.typesafe.config.Config;

import cars.database.DatabaseExecutionContext;
import cars.database.H2Database;
import cars.database.beans.CarPosition;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class CarPositionController extends Controller {
	private final FormFactory formFactory;
	private final DatabaseExecutionContext customContext;
	private final H2Database database;

	@Inject
    public CarPositionController(FormFactory formFactory, DatabaseExecutionContext customContext, Config config) {
		this.formFactory=formFactory;
		this.customContext=customContext;
		this.database=H2Database.getInstance(config.getString("db.default.url"));
    }
	
    public CompletionStage<Result> searchCarPositions(final Http.Request request) {
    	CarPosition carPosition = formFactory.form(CarPosition.class).bindFromRequest(request).get();
    	
    	return CompletableFuture.runAsync(() -> {
    		database.getCarPositions(carPosition);
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
	
    public CompletionStage<Result> deleteCarPosition(final Http.Request request) {
    	CarPosition carPosition = formFactory.form(CarPosition.class).bindFromRequest(request).get();
    	
    	return CompletableFuture.runAsync(() -> {
    		database.deleteCarPositionById(carPosition.getId());
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }

    public CompletionStage<Result> addCarPosition(final Http.Request request) {
    	CarPosition carPosition = formFactory.form(CarPosition.class).bindFromRequest(request).get();
    	
    	return CompletableFuture.runAsync(() -> {
    		database.addCarToMarket(carPosition);
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
    
    public CompletionStage<Result> getCarPositions(final Http.Request request) {
    	return CompletableFuture.supplyAsync(() -> {
    		return database.getCarPositions(null);
    	}, customContext).thenApplyAsync(carKinds -> ok(play.libs.Json.toJson(carKinds.stream().collect(Collectors.toList()))), customContext.current());
    }
}
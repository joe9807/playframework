package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.typesafe.config.Config;

import cars.database.DatabaseExecutionContext;
import cars.database.H2Database;
import cars.database.beans.CarKind;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

public class CarKindController extends Controller {
	private final FormFactory formFactory;
	private final DatabaseExecutionContext customContext;
	private final H2Database database;

	@Inject
    public CarKindController(FormFactory formFactory, DatabaseExecutionContext customContext, Config config) {
		this.formFactory=formFactory;
		this.customContext=customContext;
		this.database=H2Database.getInstance(config.getString("db.default.url"));
    }
	
    public CompletionStage<Result> deleteCarKind(final Http.Request request) {
    	CarKind carKind = formFactory.form(CarKind.class).bindFromRequest(request).get();
    	
    	return CompletableFuture.runAsync(() -> {
    		database.deleteCarKindById(carKind.getId());
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }

    public CompletionStage<Result> addCarKind(final Http.Request request) {
    	CarKind carKind = formFactory.form(CarKind.class).bindFromRequest(request).get();
    	
    	return CompletableFuture.runAsync(() -> {
    		database.addCardKind(carKind);
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customContext.current());
    }
    
    public CompletionStage<Result> getCarKinds(final Http.Request request) {
    	return CompletableFuture.supplyAsync(() -> {
    		return database.getCarKinds();
    	}, customContext).thenApplyAsync(carKinds -> ok(play.libs.Json.toJson(carKinds.stream().collect(Collectors.toList()))), customContext.current());
    }
}
package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import javax.inject.Inject;

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

	@Inject
    public MarketController(FormFactory formFactory, DatabaseExecutionContext customContext) {
		this.formFactory=formFactory;
		this.customContext=customContext;
    	
    }
    public Result index() {
        return ok(views.html.index.render());
    }

    public CompletionStage<Result> addCarKind(final Http.Request request) {
    	CarKind carKind = formFactory.form(CarKind.class).bindFromRequest(request).get();
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	CompletionStage<Result> completionResult = CompletableFuture.supplyAsync(() -> {
    		H2Database.getInstance().addCardKind(carKind);
    		
    		return carKind;
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customExecutor);
    	
    	return completionResult;
    }
    
    public CompletionStage<Result> addCarModel(final Http.Request request) {
    	CarModel carModel = formFactory.form(CarModel.class).bindFromRequest(request).get();
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	CompletionStage<Result> completionResult = CompletableFuture.supplyAsync(() -> {
    		H2Database.getInstance().addCarModel(carModel);
    		
    		return carModel;
    	}, customContext).thenApplyAsync(r->redirect(routes.MarketController.index()), customExecutor);
    	
    	return completionResult;
    }
    
    public CompletionStage<Result> getCarKinds(final Http.Request request) {
    	Executor customExecutor = HttpExecution.fromThread(customContext);
    	
    	return CompletableFuture.supplyAsync(() -> {
    		return H2Database.getInstance().getCarKinds();
    	}, customContext).thenApplyAsync(carKinds -> ok(play.libs.Json.toJson(carKinds.stream().collect(Collectors.toList()))), customExecutor);
    }
}

package controllers;

import javax.inject.Inject;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class MarketController extends Controller {

	@Inject
    public MarketController() {}
	
    public Result index() {
        return ok(views.html.index.render());
    }
}
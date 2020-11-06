package controllers;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

import org.junit.Test;

import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Http.RequestBuilder;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

public class MarketControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder().method(GET).uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }
    
    @Test
    public void testBadRoute() {
      RequestBuilder request = Helpers.fakeRequest().method(GET).uri("/just/some/urli");

      Result result = route(app, request);
      assertEquals(NOT_FOUND, result.status());
    }
}
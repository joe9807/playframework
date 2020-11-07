package controllers;

import org.junit.Assert;
import org.junit.Test;

import play.mvc.Http;
import play.mvc.Http.RequestBuilder;
import play.mvc.Http.Status;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

public class MarketControllerTest extends WithApplication {
    @Test
    public void testBadRoute() {
      RequestBuilder request = Helpers.fakeRequest().method(Helpers.GET).uri("/just/some/url");

      Result result = Helpers.route(app, request);
      Assert.assertEquals(Status.NOT_FOUND, result.status());
    }

    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder().method(Helpers.GET).uri("/");

        Result result = Helpers.route(app, request);
        Assert.assertEquals(Status.OK, result.status());
    }
}
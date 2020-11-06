package controllers;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.route;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import cars.database.beans.CarKind;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
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
    
    public void testList() {
    	CarKind carKind = new CarKind("1", "2");
    	//Form form = mock(Form.class);
    	//Http.RequestBuilder request = new Http.RequestBuilder().method(POST).bodyText("name=CarKindName&country=CarKindCountry").uri("/carKind/add");
    	Http.RequestBuilder request = Helpers.fakeRequest("POST", "/carKind/add").header("Content-Type", "application/x-www-form-urlencoded").bodyText("name=CarKindName&country=CarKindCountry");
    	
    	Result result = route(app, request);

        assertEquals(200, result.status());

        //JsonNode listOfPosts = contentAsJson(result);
        //Optional<PostResource> post = findPostByTitle(listOfPosts, "title-of-post-123");
        //assertTrue(post.isPresent());
    }
    
    private JsonNode contentAsJson(Result result) {
        final String responseBody = contentAsString(result);
        return Json.parse(responseBody);
    }
}
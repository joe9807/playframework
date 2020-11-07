package controllers;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import play.mvc.Http;
import play.mvc.Http.Status;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

public class CarKindControllerTest extends WithApplication {
    @Test
    public void testAddCarKind() {
    	String stringResult = ",\"name\":\"11\",\"country\":\"2\"";
    	Map<String, String> formData = new HashMap<>();
        formData.put("name", "11");
        formData.put("country", "2");
        
    	Http.RequestBuilder request = Helpers.fakeRequest(Helpers.POST, "/carKind/add").bodyForm(formData);
    	Result result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);

    	request = Helpers.fakeRequest(Helpers.GET, "/carKind/all");
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	Assert.assertTrue(Helpers.contentAsString(result).contains(stringResult));
    }
}

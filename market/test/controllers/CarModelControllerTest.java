package controllers;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Http.Status;
import play.test.Helpers;
import play.test.WithApplication;

public class CarModelControllerTest extends WithApplication {
	@Test
    public void testAddCarModel() {
    	String stringResult = ",\"name\":\"CarModelName\",\"yearStart\":\"2015\",\"yearEnd\":\"2018\"";
    	Map<String, String> formData = new HashMap<>();
        formData.put("name", "CarModelName");
        formData.put("yearStart", "2015");
        formData.put("yearEnd", "2018");
        
    	Http.RequestBuilder request = Helpers.fakeRequest(Helpers.POST, "/carModel/add").bodyForm(formData);
    	Result result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);

    	request = Helpers.fakeRequest(Helpers.GET, "/carModel/all");
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	Assert.assertTrue(Helpers.contentAsString(result).contains(stringResult));
    }
}

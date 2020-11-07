package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cars.database.beans.CarKind;
import cars.database.beans.CarModel;
import cars.database.beans.CarPosition;
import play.mvc.Http;
import play.mvc.Http.RequestBuilder;
import play.mvc.Http.Status;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MarketControllerTest extends WithApplication {
	private CarKind carKind;
	private CarModel carModel;
	private CarPosition carPosition;
	
	@Before
	public void before() {
		carKind = new CarKind("CarKindName", "CarKindCountry");
		carModel = new CarModel("CarModelName", "2015", "2018");
		carPosition = new CarPosition();
    	carPosition.setOd(56000);
    	carPosition.setPrice(750000);
    	carPosition.setYearIssue("2020");
    	carPosition.setKind(carKind);
    	carPosition.setModel(carModel);
	}
	
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
    
    @Test
    public void testAddCarKind() throws Exception{
    	Map<String, String> formData = new HashMap<>();
        formData.put("name", carKind.getName());
        formData.put("country", carKind.getCountry());
        
    	Http.RequestBuilder request = Helpers.fakeRequest(Helpers.POST, "/carKind/add").bodyForm(formData);
    	Result result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);

    	request = Helpers.fakeRequest(Helpers.GET, "/carKind/all");
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	List<CarKind> carKinds = new ObjectMapper().readValue(Helpers.contentAsString(result), new TypeReference<List<CarKind>>(){});
    	Assert.assertTrue(carKinds.size() == 1);
    	CarKind carKind = carKinds.get(0);
    	Assert.assertEquals(this.carKind.getName(), carKind.getName());
    	Assert.assertEquals(this.carKind.getCountry(), carKind.getCountry());
    }
    
    @Test
    public void testAddCarModel() throws Exception{
    	Map<String, String> formData = new HashMap<>();
        formData.put("name", carModel.getName());
        formData.put("yearStart", carModel.getYearStart());
        formData.put("yearEnd", carModel.getYearEnd());
        
    	Http.RequestBuilder request = Helpers.fakeRequest(Helpers.POST, "/carModel/add").bodyForm(formData);
    	Result result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);

    	request = Helpers.fakeRequest(Helpers.GET, "/carModel/all");
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	List<CarModel> carModels = new ObjectMapper().readValue(Helpers.contentAsString(result), new TypeReference<List<CarModel>>(){});
    	Assert.assertTrue(carModels.size() == 1);
    	CarModel carModel = carModels.get(0);
    	Assert.assertEquals(this.carModel.getName(), carModel.getName());
    	Assert.assertEquals(this.carModel.getYearStart(), carModel.getYearStart());
    	Assert.assertEquals(this.carModel.getYearEnd(), carModel.getYearEnd());
    }
    
	@Test
    public void testAddCarPosition() throws Exception{
    	Map<String, String> formData = new HashMap<>();
        formData.put("kind.name", carPosition.getKind().getName());
        formData.put("model.name", carPosition.getModel().getName());
        formData.put("yearIssue", carPosition.getYearIssue());
        formData.put("od", String.valueOf(carPosition.getOd()));
        formData.put("price", String.valueOf(carPosition.getPrice()));
        
    	Http.RequestBuilder request = Helpers.fakeRequest(Helpers.POST, "/carPosition/add").bodyForm(formData);
    	Result result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);

    	request = Helpers.fakeRequest(Helpers.GET, "/carPosition/all");
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
		List<CarPosition> carPositions = new ObjectMapper().readValue(Helpers.contentAsString(result), new TypeReference<List<CarPosition>>(){});
		Assert.assertTrue(carPositions.size() == 1);
		CarPosition carPosition = carPositions.get(0);

    	Assert.assertEquals(this.carPosition.getKind().getName(), carPosition.getKind().getName());
    	Assert.assertEquals(this.carPosition.getModel().getName(), carPosition.getModel().getName());
    	Assert.assertEquals(this.carPosition.getOd(), carPosition.getOd());
    	Assert.assertEquals(this.carPosition.getPrice(), carPosition.getPrice());
    	Assert.assertEquals(this.carPosition.getYearIssue(), carPosition.getYearIssue());
    }
}
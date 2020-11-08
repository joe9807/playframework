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
    public void testActualRoute() {
        Http.RequestBuilder request = new Http.RequestBuilder().method(Helpers.GET).uri("/");

        Result result = Helpers.route(app, request);
        Assert.assertEquals(Status.OK, result.status());
    }
    
    @Test
    public void testCarKind() throws Exception{
    	//Create
    	Map<String, String> formData = createCarKind();

    	//Read
    	Http.RequestBuilder request = Helpers.fakeRequest(Helpers.GET, "/carKind/all");
    	Result result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	List<CarKind> carKinds = new ObjectMapper().readValue(Helpers.contentAsString(result), new TypeReference<List<CarKind>>(){});
    	Assert.assertEquals(carKinds.size(), 1);
    	CarKind carKind = carKinds.get(0);
    	Assert.assertEquals(this.carKind.getName(), carKind.getName());
    	Assert.assertEquals(this.carKind.getCountry(), carKind.getCountry());
    	
    	//Update
    	formData.put("id", String.valueOf(carKind.getId()));
    	formData.put("name", carKind.getName()+"_set");
        formData.put("country", carKind.getCountry()+"_set");
    	request = Helpers.fakeRequest(Helpers.POST, "/carKind/update").bodyForm(formData);
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	//Read
    	request = Helpers.fakeRequest(Helpers.GET, "/carKind/all");
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	carKinds = new ObjectMapper().readValue(Helpers.contentAsString(result), new TypeReference<List<CarKind>>(){});
    	Assert.assertTrue(carKinds.size() == 1);
    	carKind = carKinds.get(0);
    	Assert.assertEquals(this.carKind.getName()+"_set", carKind.getName());
    	Assert.assertEquals(this.carKind.getCountry()+"_set", carKind.getCountry());
    	
    	//Delete
    	request = Helpers.fakeRequest(Helpers.POST, "/carKind/delete").bodyForm(formData);
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	request = Helpers.fakeRequest(Helpers.GET, "/carKind/all");
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	Assert.assertEquals(Helpers.contentAsString(result), "[]");
    }
    
    private Map<String, String> createCarKind(){
    	Map<String, String> formData = new HashMap<>();
        formData.put("name", carKind.getName());
        formData.put("country", carKind.getCountry());
        
    	Http.RequestBuilder request = Helpers.fakeRequest(Helpers.POST, "/carKind/add").bodyForm(formData);
    	Result result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	return formData;
    }
    
    private Map<String, String> createCarModel(){
    	Map<String, String> formData = new HashMap<>();
        formData.put("name", carModel.getName());
        formData.put("yearStart", carModel.getYearStart());
        formData.put("yearEnd", carModel.getYearEnd());
        
    	Http.RequestBuilder request = Helpers.fakeRequest(Helpers.POST, "/carModel/add").bodyForm(formData);
    	Result result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	return formData;
    }
    
    @Test
    public void testCarModel() throws Exception{
    	//Create
    	Map<String, String> formData = createCarModel();

    	//Read
    	Http.RequestBuilder request = Helpers.fakeRequest(Helpers.GET, "/carModel/all");
    	Result result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	List<CarModel> carModels = new ObjectMapper().readValue(Helpers.contentAsString(result), new TypeReference<List<CarModel>>(){});
    	Assert.assertTrue(carModels.size() == 1);
    	CarModel carModel = carModels.get(0);
    	Assert.assertEquals(this.carModel.getName(), carModel.getName());
    	Assert.assertEquals(this.carModel.getYearStart(), carModel.getYearStart());
    	Assert.assertEquals(this.carModel.getYearEnd(), carModel.getYearEnd());
    	
    	//Update
    	formData.put("id", String.valueOf(carModel.getId()));
    	formData.put("name", carModel.getName()+"_set");
        formData.put("yearStart", "1927");
        formData.put("yearEnd", "1930");
    	request = Helpers.fakeRequest(Helpers.POST, "/carModel/update").bodyForm(formData);
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	//Read
    	request = Helpers.fakeRequest(Helpers.GET, "/carModel/all");
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	carModels = new ObjectMapper().readValue(Helpers.contentAsString(result), new TypeReference<List<CarModel>>(){});
    	Assert.assertTrue(carModels.size() == 1);
    	carModel = carModels.get(0);
    	Assert.assertEquals(this.carModel.getName()+"_set", carModel.getName());
    	Assert.assertEquals("1927", carModel.getYearStart());
    	Assert.assertEquals("1930", carModel.getYearEnd());
    	
    	//Delete
    	request = Helpers.fakeRequest(Helpers.POST, "/carModel/delete").bodyForm(formData);
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	request = Helpers.fakeRequest(Helpers.GET, "/carModel/all");
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	Assert.assertEquals(Helpers.contentAsString(result), "[]");
    }
    
	@Test
    public void testCarPosition() throws Exception{
		createCarKind();
		createCarModel();
		
		//Create
    	Map<String, String> formData = new HashMap<>();
        formData.put("kind.name", carPosition.getKind().getName());
        formData.put("model.name", carPosition.getModel().getName());
        formData.put("yearIssue", carPosition.getYearIssue());
        formData.put("od", String.valueOf(carPosition.getOd()));
        formData.put("price", String.valueOf(carPosition.getPrice()));
        
    	Http.RequestBuilder request = Helpers.fakeRequest(Helpers.POST, "/carPosition/add").bodyForm(formData);
    	Result result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);

    	//Read
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
    	
    	//search
    	request = Helpers.fakeRequest(Helpers.GET, "/carPosition/search").bodyForm(formData);
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
		carPositions = new ObjectMapper().readValue(Helpers.contentAsString(result), new TypeReference<List<CarPosition>>(){});
		Assert.assertTrue(carPositions.size() == 1);
		carPosition = carPositions.get(0);

    	Assert.assertEquals(this.carPosition.getKind().getName(), carPosition.getKind().getName());
    	Assert.assertEquals(this.carPosition.getModel().getName(), carPosition.getModel().getName());
    	Assert.assertEquals(this.carPosition.getOd(), carPosition.getOd());
    	Assert.assertEquals(this.carPosition.getPrice(), carPosition.getPrice());
    	Assert.assertEquals(this.carPosition.getYearIssue(), carPosition.getYearIssue());
    	
    	//Update
    	formData.put("id", String.valueOf(carPosition.getId()));
        formData.put("yearIssue", "1940");
        formData.put("od", String.valueOf("1"));
        formData.put("price", String.valueOf("2"));
        
        request = Helpers.fakeRequest(Helpers.POST, "/carPosition/update").bodyForm(formData);
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	//Read
    	request = Helpers.fakeRequest(Helpers.GET, "/carPosition/all");
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
		carPositions = new ObjectMapper().readValue(Helpers.contentAsString(result), new TypeReference<List<CarPosition>>(){});
		Assert.assertTrue(carPositions.size() == 1);
		carPosition = carPositions.get(0);

    	Assert.assertEquals(this.carPosition.getKind().getName(), carPosition.getKind().getName());
    	Assert.assertEquals(this.carPosition.getModel().getName(), carPosition.getModel().getName());
    	Assert.assertEquals("1940", carPosition.getYearIssue());
    	Assert.assertEquals(1, carPosition.getOd());
    	Assert.assertEquals(2, carPosition.getPrice());
    	
    	//Delete
    	request = Helpers.fakeRequest(Helpers.POST, "/carPosition/delete").bodyForm(formData);
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	
    	request = Helpers.fakeRequest(Helpers.GET, "/carPosition/all");
    	result = Helpers.route(app, request);
    	Assert.assertTrue(result.status() == Status.OK || result.status() == Status.SEE_OTHER);
    	Assert.assertEquals(Helpers.contentAsString(result), "[]");
    }
}
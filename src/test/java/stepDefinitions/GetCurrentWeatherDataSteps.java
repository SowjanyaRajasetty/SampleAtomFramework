package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.ResponseValidator;
import resources.RestAssuredHelper;
import resources.RestContext;
import resources.CommonUtilities;
import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetCurrentWeatherDataSteps extends CommonUtilities {

	RestContext restContext;
	RequestSpecification request = given();

	public GetCurrentWeatherDataSteps(RestContext restContext) {
		this.restContext = restContext;
	}

	public static Logger log = LogManager.getLogger(GetCurrentWeatherDataSteps.class.getName());

	private String api = null;
	private String path = null;
	private String method = null;

	private static boolean apiTest;

	public static boolean isApiTest() {
		return apiTest;
	}

	public static void setApiTest(boolean api) {
		apiTest = api;
	}

	public void resetRest() {
		request = given();
		restContext.getRestData().setRequest(request);
	}

	@Given("^a rest api \"([^\"]*)\"$")
	public void a_url_something(String url) throws Throwable {
		url = readfromPropertiesFile(url);
		restContext.getRestData().setRequest(request);
		api = url;
		RestAssuredHelper.setBaseURI(restContext.getRestData().getRequest(), url);
		setApiTest(true);
	}

	@And("^(form parameters|query parameters|path parameters|parameters)$")
	public void query_parameters(String type, Map<String, String> map) throws Throwable {
		map.forEach((key, val) -> {
			RestAssuredHelper.setParam(restContext.getRestData().getRequest(), type, key, val);
		});
	}

	@And("^user requests (GET|PUT|POST|PATCH|DELETE)$")
	public void request_with_something(String apiMethod) throws Throwable {
		this.method = apiMethod;
		Response response = RestAssuredHelper.callAPI(restContext.getRestData().getRequest(), apiMethod);
		restContext.getRestData().setResponse(response);
		log.info(response.asPrettyString());
	}

	@Then("^verify the status code is (\\d+)$")
	public void verify_the_status_code_as_something(int statusCode) throws Throwable {
		RestAssuredHelper.checkStatus(restContext.getRestData(), statusCode);
	}

	@And("^verify that the response body contains$")
	public void responseBodyValid(DataTable table) throws IOException {
		String responseString = restContext.getRestData().getRespString();
		RestAssuredHelper.responseContains(table.asList(ResponseValidator.class), responseString);
	}
    @And("^the response matches the json schema \"(.*)\"$")
    public void matchJSONSchema(String path) {
    	String schemaLib = System.getProperty("user.dir") + "\\src\\test\\java\\schemaLibrary\\"+path;
        RestAssuredHelper.checkSchema(restContext.getRestData(), schemaLib);
    }
    @Then("^verify that the response header contains$")
    public void verifyHeader(List<List<String>> list) {
        Object val;
        String key;
        String matcher;
        for (List<String> row : list) {
            if (row.size() == 2) {
                matcher = "equals";
                key = row.get(0);
                val = row.get(1);
            } else {
                matcher = row.get(1);
                key = row.get(0);
                val = row.get(2);
            }
            RestAssuredHelper.checkHeader(restContext.getRestData(), key, matcher, val);
        }
    }
}
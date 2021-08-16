package resources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import stepDefinitions.GetCurrentWeatherDataSteps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import io.restassured.module.jsv.JsonSchemaValidator;

public class RestAssuredHelper {

	public static RequestSpecification setBasicAuth(RequestSpecification request, String uname, String pword) {
		return request.auth().preemptive().basic(uname, pword);
	}

	public static RequestSpecification setChallengedBasicAuth(RequestSpecification request, String uname,
			String pword) {
		return request.auth().basic(uname, pword);
	}

	public static RequestSpecification setBaseURI(RequestSpecification request, String uri) {
		return request.baseUri(uri);
	}

	public static RequestSpecification setBasePath(RequestSpecification request, String basepath) {
		return request.basePath(basepath);
	}

	public static RequestSpecification setPort(RequestSpecification request, int port) {
		return request.port(port);
	}

	public static RequestSpecification setHeader(RequestSpecification request, String key, String val) {
		return request.header(key, val);
	}

	public static RequestSpecification setParam(RequestSpecification request, String type, String key, String val) {
		switch (type) {
		case "parameters":
			request.param(key, val);
			break;
		case "form parameters":
			request.formParam(key, val);
			break;
		case "query parameters":
			request.queryParam(key, val);
			break;
		case "path parameters":
			request.pathParam(key, val);
			break;
		}

		return request;
	}

	public static RequestSpecification setParamList(RequestSpecification request, String key, List<String> val) {
		return request.param(key, val);
	}

	public static RequestSpecification setBody(RequestSpecification request, String content) {
		return request.body(content);
	}

	public static Response callAPI(RequestSpecification request, String method) {
		Response response = null;
		switch (method) {
		case "GET":
			response = request.get();
			break;
		case "PUT":
			response = request.put();
			break;
		case "POST":
			response = request.post();
			break;
		case "PATCH":
			response = request.patch();
			break;
		case "DELETE":
			response = request.delete();
			break;
		}

		return response;
	}

	public static void checkStatus(RestData restData, int statusCode) {
		restData.getRespValidator().assertThat().statusCode(statusCode);
	}

	public static void checkStatus(RestData restData, String msg) {
		restData.getRespValidator().assertThat().statusLine(containsString(msg));
	}

	public static void checkResponseTime(RestData restData, long duration) {
		restData.getRespValidator().assertThat().time(lessThan(duration));
	}

	public static void checkHeader(RestData restData, String key, String matcher, Object val) {
		String act = restData.getResponse().header(key);

		switch (matcher) {
		case "equals":
			assertThat(act, equalTo(val));
			break;
		case "regex":
			assertThat("value " + act.toString() + " does not match regex " + val.toString(),
					act.toString().matches(val.toString()), is(true));
			break;
		case "isNull":
			assertThat(act, nullValue());
			break;
		case "!isNull":
			assertThat(act, not(nullValue()));
			break;
		}
	}

	public static void checkBody(JsonPath jsonPath, Object[] obj, String element, String matcher) {
		Object act = jsonPath.get(element);// from(respString).get(element);
		Object nullObj = null;
		List<Object> list = null;

		switch (matcher) {
		case "is":
			assertThat(act, is(obj[0]));
			break;
		case "equals":
			assertThat(act, equalTo(obj[0]));
			break;
		case "hasItem":
			list = jsonPath.get(element);
			assertThat(list, hasItem(obj[0]));
			break;
		case "hasItems":
			list = jsonPath.get(element);
			assertThat(list, hasItems(obj));
			break;
		case "contains":
			list = jsonPath.get(element);
			assertThat(list, contains(obj));
			break;
		case "containsAnyOrder":
			list = jsonPath.get(element);
			assertThat(list, containsInAnyOrder(obj));
			break;
		case "isNull":
			if (act instanceof List) {
				list = jsonPath.get(element);
				assertThat(list, contains(nullObj));
			} else {
				assertThat(act, nullValue());
			}
			break;
		case "isEmpty":
			if (act instanceof List) {
				list = jsonPath.get(element);
				assertThat(list, is(empty()));
			} else {
				assertThat(act.toString().trim(), equalTo("{}"));
			}
			break;
		case "startsWith":
			assertThat(act.toString(), startsWith(obj[0].toString()));
			break;
		case "endsWith":
			assertThat(act.toString(), endsWith(obj[0].toString()));
			break;
		case "containsString":
			assertThat(act.toString(), containsString(obj[0].toString()));
			break;
		case "!isNull":
			if (act instanceof List) {
				list = jsonPath.get(element);
				assertThat(list, not(contains(nullObj)));
			} else {
				assertThat(act, not(nullValue()));
			}
			break;
		}
	}

	public static void responseContains(List<ResponseValidator> table, String responseString) {

		table.forEach((data) -> {
			ArrayList<Object> exp = new ArrayList<Object>();
			String[] str = null;
			if (!data.getMatcher().equalsIgnoreCase("regex") && data.getValue().length() > 1
					&& data.getValue().substring(0, 1).equalsIgnoreCase("[")) {
				str = data.getValue().substring(1, data.getValue().length() - 1).split(",");
			} else {
				str = new String[1];
				str[0] = data.getValue();
			}

			for (String s : str) {
				if (data.getType().equals("int")) {
					exp.add(Integer.parseInt(s));
				} else if (data.getType().equals("float")) {
					exp.add(Float.parseFloat(s));
				} else if (data.getType().equals("boolean")) {
					exp.add(Boolean.parseBoolean(s));
				} else if (data.getType().equals("long")) {
					exp.add(Long.parseLong(s));
				} else {
					exp.add(s);
				}
			}

			Object[] obj = exp.toArray(new Object[exp.size()]);
			JsonPath jsonPath = new JsonPath(responseString);
			checkBody(jsonPath, obj, data.getElement(), data.getMatcher());
			try {
				checkBody(jsonPath, obj, data.getElement(), data.getMatcher());
			} catch (AssertionError e) {
				TestContext.getInstance().sa().assertNull(e.getMessage());
			}
		});
		TestContext.getInstance().sa().assertAll();
	}
    public static void checkSchema(RestData restData, String path) {
    	restData.getRespValidator().body(JsonSchemaValidator.matchesJsonSchema(new File(path)));
    }
}
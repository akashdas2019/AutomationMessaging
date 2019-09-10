package narvar.utilities;

import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RestClient {

    RequestSpecification httpRequest;

    public RestClient(String baseURI, String port) {
        if (baseURI == null) {
            return;
        }
        RestAssured.baseURI = baseURI;
        if (port != null) {
            RestAssured.port = Integer.valueOf(port);
        }

        httpRequest = RestAssured.given();
    }

    public RestClient(String baseUriPortOptional) {
        if (baseUriPortOptional.contains(":")) {
            String baseURI = baseUriPortOptional.split(":")[0];
            String port = baseUriPortOptional.split(":")[1];
            if (baseURI == null) {
                return;
            }

            if (port != null) {
                RestAssured.port = Integer.valueOf(port);
            }
            RestAssured.baseURI = baseURI;
        } else {
            RestAssured.baseURI = baseUriPortOptional;
        }

        httpRequest = RestAssured.given();
    }

    public Response getMethod(String methodURI, int expectedStatusCode) {
        return httpRequest.request(Method.GET, methodURI).then().statusCode(expectedStatusCode).extract().response();
    }

    public Response getMethodWithQueryParams(String methodURI, int expectedStatusCode, Map<String, String> queryParams) {

        RequestSpecification requestSpecification = httpRequest;
        for (String key: queryParams.keySet()) {
            requestSpecification.queryParam(key, queryParams.get(key));
        }

        return requestSpecification.get(methodURI).then().statusCode(expectedStatusCode).extract().response();
    }

    public Response getMethodWithQueryParamsAndSpecificHeaders(String methodURI, int expectedStatusCode, Map<String, String> queryParams, Map<String, String> headers) {

        RequestSpecification requestSpecification = httpRequest;
        for (String key: queryParams.keySet()) {
            requestSpecification.queryParam(key, queryParams.get(key));
        }

        for (String key: headers.keySet()) {
            Header header = new Header(key, headers.get(key));
            requestSpecification.header(header);
        }

        return requestSpecification.get(methodURI).then().statusCode(expectedStatusCode).extract().response();
    }

    public Response postMethod(String methodURI, int expectedStatusCode, String jsonString) {

        httpRequest.header("Content-Type", "application/json");

        // Add the Json to the body of the request
        httpRequest.body(jsonString);

        // Post the request and check the response
        return httpRequest.post(methodURI).then().statusCode(expectedStatusCode).extract().response();
    }

    public Response postMethodWithSpecificHeaders(String methodURI, int expectedStatusCode, String jsonString, Map<String, String> headers) {

        RequestSpecification requestSpecification = httpRequest;
        for (String key: headers.keySet()) {
            Header header = new Header(key, headers.get(key));
            requestSpecification.header(header);
        }

        // Add the Json to the body of the request
        requestSpecification.body(jsonString);

        // Post the request and check the response
        return requestSpecification.post(methodURI).then().statusCode(expectedStatusCode).extract().response();
    }

    public Response putMethod(String methodURI, int expectedStatusCode, String jsonString) {

        httpRequest.header("Content-Type", "application/json");

        // Add the Json to the body of the request
        httpRequest.body(jsonString);

        // Post the request and check the response
        return httpRequest.put(methodURI).then().statusCode(expectedStatusCode).extract().response();
    }

    public Response puttMethodWithSpecificHeaders(String methodURI, int expectedStatusCode, String jsonString, Map<String, String> headers) {

        RequestSpecification requestSpecification = httpRequest;
        for (String key: headers.keySet()) {
            Header header = new Header(key, headers.get(key));
            requestSpecification.header(header);
        }

        // Add the Json to the body of the request
        requestSpecification.body(jsonString);

        // Post the request and check the response
        return requestSpecification.put(methodURI).then().statusCode(expectedStatusCode).extract().response();
    }

    public Response deleteMethod(String methodURI, int expectedStatusCode) {
        return httpRequest.request(Method.DELETE, methodURI).then().statusCode(expectedStatusCode).extract().response();
    }

    public Response deleteMethodWithQueryParams(String methodURI, int expectedStatusCode, Map<String, String> queryParams) {

        RequestSpecification requestSpecification = httpRequest;
        for (String key: queryParams.keySet()) {
            requestSpecification.queryParam(key, queryParams.get(key));
        }

        return requestSpecification.delete(methodURI).then().statusCode(expectedStatusCode).extract().response();
    }

    public Response deleteMethodWithQueryParamsAndSpecificHeaders(String methodURI, int expectedStatusCode, Map<String, String> queryParams, Map<String, String> headers) {

        RequestSpecification requestSpecification = httpRequest;
        for (String key: queryParams.keySet()) {
            requestSpecification.queryParam(key, queryParams.get(key));
        }

        for (String key: headers.keySet()) {
            Header header = new Header(key, headers.get(key));
            requestSpecification.header(header);
        }

        return requestSpecification.delete(methodURI).then().statusCode(expectedStatusCode).extract().response();
    }


    public String getResponseBody(Response response) {
        return response.getBody().asString();
    }

    public int getResponseCode(Response response) {
        return response.getStatusCode();
    }

    public Map<String, String> getResponseCookies(Response response) {
        return response.getCookies();
    }

    public Headers getResponseHeaders(Response response) {
        return response.getHeaders();
    }

    public void printAllResponseHeaders(Response response) {
        Headers headers = response.getHeaders();
        for(Header header : headers) {
            System.out.println("Header: " + header.getName() + " Value: " + header.getValue());
        }
    }

    public Cookies getDetailedCookies(Response response) {
        return response.getDetailedCookies();
    }

    public String getSessionID(Response response) {
        return response.getSessionId();
    }

    public String getStatusMessage(Response response) {
        return response.getStatusLine();
    }

    public long getTime(Response response) {
        return response.getTimeIn(TimeUnit.MILLISECONDS);
    }

    public JsonPath getJsonResponse(Response response) {
        return response.jsonPath();
    }

    public JsonPath getJsonResponseData(Response response, String parameter) {
        return response.jsonPath().get(parameter);
    }
}

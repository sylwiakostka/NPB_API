package Utilities;

import Constants.Paths;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.lessThan;

public class Utilities {

    public static String ENDPOINT;
    public static RequestSpecBuilder REQUEST_BUILDER;
    public static RequestSpecification REQUEST_SPEC;
    public static ResponseSpecBuilder RESPONSE_BUILDER;
    public static ResponseSpecification RESPONSE_SPEC;


    public static void setEndPoint(String epoint) {
        ENDPOINT = epoint;
    }

    public static String getAccessToken() {

        Map<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("login", Paths.USERNAME);
        requestBody.put("password", Paths.ACCESS_KEY);

        String accessToken =
                given()
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .header("Accept", "application/json")
                        .header("Accept-Charset", "utf-8")
                        .header("x-api-key", "46B9D48A125733B4C9226CE570007")
                        .body(requestBody)
                        .when()
                        .post("https://taxi.demo.eo.pl/taxi-business-client-web/rest/account/userLogin")
                        .then().extract().path("result.accessToken");
        return accessToken;
    }


    public static RequestSpecification getRequestSpecification() {
        REQUEST_BUILDER = new RequestSpecBuilder();
        REQUEST_BUILDER.setBaseUri(Paths.BASE_URI);
        REQUEST_BUILDER.setBasePath(Paths.BASE_PATH);
        REQUEST_BUILDER.addHeader("Content-Type", "application/json; charset=UTF-8");
        REQUEST_BUILDER.addHeader("Accept", "application/json");
        REQUEST_BUILDER.addHeader("Accept-Charset", "utf-8");
        REQUEST_BUILDER.addHeader("x-api-key", "46B9D48A125733B4C9226CE570007");
        REQUEST_BUILDER.addHeader("x-business-token", Utilities.getAccessToken());

        REQUEST_SPEC = REQUEST_BUILDER.build();
        return REQUEST_SPEC;
    }

    public static ResponseSpecification getResponseSpecification() {
        RESPONSE_BUILDER = new ResponseSpecBuilder();
        RESPONSE_BUILDER.expectStatusCode(200);
        RESPONSE_BUILDER.expectResponseTime(lessThan(3L), TimeUnit.SECONDS);
        RESPONSE_BUILDER.expectContentType(ContentType.JSON);
        RESPONSE_BUILDER.setDefaultParser(Parser.JSON);
        RESPONSE_BUILDER.expectBody("answer", is("OK"));
        RESPONSE_BUILDER.expectBody("errors",isEmptyOrNullString());
        RESPONSE_SPEC = RESPONSE_BUILDER.build();
        return RESPONSE_SPEC;
    }

    public static void resetBathPath() {
        RestAssured.basePath = null;
    }

    public static JsonPath getJsonPath(Response res) {
        String path = res.asString();
        return new JsonPath(path);
    }

    public static XmlPath getXmlPath(Response res) {
        String path = res.asString();
        return new XmlPath(path);
    }

    public static RequestSpecification createQueryParam(RequestSpecification reqSpec, String param, String value) {
        return reqSpec.queryParam(param, value);
    }

    public static RequestSpecification createQueryParams(RequestSpecification reqSpec, Map<String, String> queryMap) {
        return reqSpec.queryParams(queryMap);
    }

    public static RequestSpecification createPathParam(RequestSpecification reqSpec, String param, String value) {
        return reqSpec.pathParam(param, value);
    }

    public static Response getResponse() {
        return given().get(ENDPOINT);
    }

}
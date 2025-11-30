package movieapi.api.spec;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class CinescopeSpecs {

    public static RequestSpecification defaultRequestSpecs() {
        return new RequestSpecBuilder()
                .setBaseUri("https://api.cinescope.t-qa.ru")
                .addHeader("Content-Type", "application/json")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification successSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification successCreationSpecs() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }
}

package movieapi.api.spec;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import movieapi.config.ConfigProvider;

public class ReviewSpecs {

    public static RequestSpecification reviewRequestSpec(String token) {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigProvider.getApiBaseUrl())
                .addHeader("Authorization", "Bearer " + token)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }
    public static ResponseSpecification deleteReviewSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

}

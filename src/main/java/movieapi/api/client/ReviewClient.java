package movieapi.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static movieapi.api.spec.ReviewSpecs.deleteReviewSpec;
import static movieapi.api.spec.ReviewSpecs.reviewRequestSpec;

public class ReviewClient {

    @Step("DELETE /movies/{movieId}/reviews")
    public Response deleteReviewForMovie(Long movieId, String userId, String token) {
        return given()
                .spec(reviewRequestSpec(token))
                .pathParam("movieId", movieId)
                .queryParam("userId", userId)
                .when()
                .delete("/movies/{movieId}/reviews")
                .then()
                .log().all()
                .extract().response();
    }
}

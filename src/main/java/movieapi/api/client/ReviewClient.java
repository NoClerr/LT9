package movieapi.api.client;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static movieapi.api.spec.ReviewSpecs.deleteReviewSpec;
import static movieapi.api.spec.ReviewSpecs.reviewRequestSpec;

public class ReviewClient {

    @Step("DELETE /movies/{movieId}/reviews")
    public void deleteReviewForMovie(Long movieId,String userId, String token) {
        given()
                .spec(reviewRequestSpec(token))
                .pathParam("movieId", movieId)
//                .queryParam("movieId", movieId)
                .queryParam("userId", userId)
                .when()
                .delete("/movies/{movieId}/reviews")
                .then()
                .spec(deleteReviewSpec());
    }
}

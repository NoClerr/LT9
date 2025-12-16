package movieapi.api.client;

import io.restassured.response.Response;
import movieapi.api.spec.ReviewSpecs;

import static io.restassured.RestAssured.given;
import static movieapi.api.spec.ReviewSpecs.reviewRequestSpec;

public class ReviewClient {

    private static final String REVIEW_ID = "/movies/{movieId}/reviews";

    public Response deleteReviewForMovie(Long movieId, String userId, String token) {
        return given()
                .spec(ReviewSpecs.reviewRequestSpec(token))
                .pathParam("movieId", movieId)
                .queryParam("userId", userId)
                .when()
                .delete(REVIEW_ID);
    }
}

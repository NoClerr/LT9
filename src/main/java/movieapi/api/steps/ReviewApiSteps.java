package movieapi.api.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import movieapi.api.client.ReviewClient;
import movieapi.config.ConfigProvider;

public class ReviewApiSteps {

    private final ReviewClient reviewClient = new ReviewClient();
    private final AuthApiSteps authApiSteps = new AuthApiSteps();


    @Step("Удаляем отзыв для фильма")
    public Response deleteReviewForMovie(Long movieId, String userId, String token) {
        return reviewClient.deleteReviewForMovie(movieId, userId, token);
    }
}

package movieapi.api.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import movieapi.api.client.ReviewClient;
import movieapi.config.ConfigProvider;

public class ReviewApiSteps {

    private final ReviewClient reviewClient = new ReviewClient();
    private final AuthApiSteps authApiSteps = new AuthApiSteps();
    private Long movieId = 56L;


    @Step("Удаляем отзыв для фильма")
    public Response deleteReviewForMovie(Long movieId) {
        String token = authApiSteps.getToken();
        String userId = ConfigProvider.getUserId();
        return reviewClient.deleteReviewForMovie(movieId, userId, token);
    }
}

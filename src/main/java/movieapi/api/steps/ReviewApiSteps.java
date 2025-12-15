package movieapi.api.steps;

import io.qameta.allure.Step;
import movieapi.api.client.ReviewClient;
import movieapi.config.ConfigProvider;

public class ReviewApiSteps {

    private final ReviewClient reviewClient = new ReviewClient();
    private final AuthApiSteps authApiSteps = new AuthApiSteps();


    @Step("Удаляем отзыв для фильма с известным токеном")
    public void deleteReviewForMovie(Long movieId, String userId, String token) {
        reviewClient.deleteReviewForMovie(movieId, userId, token);
    }
    @Step("Удаляем отзыв для фильма ")
    public void deleteReviewForMovie(Long movieId) {
        String token = authApiSteps.getToken();
        String userId = ConfigProvider.getUserId();
        deleteReviewForMovie(movieId, userId, token);
    }
}

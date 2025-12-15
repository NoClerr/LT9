package movieapi.api.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import movieapi.api.client.ReviewClient;
import movieapi.config.ConfigProvider;

public class ReviewApiSteps {

    private final ReviewClient reviewClient = new ReviewClient();
    private final AuthApiSteps authApiSteps = new AuthApiSteps();


    @Step("Удаляем отзыв для фильма с известным токеном")
    public void deleteReviewForMovie(Long movieId, String userId, String token) {
        Response resp = reviewClient.deleteReviewForMovie(movieId, userId, token);
        System.out.println("DELETE status = " + resp.statusCode());;
    }
    @Step("Удаляем отзыв текущего пользователя для фильма {movieId}")
    public void deleteReviewForMovie(Long movieId) {
        String token = authApiSteps.getToken();
        String userId = ConfigProvider.getUserId(); // UUID строкой, добавь в ConfigProvider
        reviewClient.deleteReviewForMovie(movieId, userId, token);
    }
}

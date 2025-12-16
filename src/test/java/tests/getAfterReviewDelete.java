//package tests;
//
//import io.qameta.allure.Allure;
//import movieapi.api.steps.AuthApiSteps;
//import movieapi.api.steps.ReviewApiSteps;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//public class getAfterReviewDelete {
//
//    private final ReviewApiSteps reviewSteps = new ReviewApiSteps();
//    private final AuthApiSteps authSteps = new AuthApiSteps();
//
//
//    private String token;
//    private  Long movieId;
//    private String userId;
//
//
//    @Test
//    @DisplayName("Хуета ебаная")
//    void getAfterReviewDelete() {
//        token = Allure.step("Получаем токен авторизации", () ->
//                authSteps.getToken());
//        if (token == null || movieId == null) {
//            return;
//        }
//        reviewSteps.deleteReviewForMovie(movieId,userId,token);
//    }
//}

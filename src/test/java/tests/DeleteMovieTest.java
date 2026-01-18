package tests;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import movieapi.api.dto.movies.request.CreateMovieRequestDto;
import movieapi.api.dto.movies.response.GetMovieResponseDto;
import movieapi.api.steps.AuthApiSteps;
import movieapi.api.steps.MovieApiSteps;
import movietestdata.MovieTestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteMovieTest {

    private final AuthApiSteps authSteps = new AuthApiSteps();
    private final MovieApiSteps movieSteps = new MovieApiSteps();

    @Tag("smoke")
    @Test
    @DisplayName("DELETE /movies/{id} - успешное удаление фильма")
    void deleteMovieSuccess() {
       String token = Allure.step("Получаем токен авторизации", () ->
                authSteps.getToken());


        CreateMovieRequestDto request = Allure.step("Тело запроса для создания фильма", () ->
                MovieTestData.movieDeleteRequest());

        GetMovieResponseDto createResponse = movieSteps.createMovieSuccess(token, request);
        long id = createResponse.getId();

        Response deleteResponse = Allure.step("Удаляем фильм", () ->
                movieSteps.deleteMovie(token, id)
        );
        Allure.step("Проверяем статус удаления", () -> {
            assertThat(deleteResponse.getStatusCode()).isEqualTo(200);
        });
        Response afterDeleteGet = movieSteps.getMovieByInvalidId(id);

        Allure.step("Проверяем GET после удаления ", () -> {
            assertThat(afterDeleteGet.getStatusCode()).isEqualTo(404);
        });

    }
}

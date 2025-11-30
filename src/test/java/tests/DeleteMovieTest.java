package tests;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import movieapi.api.dto.movies.request.CreateMovieRequestDto;
import movieapi.api.dto.movies.response.GetMovieResponseDto;
import movieapi.api.steps.AuthApiSteps;
import movieapi.api.steps.MovieApiSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteMovieTest {

    private final AuthApiSteps authSteps = new AuthApiSteps();
    private final MovieApiSteps movieSteps = new MovieApiSteps();

    @Test
    @DisplayName("DELETE /movies/{id} - успешное удаление фильма")
    void createMovieSuccess() {
        String token = Allure.step("Получаем токен авторизации", () ->
                authSteps.loginAndGetToken(
                        "pozitiv971@gmail.com",
                        "U6r-F7X-knS-AbS"));


        CreateMovieRequestDto request = Allure.step("Тело запроса для создания фильма", () ->
                new CreateMovieRequestDto(
                        "Фильм автотест под DELETE" + UUID.randomUUID(),
                        "https://image.url",
                        50,
                        "Описание автотестового фильма под DELETE",
                        "MSK",
                        true,
                        1L
                ));

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

package tests;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import movieapi.api.dto.movies.requestdto.CreateMovieRequestDto;
import movieapi.api.dto.movies.responsedto.GetMovieResponseDto;
import movieapi.api.steps.AuthApiSteps;
import movieapi.api.steps.MovieApiSteps;
import movieapi.db.domain.Movie;
import movieapi.db.steps.MovieDbSteps;
import movieapi.util.DbCredentials;
import movieapi.util.DbUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PatchMovieTest {


    public final AuthApiSteps authSteps = new AuthApiSteps();
    public final MovieApiSteps movieSteps = new MovieApiSteps();
    private final MovieDbSteps movieDbSteps = new MovieDbSteps(DbUtils.getCredentials());


    @Test
    @DisplayName("PATCH /movies/{id} - Успешное редактирование фильма")
    void patchMovie_succes() {
        String token = Allure.step("Получаем токен авторизации", () ->
                authSteps.loginGiveToken(
                        "pozitiv971@gmail.com",
                        "U6r-F7X-knS-AbS"));

        CreateMovieRequestDto request = Allure.step("Тело запроса для создания фильма", () ->
                new CreateMovieRequestDto(
                        "Фильм автотест под PATCH" + UUID.randomUUID(),
                        "https://image.url",
                        350,
                        "Описание автотестового фильма под PATCH",
                        "MSK",
                        true,
                        1L
                ));


        GetMovieResponseDto createRequest = Allure.step("Отправляем POST /movies", () ->
                movieSteps.createMovie(token, request));

        long id = createRequest.getId();


        CreateMovieRequestDto patchRequest = new CreateMovieRequestDto(
                "Автотест фильм после PATCH " + UUID.randomUUID(),
                "https://new.image.url",
                999,
                "Обновлённое описание после PATCH",
                "SPB",
                false,
                1L
        );

        GetMovieResponseDto patchResponse = Allure.step("Редактируем фильм", () ->
                movieSteps.patchMovie(token, id, patchRequest)
        );
        Allure.step("Проверяем ответ PATCH", () -> {
            assertThat(patchResponse.getId()).isEqualTo(id);
            assertThat(patchResponse.getName()).isEqualTo(patchRequest.getName());
            assertThat(patchResponse.getDescription()).isEqualTo(patchRequest.getDescription());
            assertThat(patchResponse.getPrice()).isEqualTo(patchRequest.getPrice());
            assertThat(patchResponse.getLocation()).isEqualTo(patchRequest.getLocation());
            assertThat(patchResponse.getImageUrl()).isEqualTo(patchRequest.getImageUrl());
            assertThat(patchResponse.getPublished()).isEqualTo(patchRequest.isPublished());
            assertThat(patchResponse.getGenreId()).isEqualTo(patchRequest.getGenreId());
        });

        Movie movieDb = movieDbSteps.getMovieById(id);

        Allure.step("Проверяем что БД обновилась", () -> {
            assertThat(movieDb.getName()).isEqualTo(patchRequest.getName());
            assertThat(movieDb.getDescription()).isEqualTo(patchRequest.getDescription());
            assertThat(movieDb.getPrice()).isEqualTo(patchRequest.getPrice());
            assertThat(movieDb.getLocation()).isEqualTo(patchRequest.getLocation());
            assertThat(movieDb.getImageUrl()).isEqualTo(patchRequest.getImageUrl());
            assertThat(movieDb.getPublished()).isEqualTo(patchRequest.isPublished());
            assertThat(movieDb.getGenreId()).isEqualTo(patchRequest.getGenreId());
        });
        movieSteps.deleteMovie(token, id);

        int count = movieDbSteps.countMovieById(id);
        assertThat(count).isEqualTo(0);
    }

    @Test
    @DisplayName("PATCH /movies/{id} - Неуспешное редактирование фильма")
    void patchMovie_notAuth() {
        CreateMovieRequestDto patchRequest = new CreateMovieRequestDto(
                "Автотест фильм PATCH без авторизации",
                "https://image.url",
                99,
                "Описание фильма без авторизации",
                "SPB",
                true,
                1L
        );

        long moreId = 2;

        Response response = movieSteps.patchMovieNotAuth(moreId, patchRequest);

        Allure.step("Проверяем что PATCH без токена отдает 401", () -> {
            assertThat(response.getStatusCode()).isEqualTo(401);
        });
    }


}



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
import movieapi.util.DbName;
import movieapi.util.DbUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateMovieTest {

    private final AuthApiSteps authSteps = new AuthApiSteps();
    private final MovieApiSteps movieSteps = new MovieApiSteps();
    private final MovieDbSteps movieDbSteps = new MovieDbSteps(DbUtils.getCredentials());




    @Test
    @DisplayName("POST /movies - успешное создание фильма")
    void createMovie_succes() {
        String token = Allure.step("Получаем токен авторизации", () ->
                authSteps.loginGiveToken(
                        "pozitiv971@gmail.com",
                        "U6r-F7X-knS-AbS"));


        CreateMovieRequestDto request = Allure.step("Тело запроса для создания фильма", () ->
                new CreateMovieRequestDto(
                        "Фильм автотест " + UUID.randomUUID(),
                        "https://image.url",
                        200,
                        "Описание автотестового фильма",
                        "MSK",
                        true,
                        1L
                ));


        GetMovieResponseDto createRequest = Allure.step("Отправляем POST /movies", () ->
                movieSteps.createMovie(token, request));

        Allure.step("Проверяем ответ API на POST /movies", () -> {
            assertThat(createRequest.getId()).isNotNull();
            assertThat(createRequest.getName()).isEqualTo(request.getName());
            assertThat(createRequest.getPrice()).isEqualTo(request.getPrice());
            assertThat(createRequest.getDescription()).isEqualTo(request.getDescription());
            assertThat(createRequest.getImageUrl()).isEqualTo(request.getImageUrl());
            assertThat(createRequest.getLocation()).isEqualTo(request.getLocation());
            assertThat(createRequest.getGenreId()).isEqualTo(request.getGenreId());
            assertThat(createRequest.getGenre().getName()).isNotEmpty();
            assertThat(createRequest.getCreatedAt()).isNotEmpty();
        });

        long id = createRequest.getId();
        Movie movieDb = Allure.step("Проверяем, что фильм появился в БД", () ->
                movieDbSteps.getMovieById(id));


        Allure.step("Сравниваем данные в БД", () -> {
            assertThat(movieDb).isNotNull();
            assertThat(movieDb.getId()).isEqualTo(id);
            assertThat(movieDb.getName()).isEqualTo(request.getName());
            assertThat(movieDb.getPrice()).isEqualTo(request.getPrice());
            assertThat(movieDb.getDescription()).isEqualTo(request.getDescription());
            assertThat(movieDb.getImageUrl()).isEqualTo(request.getImageUrl());
            assertThat(movieDb.getLocation()).isEqualTo(request.getLocation());
            assertThat(movieDb.getPublished()).isEqualTo(request.isPublished());
            assertThat(movieDb.getGenreId()).isEqualTo(request.getGenreId());
        });


        Allure.step("Удаляем фильм", () ->
                movieSteps.deleteMovie(token, id));

        Response deleteAfter = movieSteps.getMovieIdRaw(id);
        Allure.step("Проверяем что фильм удалился", () -> {
            assertThat(deleteAfter.getStatusCode()).isEqualTo(404);
        });

        Allure.step("Проверяем, что фильма нет в БД", () -> {
            int count = movieDbSteps.countMovieById(id);
            assertThat(count).isEqualTo(0);
        });
    }

    @Test
    @DisplayName("POST /movies без авторизации")
    public void createMovie_notAuth() {

        CreateMovieRequestDto request = new CreateMovieRequestDto(
                "Негативный фильм " + UUID.randomUUID(),
                "https://image.url",
                200,
                "Описание",
                "MSK",
                true,
                1L
        );

        Response response = movieSteps.createMovieNotAuth(request);

        assertThat(response.getStatusCode()).isEqualTo(401);
    }
}


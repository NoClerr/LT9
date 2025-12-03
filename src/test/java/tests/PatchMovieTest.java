package tests;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import movieapi.api.dto.movies.request.CreateMovieRequestDto;
import movieapi.api.dto.movies.response.GetMovieResponseDto;
import movieapi.api.steps.AuthApiSteps;
import movieapi.api.steps.MovieApiSteps;
import apiTests.MovieTestData;
import movieapi.db.domain.Movie;
import movieapi.db.steps.MovieDbSteps;
import movieapi.util.DbName;
import movieapi.util.DbUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PatchMovieTest {


    public final AuthApiSteps authSteps = new AuthApiSteps();
    public final MovieApiSteps movieSteps = new MovieApiSteps();
    private final MovieDbSteps movieDbSteps = new MovieDbSteps(DbUtils.getCredentials(DbName.DB_MOVIES));

    private String token;
    private Long movieId;

    @AfterEach
    void getAfterDelete() {
        if (token == null || movieId == null) {
            return;
        }
        movieSteps.deleteMovie(token, movieId);
        token = null;
        movieId = null;
    }


    @Test
    @DisplayName("PATCH /movies/{id} - Успешное редактирование фильма")
    void patchMovieSuccess() {
        token = Allure.step("Получаем токен авторизации", () ->
                authSteps.getToken());

        CreateMovieRequestDto request = Allure.step("Тело запроса для создания фильма", () ->
                MovieTestData.moviePostRequestFromPatch());


        GetMovieResponseDto createRequest = Allure.step("Отправляем POST /movies", () ->
                movieSteps.createMovieSuccess(token, request));

        movieId = createRequest.getId();


        CreateMovieRequestDto patchRequest = Allure.step("Тело запроса для создания фильма", () ->
                MovieTestData.moviePatchRequest());

        GetMovieResponseDto createResponse = movieSteps.patchMovie(token, movieId, patchRequest);
        Allure.step("Проверяем ответ PATCH", () -> {
            assertThat(createResponse.getId()).isEqualTo(movieId);
            assertThat(createResponse.getName()).isEqualTo(patchRequest.getName());
            assertThat(createResponse.getDescription()).isEqualTo(patchRequest.getDescription());
            assertThat(createResponse.getPrice()).isEqualTo(patchRequest.getPrice());
            assertThat(createResponse.getLocation()).isEqualTo(patchRequest.getLocation());
            assertThat(createResponse.getImageUrl()).isEqualTo(patchRequest.getImageUrl());
            assertThat(createResponse.getPublished()).isEqualTo(patchRequest.isPublished());
            assertThat(createResponse.getGenreId()).isEqualTo(patchRequest.getGenreId());
        });

        Movie movieDb = movieDbSteps.getMovieById(movieId);

        Allure.step("Проверяем что БД обновилась", () -> {
            assertThat(movieDb.getName()).isEqualTo(patchRequest.getName());
            assertThat(movieDb.getDescription()).isEqualTo(patchRequest.getDescription());
            assertThat(movieDb.getPrice()).isEqualTo(patchRequest.getPrice());
            assertThat(movieDb.getLocation()).isEqualTo(patchRequest.getLocation());
            assertThat(movieDb.getImageUrl()).isEqualTo(patchRequest.getImageUrl());
            assertThat(movieDb.getPublished()).isEqualTo(patchRequest.isPublished());
            assertThat(movieDb.getGenreId()).isEqualTo(patchRequest.getGenreId());
        });
    }

    @Test
    @DisplayName("PATCH /movies/{id} - Неуспешное редактирование фильма")
    void patchMovieWithoitToken() {
        CreateMovieRequestDto request = Allure.step("Тело запроса для создания фильма ", () ->
                MovieTestData.moviePatchRequestWithoitToken());

        long moreId = 2;

        Response response = movieSteps.patchMovieWithoutAuth(moreId, request);

        Allure.step("Проверяем что PATCH без токена отдает 401", () -> {
            assertThat(response.getStatusCode()).isEqualTo(401);
        });
    }


}



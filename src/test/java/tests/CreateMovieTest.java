package tests;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import movieapi.api.dto.movies.request.CreateMovieRequestDto;
import movieapi.api.dto.movies.response.GetMovieResponseDto;
import movieapi.api.steps.AuthApiSteps;
import movieapi.api.steps.MovieApiSteps;
import movieapi.api.steps.ReviewApiSteps;
import movietestdata.MovieTestData;
import movieapi.db.domain.Movie;
import movieapi.db.steps.MovieDbSteps;
import movieapi.util.DbName;
import movieapi.util.DbUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateMovieTest {

    private final AuthApiSteps authSteps = new AuthApiSteps();
    private final MovieApiSteps movieSteps = new MovieApiSteps();
    private final MovieDbSteps movieDbSteps = new MovieDbSteps(DbUtils.getCredentials(DbName.DB_MOVIES));

    private String token;
    private Long createdMovieId;;

    @AfterEach
    void getAfterDelete() {
        if (token == null || createdMovieId == null) {
            return;
        }
        movieSteps.deleteMovie(token, createdMovieId);
        token = null;
        createdMovieId = null;
    }

    @Tag("smoke")
    @Test
    @DisplayName("POST /movies - успешное создание фильма")
    void createMovieSuccess() {
        token = Allure.step("Получаем токен авторизации", () ->
                authSteps.getToken());


        CreateMovieRequestDto request = MovieTestData.moviePostRequest();

        GetMovieResponseDto createResponse = movieSteps.createMovieSuccess(token, request);

        createdMovieId = createResponse.getId();

        Allure.step("Проверяем ответ API на POST /movies", () -> {
            assertThat(createResponse.getId()).isNotNull();
            assertThat(createResponse.getName()).isEqualTo(request.getName());
            assertThat(createResponse.getPrice()).isEqualTo(request.getPrice());
            assertThat(createResponse.getDescription()).isEqualTo(request.getDescription());
            assertThat(createResponse.getImageUrl()).isEqualTo(request.getImageUrl());
            assertThat(createResponse.getLocation()).isEqualTo(request.getLocation());
            assertThat(createResponse.getGenreId()).isEqualTo(request.getGenreId());
            assertThat(createResponse.getGenre().getName()).isNotEmpty();
            assertThat(createResponse.getCreatedAt()).isNotEmpty();
        });

        Movie movieDb = movieDbSteps.getMovieById(createdMovieId);

        Allure.step("Сравниваем данные в БД", () -> {
            assertThat(movieDb).isNotNull();
            assertThat(movieDb.getId()).isEqualTo(createdMovieId);
            assertThat(movieDb.getName()).isEqualTo(request.getName());
            assertThat(movieDb.getPrice()).isEqualTo(request.getPrice());
            assertThat(movieDb.getDescription()).isEqualTo(request.getDescription());
            assertThat(movieDb.getImageUrl()).isEqualTo(request.getImageUrl());
            assertThat(movieDb.getLocation()).isEqualTo(request.getLocation());
            assertThat(movieDb.getPublished()).isEqualTo(request.isPublished());
            assertThat(movieDb.getGenreId()).isEqualTo(request.getGenreId());
        });
    }

    @Tag("smoke")
    @Test
    @DisplayName("POST /movies без авторизации")
    public void createMovieWithoutToken() {

        CreateMovieRequestDto request = Allure.step("Тело запроса для создания фильма", () ->
                MovieTestData.moviePostRequestWithoitToken());

        Response response = movieSteps.createMovieWithoutToken(request);
        assertThat(response.getStatusCode()).isEqualTo(401);
    }
}




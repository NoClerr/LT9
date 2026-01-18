package tests;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import movieapi.api.dto.movies.response.GetMovieResponseDto;
import movieapi.api.steps.MovieApiSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetMovieTest {

    private final MovieApiSteps apiSteps = new MovieApiSteps();

    @Tag("smoke")
    @Test
    @DisplayName("GET /movies/{id} — успешное получение фильма")
    void getMovieById_success() {

        long testMovieId = 2;

        GetMovieResponseDto movie = apiSteps.getMovieById(testMovieId);
        Allure.step("Проверяем ответ API на GET /movies/{id}", () -> {
            assertThat(movie.getId()).isEqualTo(testMovieId);
            assertThat(movie.getName()).isNotBlank();
            assertThat(movie.getPrice()).isGreaterThan(0);
            assertThat(movie.getPublished()).isNotNull();
            assertThat(movie.getGenre()).isNotNull();
            assertThat(movie.getGenre().getName()).isNotBlank();
        });
    }

    @Tag("smoke")
    @Test
    @DisplayName("GET /movies/{id} — получение несущетсвующего фильма")
    void getMovieById_notFound() {
        long NotMovieId = 999999999;
        Response response = apiSteps.getMovieByInvalidId(NotMovieId);
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Tag("smoke")
    @Test
    @DisplayName("GET /movies/{id} — получние фильма при отррицательном id")
    void getMovieById_badRequest() {
        long badId = -1;
        Response response = apiSteps.getMovieByInvalidId(badId);
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

}

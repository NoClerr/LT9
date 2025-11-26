package movieapi.api.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import movieapi.api.client.MovieClient;
import movieapi.api.dto.movies.request.CreateMovieRequestDto;
import movieapi.api.dto.movies.response.GetMovieResponseDto;

public class MovieApiSteps {

    private final MovieClient client = new MovieClient();

    @Step("Получаем фильм по ID = {id}")
    public GetMovieResponseDto getMovieById(long id) {
        return client.getMovieById(id);
    }

    @Step("Получаем фильм по несуществующеему ID = {id}")
    public Response getMovieByInvalidId(long id) {
        return client.getMovieByIdInvalid(id);
    }

    @Step("Создаем фильм")
    public GetMovieResponseDto createMovieSuccess(String token, CreateMovieRequestDto request) {
        return client.createMovieSuccess(token, request);
    }

    @Step("Создаем фильм без авторизации")
    public Response createMovieWithoutToken(CreateMovieRequestDto request) {
        return client.createMovieWithoutToken(request);
    }

    @Step("Удаляем фильм по id = {id}")
    public Response deleteMovie(String token, long id) {
        return client.deleteMovie(token, id);
    }

    @Step("Редактируем фильм")
    public GetMovieResponseDto patchMovie(String token, long id, CreateMovieRequestDto request) {
        return client.patchMovie(token, id, request);
    }

    @Step("Редактируем фильм без авторизации")
    public Response patchMovieWithoutAuth(long id, CreateMovieRequestDto request) {
        return client.patchMovieWithoutAuth(id, request);
    }

}

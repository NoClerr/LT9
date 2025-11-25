package movieapi.api.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import movieapi.api.client.MovieClient;
import movieapi.api.dto.movies.requestdto.CreateMovieRequestDto;
import movieapi.api.dto.movies.responsedto.GetMovieResponseDto;

public class MovieApiSteps {

    private final MovieClient client = new MovieClient();

    @Step("Получаем фильм по ID = {id}")
    public GetMovieResponseDto getMovieById(long id) {
        return client.getMovieById(id);
    }

    @Step("Получаем фильм по несуществующеему ID = {id}")
    public Response getMovieIdRaw(long id) {
        return client.getMovieByIdBroken(id);
    }

    @Step("Создаем фильм")
    public GetMovieResponseDto createMovie(String token, CreateMovieRequestDto request) {
        return client.createMovie(token, request);
    }

    @Step("Создаем фильм без авторизации")
    public Response createMovieNotAuth(CreateMovieRequestDto request) {
        return client.createMovieBroken(request);
    }

    @Step("Удаляем фильм по id = {id}")
    public Response deleteMovie(String token, long id) {
        return client.deleteMovie(token, id);
    }

/*    @Step("Удаляем фильм без авторизации")
    public Response deleteMovieNotAuth(long id) {
        return client.deleteMovie("", id);
    }*/

    @Step("Редактируем фильм")
    public GetMovieResponseDto patchMovie(String token, long id, CreateMovieRequestDto request) {
        return client.patchMovie(token, id, request);
    }

    @Step("Редактируем фильм без авторизации")
    public Response patchMovieNotAuth(long id, CreateMovieRequestDto request) {
        return client.patchMovieBroken(id, request);
    }

}

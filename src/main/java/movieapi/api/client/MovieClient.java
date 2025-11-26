package movieapi.api.client;

import io.restassured.response.Response;
import movieapi.api.dto.movies.request.CreateMovieRequestDto;
import movieapi.api.dto.movies.response.GetMovieResponseDto;
import movieapi.api.spec.CinescopeSpecs;

import static io.restassured.RestAssured.given;

public class MovieClient {

    private static final String MOVIE_ID = "/movies/{id}";
    private static final String Movies = "/movies";

    public Response getMovieByIdInvalid(long id) {
        return given()
                .spec(CinescopeSpecs.defaultRequestSpecs())
                .pathParam("id", id)
                .when()
                .get(MOVIE_ID);
    }

    public GetMovieResponseDto getMovieById(long id) {
        return getMovieByIdInvalid(id)
                .then()
                .spec(CinescopeSpecs.successSpec())
                .extract()
                .as(GetMovieResponseDto.class);
    }

    public Response createMovieRaw(String token, CreateMovieRequestDto request) {
        return given()
                .spec(CinescopeSpecs.defaultRequestSpecs())
                .header("Authorization", "Bearer " + token)
                .body(request)
                .when()
                .post(Movies);
    }

    public GetMovieResponseDto createMovieSuccess(String token, CreateMovieRequestDto request) {
        return createMovieRaw(token, request)
                .then()
                .spec(CinescopeSpecs.successCreationSpecs())
                .extract()
                .as(GetMovieResponseDto.class);
    }

    public Response createMovieWithoutToken(CreateMovieRequestDto request) {
        return createMovieRaw(null,request)
                .then()
                .extract()
                .response();
    }

    public Response patchMovieRaw(String token, long id, CreateMovieRequestDto request) {
        return given()
                .spec(CinescopeSpecs.defaultRequestSpecs())
                .header("Authorization", "Bearer " + token)
                .pathParam("id", id)
                .body(request)
                .when()
                .patch(MOVIE_ID);
    }

    public GetMovieResponseDto patchMovie(String token, long id, CreateMovieRequestDto request) {
        return patchMovieRaw(token, id, request)
                .then()
                .spec(CinescopeSpecs.successSpec())
                .extract()
                .as(GetMovieResponseDto.class);
    }


    public Response patchMovieWithoutAuth(long id, CreateMovieRequestDto request) {
        return given()
                .spec(CinescopeSpecs.defaultRequestSpecs())
                .pathParam("id", id)
                .body(request)
                .when()
                .patch(MOVIE_ID)
                .then()
                .extract()
                .response();
    }

    public Response deleteMovieRaw(String token, long id) {
        return given()
                .spec(CinescopeSpecs.defaultRequestSpecs())
                .header("Authorization", "Bearer " + token)
                .pathParam("id", id)
                .when()
                .delete(MOVIE_ID);
    }

    public Response deleteMovie(String token, long id) {
        return deleteMovieRaw(token, id)
                .then()
                .spec(CinescopeSpecs.successSpec())
                .extract()
                .response();
    }


}

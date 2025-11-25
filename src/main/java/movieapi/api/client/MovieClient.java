package movieapi.api.client;

import io.restassured.response.Response;
import movieapi.api.dto.movies.requestdto.CreateMovieRequestDto;
import movieapi.api.dto.movies.responsedto.GetMovieResponseDto;
import movieapi.api.spec.CinescopeSpecs;

import static io.restassured.RestAssured.given;

public class MovieClient {

    private static final String Movie_Id = "/movies/{id}";
    private static final String Movies = "/movies";

    public GetMovieResponseDto getMovieById(long id) {
        return given()
                .spec(CinescopeSpecs.defaultRequestSpecs())
                .pathParam("id", id)
                .when()
                .get(Movie_Id)
                .then()
                .spec(CinescopeSpecs.successSpec())
                .extract()
                .as(GetMovieResponseDto.class);
    }

    public Response getMovieByIdBroken(long id) {
        return given()
                .spec(CinescopeSpecs.defaultRequestSpecs())
                .pathParam("id", id)
                .when()
                .get(Movie_Id)
                .then()
                .extract()
                .response();
    }

    public GetMovieResponseDto createMovie(String token, CreateMovieRequestDto request) {
        return given()
                .spec(CinescopeSpecs.defaultRequestSpecs())
                .header("Authorization", "Bearer " + token)
                .body(request)
                .when()
                .post(Movies)
                .then()
                .spec(CinescopeSpecs.createdSpecs())
                .extract()
                .as(GetMovieResponseDto.class);
    }

    public Response createMovieBroken(CreateMovieRequestDto request) {
        return given()
                .spec(CinescopeSpecs.defaultRequestSpecs())
                .body(request)
                .when()
                .post(Movies)
                .then()
                .extract()
                .response();
    }

    public Response deleteMovie(String token, long id) {
        return given()
                .spec(CinescopeSpecs.defaultRequestSpecs())
                .header("Authorization", "Bearer " + token)
                .pathParam("id", id)
                .when()
                .delete(Movie_Id)
                .then()
                .log().all()
                .spec(CinescopeSpecs.successSpec())
                .extract()
                .response();
    }

    public GetMovieResponseDto patchMovie(String token, long id, CreateMovieRequestDto request) {
        return given()
                .spec(CinescopeSpecs.defaultRequestSpecs())
                .header("Authorization", "Bearer " + token)
                .pathParam("id", id)
                .body(request)
                .when()
                .patch(Movie_Id)
                .then()
                .spec(CinescopeSpecs.successSpec())
                .extract()
                .as(GetMovieResponseDto.class);
    }

    public Response patchMovieBroken(long id, CreateMovieRequestDto request) {
        return given()
                .spec(CinescopeSpecs.defaultRequestSpecs())
                .pathParam("id", id)
                .body(request)
                .when()
                .patch(Movie_Id)
                .then()
                .extract()
                .response();
    }
}

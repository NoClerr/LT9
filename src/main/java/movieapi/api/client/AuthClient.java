package movieapi.api.client;

import movieapi.api.dto.auth.request.LoginRequestDto;
import movieapi.api.dto.auth.response.LoginResponseDto;
import movieapi.api.spec.AuthSpecs;

import static io.restassured.RestAssured.given;

public class AuthClient {

    public LoginResponseDto login(LoginRequestDto request) {
        return given()
                .spec(AuthSpecs.authRequestSpecs())
                .body(request)
                .when()
                .post("/login")
                .then()
                .spec(AuthSpecs.authSuccessSpec())
                .extract()
                .as(LoginResponseDto.class);
    }
}

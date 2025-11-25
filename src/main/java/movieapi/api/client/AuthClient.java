package movieapi.api.client;

import movieapi.api.dto.auth.requestdto.LoginRequestDto;
import movieapi.api.dto.auth.responsedto.LoginResponseDto;
import movieapi.api.spec.AuthSpecs;

import static io.restassured.RestAssured.given;

public class AuthClient {

    private static final String Login = "/login";

    public LoginResponseDto login(LoginRequestDto request) {
        return given()
                .spec(AuthSpecs.authRequestSpecs())
                .body(request)
                .when()
                .post(Login)
                .then()
                .spec(AuthSpecs.authSuccessSpec())
                .extract()
                .as(LoginResponseDto.class);
    }

    public String loginGiveToken(LoginRequestDto request) {
        return login(request).getAccessToken();
    }
}

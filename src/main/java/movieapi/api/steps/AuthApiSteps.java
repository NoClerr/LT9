package movieapi.api.steps;

import io.qameta.allure.Step;
import movieapi.api.client.AuthClient;
import movieapi.api.dto.auth.request.LoginRequestDto;

public class AuthApiSteps {

    private final AuthClient client = new AuthClient();


    @Step("Получаем токен")
    public String loginAndGetToken(String email, String password) {
        LoginRequestDto request = new LoginRequestDto(email, password);
        return client.login(request).getAccessToken();
    }
}

package movieapi.api.dto.auth.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private UserLoginDto user;
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
}

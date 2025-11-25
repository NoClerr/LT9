package movieapi.api.dto.auth.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    private String id;
    private String email;
    private String fullName;
    private String createdAt;
    private List<String> roles;
    private boolean verified;
    private boolean banned;
}

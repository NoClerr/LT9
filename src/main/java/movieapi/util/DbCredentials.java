package movieapi.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbCredentials {
    private String host;
    private int port;
    private String dbName;
    private String username;
    private String password;
}

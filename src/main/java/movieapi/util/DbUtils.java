package movieapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class DbUtils {

    private static final String CREDENTIALS_FILE = "db_credentials.json";
    private static final DbCredentials credentials;

    static {
        try (InputStream is = DbUtils.class.getClassLoader().getResourceAsStream(CREDENTIALS_FILE)) {
            if (is == null) {
                throw new RuntimeException("Файл " + CREDENTIALS_FILE + " не найден в resources");
            }
            ObjectMapper mapper = new ObjectMapper();
            credentials = mapper.readValue(is, DbCredentials.class);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при загрузке креденшалов БД", e);
        }
    }
    public static DbCredentials getCredentials() {
        return credentials;
    }
}


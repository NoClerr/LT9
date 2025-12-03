package movieapi.config;

import java.util.ResourceBundle;

public class ConfigProvider {

    private static final ResourceBundle config = ResourceBundle.getBundle("CinescopeConfig");

    public static String getAuthBaseUrl() {
        return config.getString("auth.base.url");
    }

    public static String getApiBaseUrl() {
        return config.getString("api.base.url");
    }

    public static String getUserEmail() {
        return config.getString("auth.user.email");
    }

    public static String getUserPassword() {
        return config.getString("auth.user.password");
    }
}
package movieapi.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import movieapi.util.DbCredentials;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.spi.JdbiPlugin;
import org.jdbi.v3.jackson2.Jackson2Config;
import org.jdbi.v3.jackson2.Jackson2Plugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public abstract class DbBaseSteps {

    protected Jdbi dbClient;

    public DbBaseSteps(String url, String username, String password) {
        this.dbClient = buildDbClient(url, username, password);
        initMapper();
    }

    public DbBaseSteps(DbCredentials creds) {
        this(
                "jdbc:postgresql://" + creds.getHost() + ":" + creds.getPort() + "/" + creds.getDbName(),
                creds.getUsername(),
                creds.getPassword()
        );
    }

    private Jdbi buildDbClient(String url, String username, String password) {
        Jdbi jdbi = Jdbi.create(url, username, password);
        jdbi.installPlugin(new PostgresPlugin());
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.installPlugin(new Jackson2Plugin());
        return jdbi;
    }

    private void initMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.registerModule(new JavaTimeModule());

        dbClient.getConfig(Jackson2Config.class).setMapper(mapper);
    }

    private class PostgresPlugin implements JdbiPlugin {
    }
}


package movieapi.db.dao;

import movieapi.db.domain.Movie;
import org.jdbi.v3.json.Json;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface MoviesDao {

    @Json
    @SqlQuery("SELECT to_jsonb(m) FROM movies m WHERE id = :id")
    Movie getMovieById(@Bind("id") long id);
}

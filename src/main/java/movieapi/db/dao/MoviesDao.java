package movieapi.db.dao;

import movieapi.db.domain.Movie;
import org.jdbi.v3.json.Json;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface MoviesDao {

    @Json
    @SqlQuery("SELECT to_jsonb(m) FROM movies m WHERE id = :id")
    Movie getMovieById(@Bind("id") long id);

    @SqlQuery("SELECT COUNT(*) FROM movies WHERE id = :id")
    int countMovieById(@Bind("id") long id);

    @SqlUpdate("DELETE FROM movies WHERE od = :id")
    int deleteMovie(@Bind("id") long id);
}

package movieapi.db.steps;

import io.qameta.allure.Step;
import movieapi.db.DbBaseSteps;
import movieapi.db.dao.MoviesDao;
import movieapi.db.domain.Movie;
import movieapi.util.DbCredentials;

public class MovieDbSteps extends DbBaseSteps {

    public MovieDbSteps(DbCredentials creds) {
        super(creds);
    }

    @Step("Получаем фильм из БД по id = {id}")
    public Movie getMovieById(long id) {
        return dbClient.withExtension(MoviesDao.class, dao -> dao.getMovieById(id));
    }
}

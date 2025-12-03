package apiTests;

import movieapi.api.dto.movies.request.CreateMovieRequestDto;

import java.util.UUID;

public class MovieTestData {

    public static CreateMovieRequestDto moviePostRequest() {
        return new CreateMovieRequestDto(
                "Фильм автотест " + UUID.randomUUID(),
                "https://image.url",
                200,
                "Описание автотестового фильма",
                "MSK",
                true,
                1L
        );
    }

    public static CreateMovieRequestDto moviePostRequestWithoitToken() {
        return new CreateMovieRequestDto(
                "Негативный фильм " + UUID.randomUUID(),
                "https://image.url",
                200,
                "Описание",
                "MSK",
                true,
                1L
        );
    }

    public static CreateMovieRequestDto moviePostRequestFromPatch() {
        return new CreateMovieRequestDto(
                "Фильм автотест под PATCH" + UUID.randomUUID(),
                "https://image.url",
                350,
                "Описание автотестового фильма под PATCH",
                "MSK",
                true,
                1L
        );
    }

    public static CreateMovieRequestDto moviePatchRequest() {
        return new CreateMovieRequestDto(
                "Автотест фильм после PATCH " + UUID.randomUUID(),
                "https://new.image.url",
                999,
                "Обновлённое описание после PATCH",
                "SPB",
                false,
                1L
        );
    }

    public static CreateMovieRequestDto moviePatchRequestWithoitToken() {
        return new CreateMovieRequestDto(
                "Автотест фильм PATCH без авторизации",
                "https://image.url",
                99,
                "Описание фильма без авторизации",
                "SPB",
                true,
                1L
        );
    }


    public static CreateMovieRequestDto movieDeleteRequest() {
        return new CreateMovieRequestDto(
                "Фильм автотест под DELETE" + UUID.randomUUID(),
                "https://image.url",
                50,
                "Описание автотестового фильма под DELETE",
                "MSK",
                true,
                1L
        );
    }
}

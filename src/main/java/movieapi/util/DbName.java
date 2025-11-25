package movieapi.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DbName {
    MOVIES("movies");

    private final String value;
}


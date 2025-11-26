package movieapi.api.dto.movies.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieRequestDto {
    private String name;
    private String imageUrl;
    private Integer price;
    private String description;
    private String location;
    private boolean published;
    private long genreId;
}

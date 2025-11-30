package movieapi.api.dto.movies.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import movieapi.api.dto.common.GenreDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetMovieResponseDto {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String imageUrl;
    private String location;
    private Boolean published;
    private Long genreId;
    private GenreDto genre;
    private String createdAt;
    private Integer rating;
}

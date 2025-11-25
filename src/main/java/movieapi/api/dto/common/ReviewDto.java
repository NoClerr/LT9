package movieapi.api.dto.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private String userId;
    private Integer rating;
    private String text;
    private Boolean hidden;
    private String createdAt;
    private UserDto user;
}

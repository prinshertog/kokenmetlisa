package be.freedombox.backend.request;

import be.freedombox.backend.domain.Category;
import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DishUpdateRequest {
    private Long id;
    private String dishName;
    private String description;
    private List<String> categories;
    private String imageName;
}

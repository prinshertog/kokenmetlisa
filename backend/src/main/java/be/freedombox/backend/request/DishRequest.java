package be.freedombox.backend.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DishRequest {
    @NotBlank(message = "Dish name is required")
    private String name;
    @NotBlank
    private String description;
    private List<String> categories;
    @NotBlank
    private String imageName;
}

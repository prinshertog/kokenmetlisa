package be.freedombox.backend.dto;

import be.freedombox.backend.domain.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class DishDTO {
    @NotBlank
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private List<Category> categories;
    @NotBlank
    private String imageName;
}

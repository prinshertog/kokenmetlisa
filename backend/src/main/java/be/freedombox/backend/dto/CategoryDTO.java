package be.freedombox.backend.dto;

import be.freedombox.backend.domain.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryDTO {
    @NotBlank
    private String category;
    private Category parentCategory;
}

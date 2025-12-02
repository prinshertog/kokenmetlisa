package be.freedombox.backend.request;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryRequest {
    @NotBlank
    private String category;
    private String parentCategory;
}

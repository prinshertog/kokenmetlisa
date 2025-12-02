package be.freedombox.backend.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SwitchCategoryRequest {
    @NotBlank
    private String category;
    private boolean up;
}

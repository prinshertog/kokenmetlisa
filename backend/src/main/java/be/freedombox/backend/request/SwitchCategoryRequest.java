package be.freedombox.backend.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SwitchCategoryRequest {
    @NotBlank
    private String category;
    private boolean up;
}

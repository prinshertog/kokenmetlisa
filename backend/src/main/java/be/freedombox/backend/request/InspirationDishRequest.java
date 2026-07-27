package be.freedombox.backend.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InspirationDishRequest {
    @NotBlank
    @Valid
    private String title;

    private String shortDescription;

    private String imageUrl;

    private Set<String> categories;

    @NotBlank
    @Valid
    private String sourceName;

    private String externalUrl;
}

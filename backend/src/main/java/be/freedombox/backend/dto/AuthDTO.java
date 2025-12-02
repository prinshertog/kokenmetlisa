package be.freedombox.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthDTO {
    @NotBlank
    private String bearerToken;
    @NotBlank
    private String role;
}

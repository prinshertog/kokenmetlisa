package be.freedombox.backend.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}

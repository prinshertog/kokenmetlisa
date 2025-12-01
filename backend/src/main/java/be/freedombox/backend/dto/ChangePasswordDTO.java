package be.freedombox.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ChangePasswordDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
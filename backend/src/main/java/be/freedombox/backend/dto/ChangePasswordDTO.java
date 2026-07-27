package be.freedombox.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ChangePasswordDTO {
    private String username;
    private String oldPassword;
    private String newPassword;
}
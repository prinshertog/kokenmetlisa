package be.freedombox.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserDTO {
    public String username;
    public String role;
}
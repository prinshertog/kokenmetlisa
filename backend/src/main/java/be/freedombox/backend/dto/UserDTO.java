package be.freedombox.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    @NotBlank
    public String username;
    @NotBlank
    public String role;

    public UserDTO() {

    }

    public UserDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

package be.freedombox.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthDTO {
    @NotBlank
    private String bearerToken;
    @NotBlank
    private String role;

    public AuthDTO(String bearerToken, String role) {
        this.bearerToken = bearerToken;
        this.role = role;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

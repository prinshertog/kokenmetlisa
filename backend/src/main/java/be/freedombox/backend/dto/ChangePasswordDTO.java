package be.freedombox.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;

    public ChangePasswordDTO() {

    }

    public ChangePasswordDTO(String username, String oldPassword, String newPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

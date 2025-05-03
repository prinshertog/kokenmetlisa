package be.freedombox.backend.service;

public interface IAuthenticationService {
    boolean hasRole(String authorizationHeader, String username, String role);
    boolean isActualUser(String authorizationHeader, String username);
}

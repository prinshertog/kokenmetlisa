package be.freedombox.backend.service;

import be.freedombox.backend.dto.ChangePasswordDTO;
import be.freedombox.backend.dto.UserDTO;
import be.freedombox.backend.request.UserRequest;

import java.util.List;

public interface IUserService {
    List<UserDTO> all(String authorizationHeader);
    void create(String authorizationHeader, UserRequest userRequest);
    void delete(String authorizationHeader, String username);
    void changePassword(String authorizationHeader, ChangePasswordDTO changePasswordDTO);
    String bearerTokenGenerator(String username, String password);
}

package be.freedombox.backend.service;

import be.freedombox.backend.domain.User;
import be.freedombox.backend.dto.UserDTO;
import be.freedombox.backend.request.UserRequest;

import java.util.List;

public interface IUserService {
    User findByUsername(String username);
    String validate(String username, String password);
    List<UserDTO> all();
    void create(UserRequest userRequest);
    void update(UserRequest userRequest);
    void delete(String username);
}

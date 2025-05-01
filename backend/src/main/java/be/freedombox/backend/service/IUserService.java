package be.freedombox.backend.service;

import be.freedombox.backend.domain.User;
import be.freedombox.backend.dto.UserDTO;

import java.util.List;

public interface IUserService {
    User findByUsername(String username);
    String validate(String username, String password);
    List<UserDTO> all();
}

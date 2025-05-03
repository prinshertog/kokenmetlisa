package be.freedombox.backend.service;

import be.freedombox.backend.domain.User;
import be.freedombox.backend.dto.UserDTO;
import be.freedombox.backend.exception.ObjectAlreadyExistsException;
import be.freedombox.backend.exception.ObjectDoesNotExistException;
import be.freedombox.backend.exception.UnAuthorizedException;
import be.freedombox.backend.repository.UserRepository;
import be.freedombox.backend.request.UserRequest;
import be.freedombox.backend.tools.JwtUtils;
import be.freedombox.backend.tools.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new ObjectDoesNotExistException("There is no user with username " + username);
        return user;
    }

    public String validate(String username, String password) {
        User user = findByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) throw new UnAuthorizedException("Wrong password");
        return JwtUtils.generateToken(user.getUsername());
    }

    public List<UserDTO> all() {
        return userRepository.findAll().stream().map(Mapper::toUserDTO).collect(Collectors.toList());
    }

    public void create(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) throw new ObjectAlreadyExistsException("Username already exists");
        User user = new User(userRequest.getUsername(), passwordEncoder.encode(userRequest.getPassword()), userRequest.getRole());
        userRepository.save(user);
    }

    public void update(UserRequest userRequest) {
        User user = findByUsername(userRequest.getUsername());
        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) throw new UnAuthorizedException("Wrong password");
    }

    public void delete(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new ObjectDoesNotExistException("There is no user with username " + username);
        userRepository.delete(user);
    }
}

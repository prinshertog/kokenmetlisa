package be.freedombox.backend.service;

import be.freedombox.backend.domain.User;
import be.freedombox.backend.dto.ChangePasswordDTO;
import be.freedombox.backend.dto.UserDTO;
import be.freedombox.backend.exception.IllegalInputException;
import be.freedombox.backend.exception.ObjectAlreadyExistsException;
import be.freedombox.backend.exception.ObjectDoesNotExistException;
import be.freedombox.backend.exception.UnAuthorizedException;
import be.freedombox.backend.repository.UserRepository;
import be.freedombox.backend.request.UserRequest;
import be.freedombox.backend.tools.JwtUtils;
import be.freedombox.backend.tools.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String bearerTokenGenerator(String username, String password) {
        User user = findByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) throw new UnAuthorizedException("Wrong password");
        return JwtUtils.generateToken(user.getUsername());
    }

    public List<UserDTO> all() {
        return userRepository.findAll().stream().map(Mapper::toUserDTO).collect(Collectors.toList());
    }

    public void create(String authorizationHeader, UserRequest userRequest) {
        User requestingUser =  getUserByBearerToken(authorizationHeader);
        if (!hasRole(authorizationHeader, requestingUser.getUsername(), "ADMIN")) throw new UnAuthorizedException("You do not have permission to access this resource");
        if (userRepository.existsByUsername(userRequest.getUsername())) throw new ObjectAlreadyExistsException("Username already exists");
        User user = new User(userRequest.getUsername(), passwordEncoder.encode(userRequest.getPassword()), userRequest.getRole());
        userRepository.save(user);
    }

    public void update(String authorizationHeader, UserRequest userRequest) {
        if (!hasRole(authorizationHeader, userRequest.getUsername(), "ADMIN")) throw new UnAuthorizedException("You do not have permission to access this resource");
        User user = findByUsername(userRequest.getUsername());
        if (passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) throw new UnAuthorizedException("Wrong password");
    }

    public void delete(String authorizationHeader, String username) {
        if (!hasRole(authorizationHeader, username, "ADMIN")) throw new UnAuthorizedException("You do not have permission to access this resource");
        User user = findByUsername(username);
        userRepository.delete(user);
    }

    public void changePassword(String authorizationHeader, ChangePasswordDTO changePasswordDTO) {
        User requestingUser = getUserByBearerToken(authorizationHeader);
        if (!hasRole(authorizationHeader, requestingUser.getUsername(), "ADMIN")) {
            if (!isActualUser(authorizationHeader, changePasswordDTO.getUsername())) throw new UnAuthorizedException("You do not have permission to change the password for this user");
            if (changePasswordDTO.getNewPassword().equals(changePasswordDTO.getOldPassword())) throw new IllegalInputException("New password cannot be the old password");
        }
        User user = findByUsername(changePasswordDTO.getUsername());
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) throw new IllegalInputException("Old password is wrong");
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }

    public boolean hasRole(String authorizationHeader, String username, String role) {
        isActualUser(authorizationHeader, username);
        User currentUser = findByUsername(username);
        return currentUser.getRole().equals(role);
    }

    public boolean isActualUser(String authorizationHeader, String username) {
        String bearerToken = authorizationHeader.replace("Bearer ", "");
        User currentUser = findByUsername(JwtUtils.extractUsername(bearerToken));
        return currentUser.getUsername().equals(username);
    }

    public User getUserByBearerToken(String authorizationHeader) {
        String bearerToken = authorizationHeader.replace("Bearer ", "");
        return findByUsername(JwtUtils.extractUsername(bearerToken));
    }
}

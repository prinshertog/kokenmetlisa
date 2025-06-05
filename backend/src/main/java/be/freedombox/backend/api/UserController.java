package be.freedombox.backend.api;

import be.freedombox.backend.dto.ChangePasswordDTO;
import be.freedombox.backend.dto.UserDTO;
import be.freedombox.backend.request.UserRequest;
import be.freedombox.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> allUsers(@RequestHeader(value = "Authorization") String authorizationHeader) {
        List<UserDTO> users = userService.all(authorizationHeader);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addUser(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody UserRequest userRequest) {
        userService.create(authorizationHeader, userRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/password")
    public ResponseEntity<HttpStatus> updateUser(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(authorizationHeader, changePasswordDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteUser(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody String username) {
        userService.delete(authorizationHeader, username);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

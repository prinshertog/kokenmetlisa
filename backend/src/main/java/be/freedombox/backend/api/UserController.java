package be.freedombox.backend.api;

import be.freedombox.backend.dto.UserDTO;
import be.freedombox.backend.request.UserRequest;
import be.freedombox.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<List<UserDTO>> allUsers() {
        return ResponseEntity.ok(userService.all());
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addUser(@RequestBody UserRequest userRequest) {
        userService.create(userRequest);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserRequest userRequest, String newPassword) {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteUser(@RequestBody String username) {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

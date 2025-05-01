package be.freedombox.backend.api;

import be.freedombox.backend.domain.User;
import be.freedombox.backend.request.AuthRequest;
import be.freedombox.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(userService.validate(authRequest.getUsername(), authRequest.getPassword()));
    }
}

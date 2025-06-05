package be.freedombox.backend.api;

import be.freedombox.backend.domain.User;
import be.freedombox.backend.dto.AuthDTO;
import be.freedombox.backend.request.AuthRequest;
import be.freedombox.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<AuthDTO> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(userService.authenticate(authRequest));
    }

    @GetMapping
    public ResponseEntity<Map<String, Boolean>> isAuthenticated(@RequestHeader(value = "Authorization") String authorizationHeader) {
        Map<String, Boolean> response = new HashMap<>();
        if (userService.isAuthenticated(authorizationHeader)) {
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }
}

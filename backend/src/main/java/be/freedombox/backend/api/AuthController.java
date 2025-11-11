package be.freedombox.backend.api;

import be.freedombox.backend.dto.AuthDTO;
import be.freedombox.backend.request.AuthRequest;
import be.freedombox.backend.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public AuthDTO login(@RequestBody @Valid AuthRequest authRequest) {
        return userService.authenticate(authRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Boolean> isAuthenticated(@RequestHeader(value = "Authorization") @NotBlank String authorizationHeader) {
        Map<String, Boolean> response = new HashMap<>();
        if (userService.isAuthenticated(authorizationHeader)) {
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return response;
    }
}

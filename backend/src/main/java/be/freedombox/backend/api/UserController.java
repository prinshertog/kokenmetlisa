package be.freedombox.backend.api;

import be.freedombox.backend.dto.ChangePasswordDTO;
import be.freedombox.backend.dto.UserDTO;
import be.freedombox.backend.request.UserRequest;
import be.freedombox.backend.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> allUsers(@RequestHeader(value = "Authorization") @NotBlank String authorizationHeader) {
        List<UserDTO> users = userService.all(authorizationHeader);
        return users;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus addUser(@RequestHeader(value = "Authorization") @NotBlank String authorizationHeader,
                                              @RequestBody @Valid UserRequest userRequest) {
        userService.create(authorizationHeader, userRequest);
        return HttpStatus.CREATED;
    }

    @PutMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestHeader(value = "Authorization") @NotBlank String authorizationHeader,
                                                 @RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(authorizationHeader, changePasswordDTO);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestHeader(value = "Authorization") @NotBlank String authorizationHeader,
                                                 @RequestBody @NotBlank String username) {
        userService.delete(authorizationHeader, username);
    }
}

package be.freedombox.backend.api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus getHealth() {
        return HttpStatus.OK;
    }
}

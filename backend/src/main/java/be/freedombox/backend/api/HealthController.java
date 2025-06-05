package be.freedombox.backend.api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping
    public ResponseEntity<HttpStatus> getHealth() {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

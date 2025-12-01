package be.freedombox.backend;

import be.freedombox.backend.domain.User;
import be.freedombox.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner createTestUser(UserRepository userRepository, PasswordEncoder encoder) {
        boolean createAdminUser = Boolean.parseBoolean(System.getenv("CREATE_ADMIN_USER"));
        return args -> {
            if (createAdminUser) {
                if (!userRepository.existsByUsername("admin")) {
                    User user = new User("admin", encoder.encode("test123"), "ADMIN");
                    userRepository.save(user);
                    System.out.println("Created user: " + user);
                } else {
                    System.out.println("User already exists");
                }
            }
        };
    }

}

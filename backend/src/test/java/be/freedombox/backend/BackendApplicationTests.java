package be.freedombox.backend;

import be.freedombox.backend.domain.Dish;
import be.freedombox.backend.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
        Dish.builder().id(1L).build();
    }

}

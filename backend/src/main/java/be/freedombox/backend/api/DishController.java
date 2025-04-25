package be.freedombox.backend.api;

import be.freedombox.backend.dto.DishDTO;
import be.freedombox.backend.request.DishRequest;
import be.freedombox.backend.service.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dishes")
public class DishController {
    IDishService dishService;

    @Autowired
    public DishController(IDishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDishes() {
        return ResponseEntity.ok(dishService.all());
    }

    @PostMapping
    public ResponseEntity<DishDTO> createDish(@RequestBody DishRequest dishRequest) {
        return ResponseEntity.ok(dishService.create(dishRequest));
    }
}

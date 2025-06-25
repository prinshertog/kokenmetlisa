package be.freedombox.backend.api;

import be.freedombox.backend.dto.DishDTO;
import be.freedombox.backend.exception.NotImplementedException;
import be.freedombox.backend.request.CategoryRequest;
import be.freedombox.backend.request.DishRequest;
import be.freedombox.backend.service.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {
    IDishService dishService;

    @Autowired
    public DishController(IDishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<DishDTO>> getDishes(@PathVariable("category") String category) {
        return ResponseEntity.ok(dishService.getByCategory(category));
    }

    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDishes() {
        return ResponseEntity.ok(dishService.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<DishDTO>> getDishById(@PathVariable Long id) {
        return ResponseEntity.ok(dishService.getById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createDish(@RequestParam("file") MultipartFile file, @RequestPart DishRequest dishRequest) {
        dishService.create(dishRequest, file);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<DishDTO> updateDish(@PathVariable Long id, @RequestBody DishRequest dishRequest) {
        //TODO add functionality to update a dish
        throw new NotImplementedException("This controller method is not implemented yet!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDish(@PathVariable Long id) {
        dishService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

package be.freedombox.backend.api;

import be.freedombox.backend.dto.DishDTO;
import be.freedombox.backend.exception.IllegalInputException;
import be.freedombox.backend.request.DishRequest;
import be.freedombox.backend.request.DishUpdateRequest;
import be.freedombox.backend.service.DishService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {
    DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/category/{category}")
    @ResponseStatus(HttpStatus.OK)
    public List<DishDTO> getDishes(@PathVariable("category") @NotBlank String category) {
        return dishService.getByCategory(category);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DishDTO> getAllDishes() {
        return dishService.all();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<DishDTO> getDishById(@PathVariable @NotNull Long id) {
        return dishService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createDish(@RequestParam("file") @Valid MultipartFile file, @RequestPart @Valid DishRequest dishRequest) {
        System.out.println(dishRequest.getCategories());
        dishService.create(dishRequest, file);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateDish(@RequestParam(value = "file", required = false) @Valid MultipartFile file,
                                                 @RequestPart @Valid DishUpdateRequest dishUpdateRequest) {
        dishService.update(dishUpdateRequest, file);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDish(@PathVariable @NotNull Long id) {
        dishService.delete(id);
    }
}

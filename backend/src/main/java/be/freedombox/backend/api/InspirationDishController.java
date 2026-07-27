package be.freedombox.backend.api;

import be.freedombox.backend.dto.InspirationDishDTO;
import be.freedombox.backend.request.InspirationDishRequest;
import be.freedombox.backend.service.InspirationDishService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/inspirationDishes"))
public class InspirationDishController {
    private InspirationDishService inspirationDishService;

    @Autowired
    public InspirationDishController(InspirationDishService inspirationDishService) {
        this.inspirationDishService = inspirationDishService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<InspirationDishDTO> getDishes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String category
    ) {
        if (category == null || category.isEmpty()) {
            return inspirationDishService.getInspirationDishesForPage(page);
        } else {
            return inspirationDishService.getInspirationDishesForPage(page, category);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createDish(@RequestBody @Valid InspirationDishRequest request) {
        inspirationDishService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDish(@PathVariable @NotNull Long id) {
        inspirationDishService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateDish(@PathVariable Long id, @RequestBody InspirationDishRequest request) {
        inspirationDishService.update(id, request);
    }
}

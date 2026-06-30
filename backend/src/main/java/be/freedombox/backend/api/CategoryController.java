package be.freedombox.backend.api;

import be.freedombox.backend.dto.CategoryDTO;
import be.freedombox.backend.request.CategoryRequest;
import be.freedombox.backend.request.SwitchCategoryRequest;
import be.freedombox.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDTO> getAllCategories() {
        return categoryService.all();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.create(categoryRequest);
    }

    @PutMapping("/position")
    @ResponseStatus(HttpStatus.OK)
    public void switchCategoryPosition(@RequestBody @Valid SwitchCategoryRequest categoryRequest) {
        categoryService.switchPosition(categoryRequest.getName(), categoryRequest.isUp());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        categoryService.delete(categoryRequest);
    }
}
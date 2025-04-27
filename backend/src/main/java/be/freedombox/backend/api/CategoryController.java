package be.freedombox.backend.api;

import be.freedombox.backend.dto.CategoryDTO;
import be.freedombox.backend.request.CategoryRequest;
import be.freedombox.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.all());
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.create(categoryRequest));
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.delete(categoryRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
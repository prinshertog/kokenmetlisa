package be.freedombox.backend.api;

import be.freedombox.backend.domain.SubCategory;
import be.freedombox.backend.dto.CategoryDTO;
import be.freedombox.backend.dto.SubCategoryDTO;
import be.freedombox.backend.request.CategoryRequest;
import be.freedombox.backend.request.SubCategoryRequest;
import be.freedombox.backend.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/subcategory")
public class SubCategoryController {
    SubCategoryService subCategoryService;

    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<SubCategoryDTO>> getAllSubCategories() {
        return ResponseEntity.ok(subCategoryService.all());
    }

    @PostMapping
    public ResponseEntity<SubCategoryDTO> createCategory(@RequestBody SubCategoryRequest subCategoryRequest) {
        return ResponseEntity.ok(subCategoryService.create(subCategoryRequest));
    }
}

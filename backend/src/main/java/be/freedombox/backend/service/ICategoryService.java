package be.freedombox.backend.service;

import be.freedombox.backend.dto.CategoryDTO;
import be.freedombox.backend.request.CategoryRequest;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> all();
    CategoryDTO create(CategoryRequest categoryRequest);
    void delete(CategoryRequest categoryRequest);
    void switchPosition(String category, boolean isUp);
}

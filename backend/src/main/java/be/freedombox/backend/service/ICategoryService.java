package be.freedombox.backend.service;

import be.freedombox.backend.dto.CategoryDTO;
import be.freedombox.backend.request.CategoryRequest;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> all();
    CategoryDTO create(CategoryRequest request);
}

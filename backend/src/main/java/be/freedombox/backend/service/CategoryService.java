package be.freedombox.backend.service;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.dto.CategoryDTO;
import be.freedombox.backend.exception.ObjectAlreadyExistsException;
import be.freedombox.backend.exception.ObjectDoesNotExistException;
import be.freedombox.backend.repository.CategoryRepository;
import be.freedombox.backend.request.CategoryRequest;
import be.freedombox.backend.tools.Mapper;
import be.freedombox.backend.tools.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDTO create(CategoryRequest categoryRequest) {
        categoryRequest.setCategory(Validator.initCap(categoryRequest.getCategory()));
        Category existingCategory = categoryRepository.findByCategory(categoryRequest.getCategory());
        Category parentCategory = categoryRepository.findByCategory(categoryRequest.getParentCategory());
        if (categoryRequest.getParentCategory() != null && categoryRequest.getParentCategory().isEmpty()) {
            if (parentCategory == null) throw new ObjectDoesNotExistException("The category " + categoryRequest.getParentCategory() + " does not exist.");
        }
        if (existingCategory != null) throw new ObjectAlreadyExistsException("The category " + categoryRequest.getCategory() + " already exists.");
        Category category = new Category(categoryRequest.getCategory(), parentCategory);
        category.setPosition(getFreePosition());
        return Mapper.toCategoryDTO(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDTO> all() {
        return categoryRepository.findAll().stream().sorted().map(Mapper::toCategoryDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(CategoryRequest categoryRequest) {
        categoryRequest.setCategory(Validator.initCap(categoryRequest.getCategory()));
        Category category = categoryRepository.findByCategory(categoryRequest.getCategory());
        if (category == null) throw new ObjectDoesNotExistException("The category " + categoryRequest.getCategory() + " does not exist.");
        categoryRepository.delete(category);
    }

    private int getFreePosition() {
        List<Category> categories = categoryRepository.findAll();
        categories.sort(Comparator.comparing(Category::getPosition).reversed());
        return categories.get(0).getPosition() + 1;
    }

    @Override
    public void switchPosition(String categoryName1, String categoryName2) {
        Category category1 = categoryRepository.findByCategory(categoryName1);
        Category category2 = categoryRepository.findByCategory(categoryName2);
        category1.setPosition(category2.getPosition());
        category2.setPosition(category1.getPosition());
    }
}

package be.freedombox.backend.service;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.dto.CategoryDTO;
import be.freedombox.backend.exception.CategoryException;
import be.freedombox.backend.exception.ObjectAlreadyExistsException;
import be.freedombox.backend.exception.ObjectDoesNotExistException;
import be.freedombox.backend.repository.CategoryRepository;
import be.freedombox.backend.request.CategoryRequest;
import be.freedombox.backend.tools.Mapper;
import be.freedombox.backend.tools.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Category> categories = categoryRepository.findAll();
        categories.sort(Comparator.comparing(Category::getPosition));
        return categories.stream().map(Mapper::toCategoryDTO).toList();
    }

    @Override
    public void delete(CategoryRequest categoryRequest) {
        categoryRequest.setCategory(Validator.initCap(categoryRequest.getCategory()));
        Category category = categoryRepository.findByCategory(categoryRequest.getCategory());
        if (category == null) throw new ObjectDoesNotExistException("The category " + categoryRequest.getCategory() + " does not exist.");
        int deletedPosition = category.getPosition();
        categoryRepository.delete(category);
        resortPositions(deletedPosition);
    }

    private void resortPositions(int deletedPosition) {
        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            if (category.getPosition() > deletedPosition) {
                category.setPosition(category.getPosition() - 1);
                categoryRepository.save(category);
            }
        }
    }

    private int getFreePosition() {
        List<Category> categories = categoryRepository.findAll();
        categories.sort(Comparator.comparing(Category::getPosition).reversed());

        if (categories.isEmpty()) {
            return 1;
        } else {
            return categories.get(0).getPosition() + 1;
        }
    }

    @Override
    public void switchPosition(String categoryName, boolean isUp) {
        try {
            Category category = categoryRepository.findByCategory(categoryName);

            if (category == null) throw new ObjectDoesNotExistException("Current category does not exist!");

            Category otherCategory = findCategory(isUp, category);

            int categoryPosition = category.getPosition();
            int otherCategoryPosition = otherCategory.getPosition();

            category.setPosition(otherCategoryPosition);
            otherCategory.setPosition(categoryPosition);

            categoryRepository.save(category);
            categoryRepository.save(otherCategory);

        } catch (Exception e) {
            throw new CategoryException(e.getMessage());
        }
    }

    private Category findCategory(boolean up, Category category) {
        List<Category> categories;

        if (up) categories = categoryRepository.findAll().stream()
                .filter(cat -> cat.getPosition() < category.getPosition()).toList();
        else categories = categoryRepository.findAll().stream()
                .filter(cat -> cat.getPosition() > category.getPosition()).toList();

        if (category.getParentCategory() != null) {
            categories = new ArrayList<>(categories.stream()
                    .filter(cat -> cat.getParentCategory() != null)
                    .filter(cat -> cat.getParentCategory() == category.getParentCategory()).toList());
        } else {
            categories = new ArrayList<>(categories.stream()
                    .filter(cat -> cat.getParentCategory() == null).toList());
        }

        if (up) categories.sort(Comparator.comparing(Category::getPosition).reversed());
        else categories.sort(Comparator.comparing(Category::getPosition));

        if (up && categories.isEmpty()) throw new ObjectDoesNotExistException("No category above.");
        else if (categories.isEmpty()) throw new ObjectDoesNotExistException("No category below.");

        return categories.get(0);
    }
}

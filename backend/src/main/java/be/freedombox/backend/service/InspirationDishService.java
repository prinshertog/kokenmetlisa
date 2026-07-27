package be.freedombox.backend.service;

import be.freedombox.backend.domain.InspirationDish;
import be.freedombox.backend.dto.InspirationDishDTO;
import be.freedombox.backend.exception.IllegalInputException;
import be.freedombox.backend.exception.InspirationDishException;
import be.freedombox.backend.repository.InspirationDishRepository;
import be.freedombox.backend.request.InspirationDishRequest;
import be.freedombox.backend.tools.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class InspirationDishService {
    private final InspirationDishRepository inspirationDishRepository;
    private final CategoryService categoryService;

    @Autowired
    public InspirationDishService(InspirationDishRepository inspirationDishRepository, CategoryService categoryService) {
        this.inspirationDishRepository = inspirationDishRepository;
        this.categoryService = categoryService;
    }

    public void create(InspirationDishRequest request) {
        try {
            inspirationDishRepository.save(Mapper.toInspirationDish(request));
        } catch (Exception error) {
            throw new IllegalInputException("Failed to create inspiration dish: " + error.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            if (inspirationDishRepository.findById(id).isEmpty()) {
                throw new InspirationDishException("Selected dish with id %s does not exist!".formatted(id));
            }
            inspirationDishRepository.deleteById(id);
        } catch (Exception error) {
            throw new InspirationDishException("Failed to delete inspiration dish: " + error.getMessage());
        }
    }

    public void update(Long id, InspirationDishRequest inspirationDishRequest) {
        try {
            InspirationDish dish = inspirationDishRepository.findById(id).orElseThrow(() -> new IllegalInputException("Selected dish with id %s does not exist!".formatted(id)));
            if (inspirationDishRequest.getTitle() != null
                    && !inspirationDishRequest.getTitle().isBlank()) {
                dish.setTitle(inspirationDishRequest.getTitle());
            }

            if (inspirationDishRequest.getShortDescription() != null
                    && !inspirationDishRequest.getShortDescription().isBlank()) {
                dish.setShortDescription(inspirationDishRequest.getShortDescription());
            }

            if (inspirationDishRequest.getImageUrl() != null
                    && !inspirationDishRequest.getImageUrl().isBlank()) {
                dish.setImageUrl(inspirationDishRequest.getImageUrl());
            }

            if (inspirationDishRequest.getCategories() != null
                    && !inspirationDishRequest.getCategories().isEmpty()) {
                dish.setCategories(
                        inspirationDishRequest.getCategories()
                                .stream()
                                .map(Mapper::toCategory)
                                .collect(Collectors.toSet())
                );
            }

            if (inspirationDishRequest.getSourceName() != null
                    && !inspirationDishRequest.getSourceName().isBlank()) {
                dish.setSourceName(inspirationDishRequest.getSourceName());
            }

            if (inspirationDishRequest.getExternalUrl() != null
                    && !inspirationDishRequest.getExternalUrl().isBlank()) {
                dish.setExternalUrl(inspirationDishRequest.getExternalUrl());
            }
            
            inspirationDishRepository.save(dish);
        } catch (Exception error) {
            throw new InspirationDishException("Failed to update inspiration dish: " + error.getMessage());
        }
    }

    public Page<InspirationDishDTO> getInspirationDishesForPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 24, Sort.by(Sort.Direction.DESC, "id"));
        Page<InspirationDish> dishes = inspirationDishRepository.findAll(pageable);
        return dishes.map(Mapper::toInspirationDishDTO);
    }

    public Page<InspirationDishDTO> getInspirationDishesForPage(int pageNumber, String categoryName) {
        Pageable pageable = PageRequest.of(pageNumber, 24, Sort.by(Sort.Direction.DESC, "id"));
        Page<InspirationDish> dishes = inspirationDishRepository.findByCategories(categoryService.getByName(categoryName), pageable);
        return dishes.map(Mapper::toInspirationDishDTO);
    }
}

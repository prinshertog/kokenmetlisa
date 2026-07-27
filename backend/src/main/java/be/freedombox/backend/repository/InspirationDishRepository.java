package be.freedombox.backend.repository;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.domain.Dish;
import be.freedombox.backend.domain.InspirationDish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspirationDishRepository extends JpaRepository<InspirationDish, Long> {
    Page<InspirationDish> findAll(Pageable pageable);
    Page<InspirationDish> findByCategories(Category category, Pageable pageable);
    List<InspirationDish> findByCategories(Category category);
}

package be.freedombox.backend.repository;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.domain.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findAll();
    List<Dish> findDishByCategories(Category category);
}

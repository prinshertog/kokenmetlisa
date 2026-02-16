package be.freedombox.backend.repository;

import be.freedombox.backend.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    Category findByCategory(String category);
    boolean existsAllByParentCategory_Category(String parentCategory);
    Category findByPosition(int position);
    Category getCategoriesByCategory(String category);
    List<Category> streamByPosition(int position);
    boolean getCategoryByPosition(int position);
    Optional<Category> findCategoryByCategory(String category);
}

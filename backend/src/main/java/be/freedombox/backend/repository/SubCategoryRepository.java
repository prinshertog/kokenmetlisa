package be.freedombox.backend.repository;

import be.freedombox.backend.domain.SubCategory;
import be.freedombox.backend.dto.SubCategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findAll();
    SubCategory findBySubCategory(String subcategory);
}

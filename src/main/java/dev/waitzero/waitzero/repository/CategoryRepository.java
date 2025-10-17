package dev.waitzero.waitzero.repository;

import dev.waitzero.waitzero.model.entity.Category;
import dev.waitzero.waitzero.model.entity.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(CategoryName name);
}

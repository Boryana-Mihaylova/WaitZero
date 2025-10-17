package dev.waitzero.waitzero.service;


import dev.waitzero.waitzero.model.entity.Category;
import dev.waitzero.waitzero.model.entity.CategoryName;
import dev.waitzero.waitzero.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findCategoryByName(CategoryName categoryName) {
        return categoryRepository
                .findByName(categoryName)
                .orElse(null);
    }
}

package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.entity.Category;
import bg.softuni.pathfinder.model.entity.CategoryName;
import bg.softuni.pathfinder.repository.CategoryRepository;
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

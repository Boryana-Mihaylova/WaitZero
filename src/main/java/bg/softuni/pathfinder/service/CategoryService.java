package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.entity.Category;
import bg.softuni.pathfinder.model.entity.CategoryName;

public interface CategoryService {

    Category findCategoryByName(CategoryName categoryName);
}

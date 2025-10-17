package dev.waitzero.waitzero.service;

import dev.waitzero.waitzero.model.entity.Category;
import dev.waitzero.waitzero.model.entity.CategoryName;

public interface CategoryService {

    Category findCategoryByName(CategoryName categoryName);
}

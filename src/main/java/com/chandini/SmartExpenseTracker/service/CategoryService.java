package com.chandini.SmartExpenseTracker.service;



import com.chandini.SmartExpenseTracker.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    void saveCategory(Category category);

    Category getCategoryById(Long id);

    void deleteCategory(Long id);
    long getCategoryCount();

}

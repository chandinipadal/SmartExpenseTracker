package com.chandini.SmartExpenseTracker.controller;


import com.chandini.SmartExpenseTracker.entity.Category;
import com.chandini.SmartExpenseTracker.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // List Categories
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    // Show Add Form
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "category-form";
    }

    // Save Category
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    // Show Edit Form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "category-form";
    }

    // Delete Category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
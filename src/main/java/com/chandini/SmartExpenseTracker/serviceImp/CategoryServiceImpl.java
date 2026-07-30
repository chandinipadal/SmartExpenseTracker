package com.chandini.SmartExpenseTracker.serviceImp;

import com.chandini.SmartExpenseTracker.entity.Category;
import com.chandini.SmartExpenseTracker.repository.CategoryRepository;
import com.chandini.SmartExpenseTracker.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public void saveCategory(Category category) {
		categoryRepository.save(category);
	}

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public long getCategoryCount() {

		return categoryRepository.count();

	}
}
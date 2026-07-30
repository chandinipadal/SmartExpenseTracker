package com.chandini.SmartExpenseTracker.repository;


import com.chandini.SmartExpenseTracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
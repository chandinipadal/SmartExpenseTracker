package com.chandini.SmartExpenseTracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandini.SmartExpenseTracker.entity.Income;
import com.chandini.SmartExpenseTracker.entity.User;

public interface IncomeRepository extends JpaRepository<Income, Long> {

	Optional<Income> findByUser(User user);

}
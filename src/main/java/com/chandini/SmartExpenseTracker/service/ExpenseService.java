package com.chandini.SmartExpenseTracker.service;

import java.util.List;

import com.chandini.SmartExpenseTracker.dto.CategoryExpenseDTO;
import com.chandini.SmartExpenseTracker.dto.MonthlyExpenseDTO;
import com.chandini.SmartExpenseTracker.entity.Expense;
import com.chandini.SmartExpenseTracker.entity.User;

public interface ExpenseService {

    List<Expense> getAllExpenses();

    void saveExpense(Expense expense);

    Expense getExpenseById(Long id);

    void deleteExpense(Long id);

    long getExpenseCount(User user);

    List<Expense> searchExpenses(User user, String keyword);

    List<Expense> getExpensesByUser(User user);

    Double getTotalExpenseAmount(User user);

    Double getThisMonthTotal(User user);

    Double getLastMonthTotal(User user);

    // NEW METHODS
    List<Expense> getTodayExpenses(User user);

    List<Expense> getThisWeekExpenses(User user);

    List<Expense> getThisMonthExpenses(User user);

	List<CategoryExpenseDTO> getCategoryTotals(User user);
	
	
	List<MonthlyExpenseDTO> getMonthlyExpenseTotals(User user);

}
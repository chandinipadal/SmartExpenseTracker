package com.chandini.SmartExpenseTracker.serviceImp;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chandini.SmartExpenseTracker.dto.CategoryExpenseDTO;
import com.chandini.SmartExpenseTracker.dto.MonthlyExpenseDTO;
import com.chandini.SmartExpenseTracker.entity.Expense;
import com.chandini.SmartExpenseTracker.entity.User;
import com.chandini.SmartExpenseTracker.repository.ExpenseRepository;
import com.chandini.SmartExpenseTracker.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	private final ExpenseRepository expenseRepository;

	public ExpenseServiceImpl(ExpenseRepository expenseRepository) {

		this.expenseRepository = expenseRepository;
	}

	@Override
	public List<Expense> getAllExpenses() {
		return expenseRepository.findAll();
	}

	@Override
	public void saveExpense(Expense expense) {
		expenseRepository.save(expense);
	}

	@Override
	public Expense getExpenseById(Long id) {
		return expenseRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteExpense(Long id) {
		expenseRepository.deleteById(id);
	}

	@Override
	public long getExpenseCount(User user) {

		return expenseRepository.countByUser(user);

	}

	@Override
	public List<Expense> searchExpenses(User user, String keyword) {

		return expenseRepository.findByUserAndTitleContainingIgnoreCase(user, keyword);

	}

	@Override
	public List<Expense> getExpensesByUser(User user) {

		return expenseRepository.findByUser(user);

	}

	@Override
	public Double getTotalExpenseAmount(User user) {
		return expenseRepository.getTotalExpenseAmount(user);
	}

	public List<Expense> getCurrentMonthExpenses(User user) {

		LocalDate today = LocalDate.now();

		LocalDate firstDay = today.withDayOfMonth(1);

		LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

		return expenseRepository.findByUserAndDateBetween(user, firstDay, lastDay);
	}

	@Override
	public Double getThisMonthTotal(User user) {

		YearMonth currentMonth = YearMonth.now();

		LocalDate start = currentMonth.atDay(1);

		LocalDate end = currentMonth.atEndOfMonth();

		return expenseRepository.getTotalAmountBetweenDates(user, start, end);
	}

	@Override
	public Double getLastMonthTotal(User user) {

		YearMonth lastMonth = YearMonth.now().minusMonths(1);

		LocalDate start = lastMonth.atDay(1);

		LocalDate end = lastMonth.atEndOfMonth();

		return expenseRepository.getTotalAmountBetweenDates(user, start, end);
	}

	@Override
	public List<Expense> getTodayExpenses(User user) {

		return expenseRepository.findByUserAndDate(user, LocalDate.now());

	}

	@Override
	public List<Expense> getThisWeekExpenses(User user) {

	    LocalDate today = LocalDate.now();

	    LocalDate start = today.with(DayOfWeek.MONDAY);

	    LocalDate end = today.with(DayOfWeek.SUNDAY);

	    return expenseRepository.findByUserAndDateBetween(user, start, end);

	}
	
	@Override
	public List<Expense> getThisMonthExpenses(User user) {

	    YearMonth currentMonth = YearMonth.now();

	    LocalDate start = currentMonth.atDay(1);

	    LocalDate end = currentMonth.atEndOfMonth();

	    return expenseRepository.findByUserAndDateBetween(user, start, end);

	}
	@Override
	public List<CategoryExpenseDTO> getCategoryTotals(User user) {

	    YearMonth currentMonth = YearMonth.now();

	    LocalDate start = currentMonth.atDay(1);

	    LocalDate end = currentMonth.atEndOfMonth();

	    return expenseRepository.getCategoryTotals(user, start, end);
	}
	@Override
	public List<MonthlyExpenseDTO> getMonthlyExpenseTotals(User user) {

	    LocalDate startDate = LocalDate.now()
	            .minusMonths(5)
	            .withDayOfMonth(1);

	    List<Object[]> rows = expenseRepository.getMonthlyExpenseTotals(user, startDate);

	    List<MonthlyExpenseDTO> list = new ArrayList<>();

	    for (Object[] row : rows) {

	        String month = (String) row[0];
	        Double total = ((Number) row[1]).doubleValue();

	        list.add(new MonthlyExpenseDTO(month, total));
	    }

	    return list;
	}
	}


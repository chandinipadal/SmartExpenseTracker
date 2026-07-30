package com.chandini.SmartExpenseTracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chandini.SmartExpenseTracker.dto.CategoryExpenseDTO;
import com.chandini.SmartExpenseTracker.dto.MonthlyExpenseDTO;
import com.chandini.SmartExpenseTracker.entity.Expense;
import com.chandini.SmartExpenseTracker.entity.User;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	List<Expense> findByUser(User user);

	List<Expense> findByUserAndTitleContainingIgnoreCase(User user, String keyword);

	long countByUser(User user);

	@Query("SELECT COALESCE(SUM(e.amount),0) FROM Expense e WHERE e.user=:user")
	Double getTotalExpenseAmount(@Param("user") User user);

	List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);

	List<Expense> findByUserAndDate(User user, LocalDate date);

	@Query("""
			SELECT COALESCE(SUM(e.amount),0)
			FROM Expense e
			WHERE e.user=:user
			AND e.date BETWEEN :startDate AND :endDate
			""")
	Double getTotalAmountBetweenDates(@Param("user") User user, @Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	// Pie Chart Data
	@Query("""
			SELECT new com.chandini.SmartExpenseTracker.dto.CategoryExpenseDTO(
			    e.category.name,
			    SUM(e.amount)
			)
			FROM Expense e
			WHERE e.user = :user
			AND e.date BETWEEN :startDate AND :endDate
			GROUP BY e.category.name
			""")
	List<CategoryExpenseDTO> getCategoryTotals(@Param("user") User user, @Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	// Line Chart Data (Last 6 Months)
	@Query(value = """
	SELECT
	    DATE_FORMAT(e.date,'%b') AS month,
	    SUM(e.amount) AS total
	FROM expenses e
	WHERE e.user_id = :#{#user.id}
	AND e.date >= :startDate
	GROUP BY YEAR(e.date), MONTH(e.date), DATE_FORMAT(e.date,'%b')
	ORDER BY YEAR(e.date), MONTH(e.date)
	""", nativeQuery = true)
	List<Object[]> getMonthlyExpenseTotals(
	        @Param("user") User user,
	        @Param("startDate") LocalDate startDate);
	
}
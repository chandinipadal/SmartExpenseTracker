package com.chandini.SmartExpenseTracker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.chandini.SmartExpenseTracker.dto.ExpenseDto;
import com.chandini.SmartExpenseTracker.entity.Category;
import com.chandini.SmartExpenseTracker.entity.Expense;
import com.chandini.SmartExpenseTracker.entity.User;
import com.chandini.SmartExpenseTracker.repository.UserRepository;
import com.chandini.SmartExpenseTracker.service.CategoryService;
import com.chandini.SmartExpenseTracker.service.ExpenseService;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

	private final ExpenseService expenseService;
	private final CategoryService categoryService;
	private final UserRepository userRepository;

	public ExpenseController(ExpenseService expenseService, CategoryService categoryService,
			UserRepository userRepository) {

		this.expenseService = expenseService;
		this.categoryService = categoryService;
		this.userRepository = userRepository;
	}

	// ==========================
	// All Expenses + Search
	// ==========================
	@GetMapping
	public String listExpenses(@RequestParam(required = false) String keyword, Authentication authentication,
			Model model) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

		// Search
		if (keyword != null && !keyword.trim().isEmpty()) {

			model.addAttribute("expenses", expenseService.searchExpenses(user, keyword));

		} else {

			model.addAttribute("expenses", expenseService.getExpensesByUser(user));
		}

		model.addAttribute("keyword", keyword);

		return "expenses";
	}

	// ==========================
	// Add Expense Page
	// ==========================
	@GetMapping("/add")
	public String showExpenseForm(Model model) {

		model.addAttribute("expenseDto", new ExpenseDto());
		model.addAttribute("categories", categoryService.getAllCategories());

		return "expense-form";
	}

	// ==========================
	// Save / Update Expense
	// ==========================
	@PostMapping("/save")
	public String saveExpense(@ModelAttribute ExpenseDto expenseDto, Authentication authentication) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

		Category category = categoryService.getCategoryById(expenseDto.getCategoryId());

		Expense expense;

		if (expenseDto.getId() != null) {
			expense = expenseService.getExpenseById(expenseDto.getId());
		} else {
			expense = new Expense();
		}

		expense.setTitle(expenseDto.getTitle());
		expense.setAmount(expenseDto.getAmount());
		expense.setDate(expenseDto.getDate());
		expense.setNotes(expenseDto.getNotes());
		expense.setCategory(category);
		expense.setUser(user);

		expenseService.saveExpense(expense);

		return "redirect:/expenses";
	}

	// ==========================
	// Edit Expense
	// ==========================
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {

		Expense expense = expenseService.getExpenseById(id);

		ExpenseDto expenseDto = new ExpenseDto();

		expenseDto.setId(expense.getId());
		expenseDto.setTitle(expense.getTitle());
		expenseDto.setAmount(expense.getAmount());
		expenseDto.setDate(expense.getDate());
		expenseDto.setNotes(expense.getNotes());
		expenseDto.setCategoryId(expense.getCategory().getId());

		model.addAttribute("expenseDto", expenseDto);
		model.addAttribute("categories", categoryService.getAllCategories());

		return "expense-form";
	}

	// ==========================
	// Delete Expense
	// ==========================
	@GetMapping("/delete/{id}")
	public String deleteExpense(@PathVariable Long id) {

		expenseService.deleteExpense(id);

		return "redirect:/expenses";
	}

	// ==========================
	// Today's Expenses
	// ==========================
	@GetMapping("/today")
	public String todayExpenses(Authentication authentication, Model model) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

		model.addAttribute("expenses", expenseService.getTodayExpenses(user));

		return "expenses";
	}

	// ==========================
	// This Week Expenses
	// ==========================
	@GetMapping("/week")
	public String weekExpenses(Authentication authentication, Model model) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

		model.addAttribute("expenses", expenseService.getThisWeekExpenses(user));

		return "expenses";
	}

	// ==========================
	// This Month Expenses
	// ==========================
	@GetMapping("/month")
	public String monthExpenses(Authentication authentication, Model model) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

		model.addAttribute("expenses", expenseService.getThisMonthExpenses(user));

		return "expenses";
	}

}
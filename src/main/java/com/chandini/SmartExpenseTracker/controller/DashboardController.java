package com.chandini.SmartExpenseTracker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chandini.SmartExpenseTracker.entity.Income;
import com.chandini.SmartExpenseTracker.entity.User;
import com.chandini.SmartExpenseTracker.repository.UserRepository;
import com.chandini.SmartExpenseTracker.service.CategoryService;
import com.chandini.SmartExpenseTracker.service.ExpenseService;
import com.chandini.SmartExpenseTracker.service.IncomeService;

@Controller
public class DashboardController {

	private final ExpenseService expenseService;
	private final CategoryService categoryService;
	private final UserRepository userRepository;
	private final IncomeService incomeService;
	private final PasswordEncoder passwordEncoder;

	public DashboardController(ExpenseService expenseService, CategoryService categoryService,
			UserRepository userRepository, IncomeService incomeService, PasswordEncoder passwordEncoder) {

		this.expenseService = expenseService;
		this.categoryService = categoryService;
		this.userRepository = userRepository;
		this.incomeService = incomeService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/dashboard")
	public String dashboard(Authentication authentication, Model model) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

		model.addAttribute("user", user);

		model.addAttribute("totalExpenses", expenseService.getExpenseCount(user));

		model.addAttribute("totalCategories", categoryService.getCategoryCount());

		Double thisMonthSpent = expenseService.getThisMonthTotal(user);

		if (thisMonthSpent == null) {
			thisMonthSpent = 0.0;
		}

		model.addAttribute("thisMonthSpent", thisMonthSpent);

		Double lastMonthSpent = expenseService.getLastMonthTotal(user);

		if (lastMonthSpent == null) {
			lastMonthSpent = 0.0;
		}

		model.addAttribute("lastMonthSpent", lastMonthSpent);

		Income income = incomeService.getIncomeByUser(user);

		double monthlyIncome = 0.0;

		if (income != null && income.getMonthlyIncome() != null) {
			monthlyIncome = income.getMonthlyIncome();
		}

		model.addAttribute("monthlyIncome", monthlyIncome);

		double savings = monthlyIncome - thisMonthSpent;

		model.addAttribute("savings", savings);

		model.addAttribute("expenses", expenseService.getThisMonthExpenses(user).stream().limit(4).toList());

		model.addAttribute("chartData", expenseService.getCategoryTotals(user));
		model.addAttribute("monthlyChartData", expenseService.getMonthlyExpenseTotals(user));

		// LINE CHART TEMPORARILY REMOVED

		return "dashboard";
	}

	@GetMapping("/profile")
	public String profile(Authentication authentication, Model model) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

		model.addAttribute("user", user);

		return "profile";
	}

	@GetMapping("/profile/edit")
	public String editProfile(Authentication authentication, Model model) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

		model.addAttribute("user", user);

		return "profile-edit";
	}

	@PostMapping("/profile/update")
	public String updateProfile(@ModelAttribute User updatedUser, Authentication authentication) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

		user.setName(updatedUser.getName());
		user.setEmail(updatedUser.getEmail());

		userRepository.save(user);

		return "redirect:/profile";
	}

	@GetMapping("/profile/change-password")
	public String changePasswordPage() {
		return "change-password";
	}

	@PostMapping("/profile/change-password")
	public String changePassword(Authentication authentication, @RequestParam String currentPassword,
			@RequestParam String newPassword, @RequestParam String confirmPassword) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

		// Current password check
		if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
			return "redirect:/profile/change-password?error=current";
		}

		// Confirm password check
		if (!newPassword.equals(confirmPassword)) {
			return "redirect:/profile/change-password?error=confirm";
		}

		// Save new password
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

		return "redirect:/profile/change-password?success";
	}

}
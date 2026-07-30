package com.chandini.SmartExpenseTracker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.chandini.SmartExpenseTracker.entity.Income;
import com.chandini.SmartExpenseTracker.entity.User;
import com.chandini.SmartExpenseTracker.repository.UserRepository;
import com.chandini.SmartExpenseTracker.service.IncomeService;

@Controller
@RequestMapping("/income")
public class IncomeController {

	private final IncomeService incomeService;
	private final UserRepository userRepository;

	public IncomeController(IncomeService incomeService, UserRepository userRepository) {

		this.incomeService = incomeService;
		this.userRepository = userRepository;
	}

	@GetMapping
	public String incomePage(Authentication authentication, Model model) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

		Income income = incomeService.getIncomeByUser(user);

		if (income == null) {
			income = new Income();
		}

		model.addAttribute("income", income);

		return "income";
	}

	@PostMapping("/save")
	public String saveIncome(@ModelAttribute Income income, Authentication authentication) {

		User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

		Income existing = incomeService.getIncomeByUser(user);

		if (existing != null) {

			existing.setMonthlyIncome(income.getMonthlyIncome());

			incomeService.saveIncome(existing);

		} else {

			income.setUser(user);

			incomeService.saveIncome(income);
		}

		return "redirect:/dashboard";
	}
}
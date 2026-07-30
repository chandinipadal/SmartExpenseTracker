package com.chandini.SmartExpenseTracker.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chandini.SmartExpenseTracker.dto.RegisterDto;
import com.chandini.SmartExpenseTracker.entity.User;
import com.chandini.SmartExpenseTracker.repository.UserRepository;
import com.chandini.SmartExpenseTracker.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController {

	private final UserService userService;

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public AuthController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {

		this.userService = userService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {

		model.addAttribute("registerDto", new RegisterDto());

		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute RegisterDto registerDto, BindingResult result, Model model) {

		// Validation errors
		if (result.hasErrors()) {
			return "register";
		}

		// Check Password & Confirm Password
		if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {

			model.addAttribute("error", "Password and Confirm Password do not match.");

			return "register";
		}

		// Save User
		try {

			userService.registerUser(registerDto);

			return "redirect:/login";

		} catch (RuntimeException e) {

			model.addAttribute("error", e.getMessage());

			return "register";
		}
	}

	@GetMapping("/forgot-password")
	public String forgotPasswordPage() {
		return "forgot-password";
	}

	@PostMapping("/forgot-password")
	public String verifyEmail(@RequestParam String email, Model model) {

		User user = userRepository.findByEmail(email).orElse(null);

		if (user == null) {

			model.addAttribute("error", "Email not found");

			return "forgot-password";
		}

		model.addAttribute("email", email);

		return "reset-password";
	}

	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String email, @RequestParam String password) {

		User user = userRepository.findByEmail(email).orElse(null);

		if (user != null) {

			user.setPassword(passwordEncoder.encode(password));

			userRepository.save(user);

		}

		return "redirect:/login";
	}

}
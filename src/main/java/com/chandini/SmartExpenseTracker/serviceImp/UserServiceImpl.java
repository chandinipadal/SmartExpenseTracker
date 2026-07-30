package com.chandini.SmartExpenseTracker.serviceImp;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chandini.SmartExpenseTracker.dto.RegisterDto;
import com.chandini.SmartExpenseTracker.entity.User;
import com.chandini.SmartExpenseTracker.repository.UserRepository;
import com.chandini.SmartExpenseTracker.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void registerUser(RegisterDto registerDto) {

		if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
			throw new RuntimeException("Email already exists");
		}

		User user = new User();

		user.setName(registerDto.getName());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		userRepository.save(user);
	}
}
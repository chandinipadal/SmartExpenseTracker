package com.chandini.SmartExpenseTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandini.SmartExpenseTracker.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
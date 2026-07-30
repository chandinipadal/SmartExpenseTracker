package com.chandini.SmartExpenseTracker.service;

import com.chandini.SmartExpenseTracker.entity.Income;
import com.chandini.SmartExpenseTracker.entity.User;

public interface IncomeService {

    void saveIncome(Income income);

    Income getIncomeByUser(User user);

}
package com.chandini.SmartExpenseTracker.serviceImp;

import org.springframework.stereotype.Service;

import com.chandini.SmartExpenseTracker.entity.Income;
import com.chandini.SmartExpenseTracker.entity.User;
import com.chandini.SmartExpenseTracker.repository.IncomeRepository;
import com.chandini.SmartExpenseTracker.service.IncomeService;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public void saveIncome(Income income) {
        incomeRepository.save(income);
    }

    @Override
    public Income getIncomeByUser(User user) {

        return incomeRepository.findByUser(user).orElse(null);

    }
}
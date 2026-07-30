package com.chandini.SmartExpenseTracker.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExpenseDto {
     private Long id;

    private String title;

    private Double amount;

    private LocalDate date;

    private String notes;

    private Long categoryId;

}
package com.chandini.SmartExpenseTracker.dto;

public class MonthlyExpenseDTO {

    private String month;

    private Double total;

    public MonthlyExpenseDTO() {
    }

    public MonthlyExpenseDTO(String month, Double total) {
        this.month = month;
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
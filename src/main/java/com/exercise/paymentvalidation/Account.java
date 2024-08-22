package com.exercise.paymentvalidation;
import org.springframework.data.annotation.Id;

public class Account {
    @Id
    private Long id;
    private String currency;
    private double balance;

    // Constructor
    public Account(Long id, String currency, double balance) {
        this.id = id;
        this.currency = currency;
        this.balance = balance;
    }

    // Getters and Setters

    public String getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
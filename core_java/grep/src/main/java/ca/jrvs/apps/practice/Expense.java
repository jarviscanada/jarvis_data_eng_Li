package ca.jrvs.apps.practice;

import java.util.Objects;

public class Expense {

  private double amount;
  private String category;
  private String date;

  public Expense(double amount, String category, String date) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than 0");
    }

    if (category == null || category.trim().isEmpty()) {
      throw new IllegalArgumentException("Category cannot be null or empty");
    }

    if (date == null || date.trim().isEmpty()) {
      throw new IllegalArgumentException("Date cannot be null or empty");
    }

    this.amount = amount;
    this.category = category;
    this.date = date;
  }

  public Expense() {

  }

  public double getAmount() {
    return amount;
  }

  public String getCategory() {
    return category;
  }

  public String getDate() {
    return date;
  }
}

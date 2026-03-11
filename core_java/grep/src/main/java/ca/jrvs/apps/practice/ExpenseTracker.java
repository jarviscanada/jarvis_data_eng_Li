package ca.jrvs.apps.practice;

import java.util.*;
import java.util.stream.Collectors;

public class ExpenseTracker extends Expense{

  private List<Expense> expenses;

  public static void main(String[] args) {

    ExpenseTracker tracker = new ExpenseTracker();

    tracker.addExpense(new Expense(1000, "Rent", "2026-03-01"));
    tracker.addExpense(new Expense(20, "Food", "2026-03-02"));
    tracker.addExpense(new Expense(15, "Food", "2026-03-02"));
    tracker.addExpense(new Expense(50, "Transport", "2026-03-03"));
    tracker.addExpense(new Expense(60, "Entertainment", "2026-03-03"));

    System.out.println("Total spent: " + tracker.totalSpent());
    System.out.println("Food total: " + tracker.totalByCategory("Food"));
    System.out.println("Transport total: " + tracker.totalByCategory("Transport"));
    System.out.println("Entertainment total: " + tracker.totalByCategory("Entertainment"));
    System.out.println("Rent total: " + tracker.totalByCategory("Rent"));
    System.out.println("Top category: " + tracker.topCategory());
  }

  public ExpenseTracker() {
    super();
    this.expenses = new ArrayList<>();
  }

  public void addExpense(Expense e) {
    if (e == null) {
      throw new IllegalArgumentException("Expense cannot be null");
    }
    expenses.add(e);
  }

  // Total spent
  public double totalSpent() {
    return expenses.stream()
        .mapToDouble(Expense::getAmount)
        .sum();
  }

  // Total spent by category
  public double totalByCategory(String category) {
    if (category == null || category.trim().isEmpty()) {
      throw new IllegalArgumentException("Category cannot be null or empty");
    }

    return expenses.stream()
        .filter(e -> e.getCategory().equalsIgnoreCase(category))
        .mapToDouble(Expense::getAmount)
        .sum();
  }

  // Find category with highest spending
  public String topCategory() {

    if (expenses.isEmpty()) {
      return "None";
    }

    Map<String, Double> totals = expenses.stream()
        .collect(Collectors.groupingBy(
            Expense::getCategory,
            Collectors.summingDouble(Expense::getAmount)
        ));

    return totals.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .get()
        .getKey();
  }

  // Filter expenses by category
  public List<Expense> getExpensesByCategory(String category) {

    return expenses.stream()
        .filter(e -> e.getCategory().equalsIgnoreCase(category))
        .collect(Collectors.toList());
  }

  // Find largest expense
  public Optional<Expense> largestExpense() {
    return expenses.stream()
        .max(Comparator.comparingDouble(Expense::getAmount));
  }

  // Group expenses by category
  public Map<String, List<Expense>> groupByCategory() {
    return expenses.stream()
        .collect(Collectors.groupingBy(Expense::getCategory));
  }

  // Sort expenses by amount
  public List<Expense> sortByAmount() {
    return expenses.stream()
        .sorted(Comparator.comparingDouble(Expense::getAmount))
        .collect(Collectors.toList());
  }
}
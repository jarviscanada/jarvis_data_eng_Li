package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BankAccount {
  private String accountNumber;
  private String ownerName;
  private double balance;

  public BankAccount(String accountNumber, String ownerName, double balance) {
    if (accountNumber == null || accountNumber.trim().isEmpty()) {
      throw new IllegalArgumentException("Account number cannot be null or empty");
    }
    if (ownerName == null || ownerName.trim().isEmpty()) {
      throw new IllegalArgumentException("Owner name cannot be null or empty");
    }
    if (balance <= 0) {
      throw new IllegalArgumentException("Balance must be greater than 0");
    }

    this.accountNumber = accountNumber;
    this.ownerName = ownerName;
    this.balance = balance;
  }

  public static void main(String[] args){
    BankAccount account = new BankAccount("1234","Jason", 100);
    List<Double> deposits = new ArrayList<>();
    deposits.add(32.1);
    deposits.add(41.1);
    deposits.add(121.2);
    deposits.add(23.2);
    List<Double> withdrawal = new ArrayList<>();
    withdrawal.add(22.1);
    withdrawal.add(12.1);
    withdrawal.add(100.2);
    withdrawal.add(20.1);
    System.out.println(account.deposit(deposits));
    System.out.println(account.withdrawal(withdrawal));
    System.out.println(account.getAccountInfo());
    System.out.println(account.largestTransaction(deposits));

  }

  public List<Double> deposit(List<Double> amounts){
    List<Double> deposits = Collections.singletonList(
        amounts.stream().filter(a -> a > 0).mapToDouble(Double::doubleValue).sum());
    balance += deposits.get(0);
    return deposits;
  }

  public Optional<Double> largestTransaction(List<Double> amounts) {
    return amounts.stream().max(Comparator.comparingDouble(Double::doubleValue));
  }

  public List<Double> withdrawal(List<Double>  amounts) {
    List<Double> withdraws = Collections.singletonList(
        amounts.stream().filter(a -> a > 0).mapToDouble(Double::doubleValue).sum());
    if (withdraws.get(0) <= 0) {
      balance = 0;
    } else {
      balance -= withdraws.get(0);
    }
    return withdraws;
  }

  public double getBalance() {
    return balance;
  }

  public String getAccountInfo(){
    String accountInfo = String.format("Account: %s, Owner: %s, Balance: $%.2f.",
        accountNumber, ownerName, balance);
    return accountInfo;
  }


}

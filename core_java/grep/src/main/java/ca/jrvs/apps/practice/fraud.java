package ca.jrvs.apps.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class fraud {

  public static List<Integer> detectFraud(List<Integer> transactions, int threshold) {
    if (transactions == null) {
      throw new IllegalArgumentException("Transaction list cannot be null");
    }
    if (threshold < 0) {
      throw new IllegalArgumentException("Threshold cannot be negative");
    }
    List<Integer> sus = transactions.stream().filter(t -> t > threshold).collect(
        Collectors.toList());
    return sus;
  }
  public static void main(String[] args) {
    List<Integer> t = new ArrayList<>();
    t.add(20);
    t.add(40);
    t.add(5000);
    t.add(30);
    System.out.println(detectFraud(t, 1000));
  }
  }

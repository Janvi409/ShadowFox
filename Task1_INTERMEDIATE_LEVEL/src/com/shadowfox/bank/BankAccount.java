package com.shadowfox.bank;

//BankAccount.java
import java.util.ArrayList;
import java.util.List;

public class BankAccount {
 private String accountNumber;
 private String accountHolder;
 private double balance;
 private List<Transaction> transactions;

 public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
     this.accountNumber = accountNumber;
     this.accountHolder = accountHolder;
     this.balance = initialBalance;
     this.transactions = new ArrayList<>();
 }

 public String getAccountNumber() {
	return accountNumber;
}

 public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
 }

 public String getAccountHolder() {
	return accountHolder;
 }

 public void setAccountHolder(String accountHolder) {
	this.accountHolder = accountHolder;
 }

 public synchronized void deposit(double amount) {
     if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive");
     balance += amount;
     transactions.add(new Transaction("DEPOSIT", amount));
     
 }

 public synchronized void withdraw(double amount) throws InsufficientFundsException {
     if (amount <= 0) throw new IllegalArgumentException("Withdrawal must be positive");
     if (amount > balance) throw new InsufficientFundsException("Insufficient balance");
     balance -= amount;
     transactions.add(new Transaction("WITHDRAWAL", amount));
 }

 public double getBalance() {
     return balance;
 }

 public List<Transaction> getTransactionHistory() {
     return new ArrayList<>(transactions); // return copy for safety
 }
}
package com.shadowfox.bank.test;

import com.shadowfox.bank.BankAccount;
import com.shadowfox.bank.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("12345", "Janvi Shinde", 1000.0);
    }

    @Test
    void testDeposit() {
        account.deposit(500);
        assertEquals(1500, account.getBalance(), 0.001);
        assertEquals(1, account.getTransactionHistory().size());
        assertEquals("DEPOSIT", account.getTransactionHistory().get(0).getType());
       
    }

    @Test
    void testWithdraw() throws InsufficientFundsException {
        account.withdraw(400);
        assertEquals(600, account.getBalance(), 0.001);
        assertEquals(1, account.getTransactionHistory().size());
        assertEquals("WITHDRAWAL", account.getTransactionHistory().get(0).getType());
    }

    @Test
    void testWithdrawInsufficientFunds() {
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            account.withdraw(2000);
        });
        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    void testTransactionHistory() throws InsufficientFundsException {
        account.deposit(200);
        account.withdraw(100);
        assertEquals(2, account.getTransactionHistory().size());
    }

    @Test
    void testConcurrentWithdraw() throws InterruptedException {
        Runnable withdrawTask = () -> {
            try {
                account.withdraw(600);
            } catch (InsufficientFundsException e) {
                System.out.println("Caught exception in thread: " + e.getMessage());
            }
        };

        Thread t1 = new Thread(withdrawTask);
        Thread t2 = new Thread(withdrawTask);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // Only one withdrawal should succeed
        assertTrue(account.getBalance() == 400 || account.getBalance() == 0);
    }
}

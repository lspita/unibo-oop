package it.unibo.inheritance.impl;

import it.unibo.inheritance.api.BankAccount;

public class StrictBankAccount implements BankAccount {

    private static final double ATM_TRANSACTION_FEE = 1;
    private static final double MANAGEMENT_FEE = 5;
    private static final double TRANSACTION_FEE = 0.1;

    private final int id;
    private double balance;
    private int transactions;

    public StrictBankAccount(final int id, final double balance) {
        this.id = id;
        this.balance = balance;
    }

    private void transactionOp(final int id, final double amount) {
        if (checkUser(id)) {
            this.balance += amount;
            this.incrementTransactions();
        }
    }

    public void deposit(final int id, final double amount) {
        this.transactionOp(id, amount);
    }

    public void withdraw(final int id, final double amount) {
        if (isWithdrawAllowed(amount)) {
            this.transactionOp(id, -amount);
        }
    }

    public void depositFromATM(final int id, final double amount) {
        this.deposit(id, amount - StrictBankAccount.ATM_TRANSACTION_FEE);
    }

    public void withdrawFromATM(final int id, final double amount) {
        this.withdraw(id, amount + StrictBankAccount.ATM_TRANSACTION_FEE);
    }

    private void incrementTransactions() {
        transactions++;
    }

    public double getBalance() {
        return this.balance;
    }

    public int getTransactionsCount() {
        return transactions;
    }

    public void chargeManagementFees(final int id) {
        final double feeAmount = MANAGEMENT_FEE + transactions * StrictBankAccount.TRANSACTION_FEE;
        if (checkUser(id) && isWithdrawAllowed(feeAmount)) {
            balance -= feeAmount;
            transactions = 0;
        }
    }

    private boolean checkUser(final int id) {
        return this.id == id;
    }

    private boolean isWithdrawAllowed(final double amount) {
        return balance >= amount;
    }
}

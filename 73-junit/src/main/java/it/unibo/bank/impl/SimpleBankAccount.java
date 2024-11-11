package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;

/**
 * Simple BankAccount interface implementation.
 */
public class SimpleBankAccount implements BankAccount {

    /**
     * ATM Transaction Fee changed to the bank account.
     */
    public static final double ATM_TRANSACTION_FEE = 1;

    /**
     * Management fee charged to the bank account.
     */
    public static final double MANAGEMENT_FEE = 5;

    private final AccountHolder holder;
    private double balance;
    private int transactions;

    /**
     * Builds a new {@link SimpleBankAccount}.
     * @param accountHolder the account holder
     * @param balance the initial balance
     */
    public SimpleBankAccount(final AccountHolder accountHolder, final double balance) {
        this.holder = accountHolder;
        this.balance = balance;
        this.transactions = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chargeManagementFees(final int id) {
        if (checkUser(id)) {
            this.balance -= MANAGEMENT_FEE;
            resetTransactions();
        } else {
            throw new IllegalArgumentException("ID not corresponding: cannot charge management fees");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deposit(final int id, final double amount) {
        this.transactionOp(id, amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void depositFromATM(final int id, final double amount) {
        this.deposit(id, amount - ATM_TRANSACTION_FEE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountHolder getAccountHolder() {
        return holder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBalance() {
        return this.balance;
    }

    /**
     * Sets the balance of the bank account.
     *
     * @param balance the new balance
     */
    protected void setBalance(final double balance) {
        this.balance = balance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTransactionsCount() {
        return this.transactions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void withdraw(final int id, final double amount) {
        this.transactionOp(id, -amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void withdrawFromATM(final int id, final double amount) {
        this.withdraw(id, amount + ATM_TRANSACTION_FEE);
    }

    /**
     * Checks that the user id corresponds to the owner of this bank account.
     * @param id the user id
     * @return true if the id corresponds to the owner of the bank account, otherwise false.
     */
    protected boolean checkUser(final int id) {
        return this.getAccountHolder().getUserID() == id;
    }

    /**
     * Increments the number of transactions performed in the bank account by 1.
     */
    protected void incrementTransactions() {
        this.transactions++;
    }

    /**
     * Resets the number of transactions performed in the bank account.
     */
    protected void resetTransactions() {
        this.transactions = 0;
    }

    private void transactionOp(final int id, final double amount) {
        if (checkUser(id)) {
            this.balance += amount;
            this.incrementTransactions();
        } else {
            throw new IllegalArgumentException("ID not corresponding: cannot perform transaction");
        }
    }
}

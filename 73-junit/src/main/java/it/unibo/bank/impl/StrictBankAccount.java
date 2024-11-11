package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;

/**
 * Simple Bank Account that adds checks to the basic operations.
 */
public class StrictBankAccount extends SimpleBankAccount {

    /**
     * Transaction fee charged to the bank account.
     */
    public static final double TRANSACTION_FEE = 0.1;

    /**
     * Builds a new {@link StrictBankAccount}.
     *
     * @param accountHolder the account holder
     * @param balance the initial balance
     */
    public StrictBankAccount(final AccountHolder accountHolder, final double balance) {
        super(accountHolder, balance);
    }

    /**
     * Beyond withdrawing the MANAGEMENT_FEE, it withdraws a TRANSACTION_FEE for each
     * transaction performed in the bank account.
     * The amount is not taken if the bank account balance is lower.
     * @param usrID the account holder identifier.
     * @throws IllegalArgumentException if the id does not correspond.
     */
    @Override
    public void chargeManagementFees(final int usrID) {
        final double feeAmount = MANAGEMENT_FEE + getTransactionsCount() * TRANSACTION_FEE;
        if (checkUser(usrID)) {
            if (isWithdrawAllowed(feeAmount)) {
                setBalance(getBalance() - feeAmount);
                resetTransactions();
            }
        } else {
            throw new IllegalArgumentException("ID not corresponding: cannot charge management fees");
        }
    }


    /**
     * Takes an amount of money from the bank account.
     *
     * @param usrID the account holder identifier.
     * @param amount the amount of money to withdraw into the bank account.
     * @throws IllegalArgumentException if the amount to withdraw is a negative value.
     * @throws IllegalArgumentException if the balance is lower than the amount to take.
     */
    @Override
    public void withdraw(final int usrID, final double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot withdraw a negative amount");
        }
        if (isWithdrawAllowed(amount)) {
            super.withdraw(usrID, amount);
        } else {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }

    /**
     * Checks that the withdrawal is allowed on the bank account.
     * @param amount the amount of money to be taken from the account.
     * @return true if the withdrawal is allowed, otherwise false.
     */
    protected boolean isWithdrawAllowed(final double amount) {
        return amount > 0 && getBalance() > amount;
    }
}

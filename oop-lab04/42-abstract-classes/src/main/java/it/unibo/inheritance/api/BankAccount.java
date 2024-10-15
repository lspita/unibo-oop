package it.unibo.inheritance.api;

public interface BankAccount {
    /*
     * This method is used to charge the management fees on the account balance
     * (they are computed every few months). This method does not return the amount
     * computed, it directly collects the amount from the balance.
     */
    void chargeManagementFees(int id);

    void deposit(int id, double amount);

    void depositFromATM(int id, double amount);

    AccountHolder getAccountHolder();

    double getBalance();

    int getTransactionsCount();

    void withdraw(int id, double amount);

    void withdrawFromATM(int id, double amount);
}

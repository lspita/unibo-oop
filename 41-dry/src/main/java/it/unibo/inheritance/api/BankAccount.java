package it.unibo.inheritance.api;

public interface BankAccount {
    void withdraw(int id, double amount);

    void deposit(int id, double amount);

    void depositFromATM(int id, double amount);

    void withdrawFromATM(int id, double amount);

    /*
     * This method is used to charge the management fees on the account balance
     * (they are computed every few months). This method does not return the amount
     * computed, it directly collects the amount from the balance.
     */
    void chargeManagementFees(int id);

    double getBalance();

    int getTransactionsCount();
}

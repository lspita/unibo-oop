package it.unibo.bank.api;

/**
 * Representation of a Banking account.
 */
public interface BankAccount {
    /**
     * This method is used to charge the management fees (MANAGEMENT_FEE) on the account balance
     * (they are computed every few months). This method does not return the amount
     * computed, it directly collects the amount from the balance.
     * It also resets the transaction number of the bank account.
     * The operation is performed only if the user ID is corresponding.
     *
     * @param id the account holder identifier.
     * @throws IllegalArgumentException if the user ID does not correspond.
     */
    void chargeManagementFees(int id);

    /**
     * Increments the number of transactions and adds the amount to the bank account's balance.
     * The deposit is performed only if the user ID is corresponding.
     *
     * @param id the account holder identifier.
     * @param amount the amount of money to deposit into the bank account.
     * @throws IllegalArgumentException if the user ID does not correspond.
     */
    void deposit(int id, double amount);

    /**
     * Increments the transaction number and adds the amount of money to the bank account's balance.
     * The deposit from the ATM includes also fees (ATM_TRANSACTION_FEE) that are directly taken from the account
     * holder's balance.
     * The deposit is performed only if the user ID is corresponding.
     * @param id the account holder identifier.
     * @param amount the amount of money to deposit into the bank account.
     * @throws IllegalArgumentException if the user ID does not correspond.
     */
    void depositFromATM(int id, double amount);

    /**
     * Account holder of this bank account.
     *
     * @return the account holder of this bank account.
     */
    AccountHolder getAccountHolder();

    /**
     * The balance of this bank account.
     *
     * @return the balance of this bank account.
     */
    double getBalance();

    /**
     * The number of transactions performed on this bank account.
     *
     * @return the transactions count of this bank account.
     */
    int getTransactionsCount();

    /**
     * Increments the number of transactions and removes the amount to the bank account's balance.
     * The withdrawal is performed only if the user ID is corresponding and the amount is lower than the
     * account's balance.
     * @param id the account holder identifier.
     * @param amount the amount of money to withdraw into the bank account.
     * @throws IllegalArgumentException if the user ID does not correspond.
     */
    void withdraw(int id, double amount);

    /**
     * Increments the transaction number and takes the amount of money to the bank account's balance.
     * The withdrawal from the ATM includes also fees (ATM_TRANSACTION_FEE) that are directly taken from the account
     * holder's balance.
     * The deposit is performed only if the user ID is corresponding.
     * @param id the account holder identifier.
     * @param amount the amount of money to deposit into the bank account.
     * @throws IllegalArgumentException if the user ID does not correspond.
     */
    void withdrawFromATM(int id, double amount);
}

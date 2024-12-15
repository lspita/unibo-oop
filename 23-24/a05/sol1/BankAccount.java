package a05.sol1;

/**
 * An interface to model a bankaccount, holding a certain non-negative amount of money (balance)
 * with the ability of depositing additional momey, or withdrawing money.
 */
public interface BankAccount {

    /**
     * @return the current balance
     */
    int balance();

    /**
     * @param amount
     * Deposits additional amount of money
     */
    void deposit(int amount);

    /**
     * @param amount
     * @return whether amount of money has been withdrawn from the account
     */
    boolean withdraw(int amount);

}

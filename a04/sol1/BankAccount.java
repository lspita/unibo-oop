package a04.sol1;

/**
 * An interface modelling a BankAccount, initially with balance 0, and where one can 
 * deposit money or withdraw money (withdraw = prelevare)
 */
public interface BankAccount {

   /**
    * @return the current balance, initialy 0, that is, how much money this account contains
    */
   int getBalance();

   /**
    * @param amount
    * puts an additional amount of money in the account
    */
   void deposit(int amount);

   /**
   * @param amount
   * removes an amount of money from the account
   * it depends on the implementation to decide wheter there will be taxes (fees) and
   * what happens if you take more money than you actually have
   * @return whether withdrawal succeeded: if it did not succeed the balance remains unchanged
   */
   boolean withdraw(int amount);
    
}

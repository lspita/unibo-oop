package a05.sol1;

public interface BankAccountFactory {

    /**
     * @return a standard bank account, without any fee or any specific control on inputs
     */
    BankAccount simple();

    /**
     * @return a bank account applying a fee to each withdraw, without any specific control on inputs
     */
    BankAccount withFee(int fee);

    /**
     * @return a bank account checking if inputs are ok, that is, amounts and balance are never negative
     * if some check is not passed, a IllegalStateException will be thrown by the specific method invocation.
     */
    BankAccount checked();

     /**
     * @return a bank account where if some checking fails (amounts and/or balance become negative), then
     * the account becomes blocked, hence withdraw and deposit are simply ignored.
     */
    BankAccount gettingBlocked();

    /**
     * @param primary
     * @param secondary
     * @return a bankaccount actually hiding a primary and secondary bankaccounts, such that:
     * - the balance is the sum of the two balances
     * - deposit is always made on the bankaccount with minimum balance
     * - withdraw is performed on the primary if possible, otherwise on the secondary
     */
    BankAccount pool(BankAccount primary, BankAccount secondary);

}

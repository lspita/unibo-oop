package a04.sol1;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * An interface modelling a factory of BankAccount, changing in how they handle withdrawal of money
 */
public interface BankAccountFactory {

    /**
     * @return the simplest bank account, where you can withdraw money that you have, and without any additional tax/fee
     */
    BankAccount createBasic();

    /**
     * @param feeFunction, e.g. 'amount -> amount > 50 ? 1 : 0' means that withdrawals greater 
     * than 50 incur in 1 of additional tax, 0 otherwise
     * @return a bank account always applying a fee as computed by feeFunction
     */
    BankAccount createWithFee(UnaryOperator<Integer> feeFunction);

    /**
     * @param allowedCredit, e.g. 'credit -> credit < 50' means we allow a maximum credit of 50 euros, i.e., balance = -50
     * @param rateFunction, e.g. 'credit -> 10' means that independently of the credit you ask, an additional tax=10 is applied
     * @return a bank account allowing you some credit, but with a tax
     */
    BankAccount createWithCredit(Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction);

    /**
     * @param blockingPolicy, e.g. '(amount,balance) -> amount > balance' means that if we try to withdraw an amount
     * that is greater than current balance, the account is blocked an no more withdrawals will be possible
     * @return a bank account where an unproper withdraw can block it forever
     */
    BankAccount createWithBlock(BiPredicate<Integer,Integer> blockingPolicy);
    
    /**
     * @param feeFunction, as in createWithFee
     * @param allowedCredit, as in creatWithCredit
     * @param rateFunction, as in creatWithCredit
     * @return a bank account applying both a fee AND allowing you some credit, but with a tax
     */
    BankAccount createWithFeeAndCredit(UnaryOperator<Integer> feeFunction, Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction);
}

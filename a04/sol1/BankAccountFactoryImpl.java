package a04.sol1;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class BankAccountFactoryImpl implements BankAccountFactory {

    private static abstract class AbstractBankAccount implements BankAccount {
        protected abstract void setBalance(int amount);
    }

    private static class BankAccountDecorator extends AbstractBankAccount {
        private final AbstractBankAccount base;
        public BankAccountDecorator(AbstractBankAccount base){
            this.base = base;
        }
        public int getBalance() {
            return base.getBalance();
        }
        public void deposit(int amount) {
            base.deposit(amount);
        }
        public boolean withdraw(int amount) {
            return base.withdraw(amount);
        }
        
        protected void setBalance(int amount) {
            base.setBalance(amount);
        }
    }

    @Override
    public BankAccount createBasic() {
        return refinedCreateBasic();
    }

    private AbstractBankAccount refinedCreateBasic() {
        return new AbstractBankAccount() {

            private int balance = 0;

            @Override
            public int getBalance() {
                return this.balance;
            }

            @Override
            protected void setBalance(int amount){
                this.balance = amount;
            }

            @Override
            public void deposit(int amount) {
                this.balance = this.balance + amount;
            }

            @Override
            public boolean withdraw(int amount) {
                if (this.balance < amount){
                    return false;
                }
                this.balance = this.balance - amount;
                return true;
            }
        };
    }

    @Override
    public BankAccount createWithFee(UnaryOperator<Integer> feeFunction) {
        return refinedCreateWithFee(feeFunction, refinedCreateBasic());
    }

    private AbstractBankAccount refinedCreateWithFee(UnaryOperator<Integer> feeFunction, AbstractBankAccount base) {
        return new BankAccountDecorator(base){
            @Override
            public boolean withdraw(int amount){
                return super.withdraw(amount + feeFunction.apply(amount));
            }
        };
    }

    @Override
    public BankAccount createWithCredit(Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction){
        return refinedCreateWithCredit(allowedCredit, rateFunction);
    }

    public AbstractBankAccount refinedCreateWithCredit(Predicate<Integer> allowedCredit, UnaryOperator<Integer> rateFunction) {
        return new BankAccountDecorator(refinedCreateBasic()){
            @Override
            public boolean withdraw(int amount){
                if (super.withdraw(amount)){
                    return true;
                }
                final var credit = amount - this.getBalance();
                if (allowedCredit.test(credit)){
                    setBalance(-credit-rateFunction.apply(-credit));
                    return true;
                }
                return false;
            }
        };
    }

    @Override
    public BankAccount createWithBlock(BiPredicate<Integer, Integer> blockingPolicy) {
        return new BankAccountDecorator(refinedCreateBasic()){
            private boolean blocked = false;

            @Override
            public boolean withdraw(int amount){
                if (blocked) {
                    return false;
                }
                if (blockingPolicy.test(amount, super.getBalance())){
                    blocked = true;
                    return false;
                }
                return super.withdraw(amount);
            }
        };
    }

    @Override
    public BankAccount createWithFeeAndCredit(UnaryOperator<Integer> feeFunction, Predicate<Integer> allowedCredit,
            UnaryOperator<Integer> rateFunction) {
       return refinedCreateWithFee(feeFunction, refinedCreateWithCredit(allowedCredit, rateFunction));
    }

   
}

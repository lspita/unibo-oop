package a05.sol1;

public class BankAccountFactoryImpl implements BankAccountFactory {

    @Override
    public BankAccount simple() {
        return new AbstractBankAccount() {

            @Override
            protected boolean canDeposit(int amount) {
                return true;
            }

            @Override
            protected void onDisallowedDeposit() {
            }

            @Override
            protected void onDisallowedWithdraw() {
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                return balance() >= amount;
            }
            
        };
    }

    @Override
    public BankAccount withFee(int fee) {
        return new AbstractBankAccount() {

            @Override
            protected boolean canDeposit(int amount) {
                return true;
            }

            @Override
            protected void onDisallowedDeposit() {
            }

            @Override
            protected void onDisallowedWithdraw() {
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return balance() - amount - fee;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                return balance() >= amount + fee;
            }
        };
    }

    @Override
    public BankAccount gettingBlocked() {
        return new AbstractBankAccount() {
            
            private boolean blocked = false;

            @Override
            protected boolean canDeposit(int amount) {
                if (blocked || amount < 0) {
                    blocked = true;
                    return false;
                } 
                return true;
            }

            @Override
            protected void onDisallowedDeposit() {
            }

            @Override
            protected void onDisallowedWithdraw() {
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                if (blocked || amount < 0 || balance() < amount) {
                    blocked = true;
                }
                return !blocked;
            }
        };
    }

    @Override
    public BankAccount checked() {
        return new AbstractBankAccount() {

            @Override
            protected boolean canDeposit(int amount) {
                if (amount < 0) {
                    return false;
                } 
                return true;
            }

            @Override
            protected void onDisallowedDeposit() {
                throw new IllegalStateException();
            }

            @Override
            protected void onDisallowedWithdraw() {
                throw new IllegalStateException();
            }

            @Override
            protected int newBalanceOnWithdraw(int amount) {
                return balance() - amount;
            }

            @Override
            protected boolean canWithdraw(int amount) {
                return amount >= 0 && balance() >= amount;
            }
        };
    }

    @Override
    public BankAccount pool(BankAccount primary, BankAccount secondary) {
        return new BankAccount() {

            @Override
            public int balance() {
                return primary.balance() + secondary.balance();
            }

            @Override
            public void deposit(int amount) {
                (primary.balance() <= secondary.balance() ? primary : secondary).deposit(amount);
            }

            @Override
            public boolean withdraw(int amount) {
                return primary.withdraw(amount) || secondary.withdraw(amount);
            }
            
        };
    }

}

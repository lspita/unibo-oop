package a05.sol1;

/**
 * An abstract class defining a template for various possible implementations. Specifically, deposit and withdraw methods
 * are template methods to be used as-is, relying on a number of abstract methods to be redefined in subclasses.
 */
public abstract class AbstractBankAccount implements BankAccount {

    private int balance = 0;

    @Override
    public final int balance() {
        return balance;
    }

    @Override
    public void deposit(int amount) {
        if (this.canDeposit(amount)){
            this.balance += amount;
        } else {
            this.onDisallowedDeposit();
        }
    }

    /**
     * @param amount
     * @return whether it is possible to deposit amount
     */
    protected abstract boolean canDeposit(int amount);

    /**
     * Code to be executed if deposit was not allowed
     */
    protected abstract void onDisallowedDeposit();

    @Override
    public boolean withdraw(int amount) {
        if (this.canWithdraw(amount)){
            this.balance = this.newBalanceOnWithdraw(amount);
            return true;
        } else {
            this.onDisallowedWithdraw();
            return false;
        }
    }

    /**
     * Code to be executed if withdraw was not allowed
     */
    protected abstract void onDisallowedWithdraw();

    /**
     * @param amount
     * @return the new balance if amount is withdrawn
     */
    protected abstract int newBalanceOnWithdraw(int amount);

    /**
     * @param amount
     * @return whether it is possible to withdraw amount
     */
    protected abstract boolean canWithdraw(int amount);
}

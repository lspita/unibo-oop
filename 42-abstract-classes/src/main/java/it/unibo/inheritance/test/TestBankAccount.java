package it.unibo.inheritance.test;

import it.unibo.inheritance.api.AccountHolder;
import it.unibo.inheritance.api.BankAccount;
import it.unibo.inheritance.impl.ExtendedStrictBankAccount;
import it.unibo.inheritance.impl.SimpleBankAccount;

public class TestBankAccount {
    private static final int INTIAL_AMOUNT = 10000;
    private static final int WITHDRAW_AMOUNT = 15000;
    private static final String ACCOUNT = "'s account balance is ";

    private TestBankAccount() {
    }

    private static void requireSame(final String what, final Object first, final Object second) {
        if (first.equals(second)) {
            System.out.println("# CORRECT: " + what + " " + first + " is equal to " + what + " " + second);
        } else {
            final var error = "# ERROR: " + what + " " + first + " is not equal to " + what + " " + second + " #";
            final var decorationBuilder = new StringBuilder(error.length());
            for (int i = 0; i < error.length(); i++) {
                decorationBuilder.append("#");
            }
            final var decoration = decorationBuilder.toString();
            System.out.println(decoration);
            System.out.println(error);
            System.out.println(decoration);
            System.exit(1);
        }
    }

    private static void requireSameAmount(final BankAccount account1, final BankAccount account2) {
        requireSame("amount in account", account1.getBalance(), account2.getBalance());
        requireSame("transactions count", account1.getTransactionsCount(), account2.getTransactionsCount());
    }

    private static void requireAccountsToWorkTheSameWay(final BankAccount account1, final BankAccount account2) {
        // First deposit
        account1.deposit(account1.getAccountHolder().getUserID(), INTIAL_AMOUNT);
        account2.deposit(account2.getAccountHolder().getUserID(), INTIAL_AMOUNT);
        requireSameAmount(account1, account2);
        // Withdraw
        account1.withdraw(account1.getAccountHolder().getUserID(), WITHDRAW_AMOUNT);
        account2.withdraw(account2.getAccountHolder().getUserID(), WITHDRAW_AMOUNT);
        requireSameAmount(account1, account2);
        // Second deposit
        account1.deposit(account1.getAccountHolder().getUserID(), INTIAL_AMOUNT);
        account2.deposit(account2.getAccountHolder().getUserID(), INTIAL_AMOUNT);
        requireSameAmount(account1, account2);
        // Withdraw
        account1.withdraw(account1.getAccountHolder().getUserID(), WITHDRAW_AMOUNT);
        account2.withdraw(account2.getAccountHolder().getUserID(), WITHDRAW_AMOUNT);
        requireSameAmount(account1, account2);
        // Management fees
        account1.chargeManagementFees(account1.getAccountHolder().getUserID());
        account2.chargeManagementFees(account2.getAccountHolder().getUserID());
        requireSameAmount(account1, account2);
    }

    public static void main(final String[] args) {
        final AccountHolder aRossi = new AccountHolder("Andrea", "Rossi", 1);
        final AccountHolder aBianchi = new AccountHolder("Alex", "Bianchi", 2);
        /*
         * Change the two accounts to verify that the behaviour of restricted and simple bank account are preserved
         */
        final BankAccount restricted = null;
        final BankAccount extendedStrict = new ExtendedStrictBankAccount(aBianchi, 0);
        final BankAccount simpleBankAccount = new SimpleBankAccount(aRossi, 0);
        final BankAccount classicBankAccount = null;
        requireAccountsToWorkTheSameWay(restricted, extendedStrict);
        requireAccountsToWorkTheSameWay(simpleBankAccount, classicBankAccount);
    }
}

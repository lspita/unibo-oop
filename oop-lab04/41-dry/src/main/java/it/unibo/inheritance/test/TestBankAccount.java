package it.unibo.inheritance.test;

import it.unibo.inheritance.api.AccountHolder;
import it.unibo.inheritance.api.BankAccount;
import it.unibo.inheritance.impl.SimpleBankAccount;
import it.unibo.inheritance.impl.StrictBankAccount;

public class TestBankAccount {
    private static final int INTIAL_AMOUNT = 10000;
    private static final int WITHDRAW_AMOUNT = 15000;

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

    private static void requireSameAmount(BankAccount account1, BankAccount account2) {
        requireSame("amount in account", account1.getBalance(), account2.getBalance());
        requireSame("transactions count", account1.getTransactionsCount(), account2.getTransactionsCount());
    }

    public static void main(final String[] args) {
        final AccountHolder aRossi = new AccountHolder("Andrea", "Rossi", 1);
        final AccountHolder aBianchi = new AccountHolder("Alex", "Bianchi", 2);
        /*
         * Change SimpleBankAccount to ExtendedStrictBankAccount
         */
        final BankAccount rossisAccount = new SimpleBankAccount(aRossi.getUserID(), 0);
        final BankAccount bianchisAccount = new StrictBankAccount(aBianchi.getUserID(), 0);
        requireSameAmount(rossisAccount, bianchisAccount);
        // First deposit
        rossisAccount.deposit(aRossi.getUserID(), INTIAL_AMOUNT);
        bianchisAccount.deposit(aBianchi.getUserID(), INTIAL_AMOUNT);
        requireSameAmount(rossisAccount, bianchisAccount);
        // Withdraw
        rossisAccount.withdraw(aRossi.getUserID(), WITHDRAW_AMOUNT);
        bianchisAccount.withdraw(aBianchi.getUserID(), WITHDRAW_AMOUNT);
        requireSameAmount(rossisAccount, bianchisAccount);
        // Second deposit
        rossisAccount.deposit(aRossi.getUserID(), INTIAL_AMOUNT);
        bianchisAccount.deposit(aBianchi.getUserID(), INTIAL_AMOUNT);
        requireSameAmount(rossisAccount, bianchisAccount);
        // Withdraw
        rossisAccount.withdraw(aRossi.getUserID(), WITHDRAW_AMOUNT);
        bianchisAccount.withdraw(aBianchi.getUserID(), WITHDRAW_AMOUNT);
        requireSameAmount(rossisAccount, bianchisAccount);
        // Management fees
        rossisAccount.chargeManagementFees(aRossi.getUserID());
        bianchisAccount.chargeManagementFees(aBianchi.getUserID());
        requireSameAmount(rossisAccount, bianchisAccount);
    }
}

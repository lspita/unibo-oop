package it.unibo.bank.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the {@link SimpleBankAccount} class.
 */
class TestSimpleBankAccount {
    private AccountHolder mRossi;
    private AccountHolder aBianchi;
    private BankAccount bankAccount;

    private static final int AMOUNT = 100;
    private static final int ACCEPTABLE_MESSAGE_LENGTH = 10;

    /**
     * Configuration step: this is performed BEFORE each test.
     */
    @BeforeEach
    void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.aBianchi = new AccountHolder("Andrea", "Bianchi", 2);
        this.bankAccount = new SimpleBankAccount(mRossi, 0.0);
    }

    /**
     * Check that the initialization of the BankAccount is created with the correct values.
     */
    @Test
    void testBankAccountInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Check that the deposit is performed correctly on the Bank Account.
     */
    @Test
    void testBankAccountDeposit() {
        for (int i = 0; i < 10;) {
            assertEquals(i, bankAccount.getTransactionsCount());
            assertEquals(i * AMOUNT, bankAccount.getBalance());
            bankAccount.deposit(mRossi.getUserID(), AMOUNT);
            i++;
            assertEquals(i * AMOUNT, bankAccount.getBalance());
            assertEquals(i, bankAccount.getTransactionsCount());
        }
    }

    /**
     * Checks that if the wrong AccountHolder id is given, the deposit will return an IllegalArgumentException.
     */
    @Test
    void testWrongBankAccountDeposit() {
        try {
            bankAccount.deposit(aBianchi.getUserID(), AMOUNT);
            Assertions.fail("Depositing from a wrong account was possible, but should have thrown an exception");
        } catch (IllegalArgumentException e) {
            assertEquals(0, bankAccount.getBalance()); // No money was deposited, balance is consistent
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
            assertTrue(e.getMessage().length() >= ACCEPTABLE_MESSAGE_LENGTH); // A message with a decent length
        }
        /*
         * Conciser alternative
         * (once you learn reflection, and preferably after you have learnt lambda expressions):
         * Assertions.assertThrows
         *
         * Use only if you **already** know reflection and lambda expressions.
         */
    }

}

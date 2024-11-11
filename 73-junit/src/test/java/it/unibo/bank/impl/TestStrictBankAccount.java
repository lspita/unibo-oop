package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {
    private static final double START_BALANCE = 0.0;
    private static final int ACCOUNT_HOLDER_ID = 1;
    private static final double EXTRA_MONEY_AMOUNT = 100.0;
    private static final double DEPOSIT_MONEY_AMOUNT = 100.0;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", ACCOUNT_HOLDER_ID);
        this.bankAccount = new StrictBankAccount(this.mRossi, START_BALANCE);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(START_BALANCE, this.bankAccount.getBalance());
        assertEquals(0, this.bankAccount.getTransactionsCount());
        assertEquals(this.mRossi, this.bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        this.bankAccount.deposit(ACCOUNT_HOLDER_ID, DEPOSIT_MONEY_AMOUNT);
        final double feeAmount = 
            StrictBankAccount.MANAGEMENT_FEE + 
            this.bankAccount.getTransactionsCount() * StrictBankAccount.TRANSACTION_FEE;
        
        this.bankAccount.chargeManagementFees(ACCOUNT_HOLDER_ID);
        assertEquals(DEPOSIT_MONEY_AMOUNT - feeAmount, this.bankAccount.getBalance());        
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.bankAccount.withdraw(ACCOUNT_HOLDER_ID, -1);
        });
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.bankAccount.withdraw(
                ACCOUNT_HOLDER_ID, 
                this.bankAccount.getBalance() + EXTRA_MONEY_AMOUNT
            );
        });
    }
}

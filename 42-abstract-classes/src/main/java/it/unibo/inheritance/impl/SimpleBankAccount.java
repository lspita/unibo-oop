package it.unibo.inheritance.impl;

import it.unibo.inheritance.api.AccountHolder;
import it.unibo.inheritance.api.BankAccount;

public class SimpleBankAccount implements BankAccount {

    protected static final double ATM_TRANSACTION_FEE = 1;
    protected static final double MANAGEMENT_FEE = 5;

    private final AccountHolder holder;
    private double balance;
    private int transactions;

    public SimpleBankAccount(final AccountHolder accountHolder, final double balance) {
        this.holder = accountHolder;
        this.balance = balance;
        this.transactions = 0;
    }

    public void chargeManagementFees(final int id) {
        /*
         * Riduce il bilancio del conto di un ammontare pari alle spese di gestione
         */
        if (checkUser(id)) {
            this.balance -= SimpleBankAccount.MANAGEMENT_FEE;
            resetTransactions();
        }
    }

    public void deposit(final int id, final double amount) {
        /*
         * Incrementa il numero di transazioni e aggiunge amount al totale del
         * conto Nota: il deposito va a buon fine solo se l'id utente
         * corrisponde
         */
        this.transactionOp(id, amount);
    }

    public void depositFromATM(final int id, final double amount) {
        /*
         * Incrementa il numero di transazioni e aggiunge amount al totale del
         * conto detraendo le spese (costante ATM_TRANSACTION_FEE) relative
         * all'uso dell'ATM (bancomat) Nota: il deposito va a buon fine solo se
         * l'id utente corrisponde
         */
        this.deposit(id, amount - SimpleBankAccount.ATM_TRANSACTION_FEE);
    }

    @Override
    public AccountHolder getAccountHolder() {
        return holder;
    }

    public double getBalance() {
        return this.balance;
    }

    protected void setBalance(final double balance) {
        this.balance = balance;
    }

    public int getTransactionsCount() {
        return this.transactions;
    }

    public void withdraw(final int id, final double amount) {
        /*
         * Incrementa il numero di transazioni e rimuove amount al totale del
         * conto. Note: - Il conto puo' andare in rosso (ammontare negativo) -
         * Il prelievo va a buon fine solo se l'id utente corrisponde
         */
        this.transactionOp(id, -amount);
    }

    public void withdrawFromATM(final int id, final double amount) {
        /*
         * Incrementa il numero di transazioni e rimuove amount + le spese
         * (costante ATM_TRANSACTION_FEE) relative all'uso dell'ATM (bancomat)
         * al totale del conto. Note: - Il conto puo' andare in rosso (ammontare
         * negativo) - Il prelievo va a buon fine solo se l'id utente
         * corrisponde
         */
        this.withdraw(id, amount + SimpleBankAccount.ATM_TRANSACTION_FEE);
    }

    protected boolean checkUser(final int id) {
        return this.getAccountHolder().getUserID() == id;
    }

    protected void incrementTransactions() {
        this.transactions++;
    }

    protected void resetTransactions() {
        this.transactions = 0;
    }

    private void transactionOp(final int id, final double amount) {
        if (checkUser(id)) {
            this.balance += amount;
            this.incrementTransactions();
        }
    }
}

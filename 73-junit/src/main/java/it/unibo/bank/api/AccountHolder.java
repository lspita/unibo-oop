package it.unibo.bank.api;

/**
 * A BankAccount holder.
 */
public final class AccountHolder {
    private final String name;
    private final String surname;
    private final int id;

    /**
     * Builds a new {@link AccountHolder}.
     * @param name the name of the bank account holder.
     * @param surname the surname of the bank account holder.
     * @param id the user id of the bank account holder.
     */
    public AccountHolder(final String name, final String surname, final int id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    /**
     * The name of the account holder.
     *
     * @return the name of the bank account holder.
     */
    public String getName() {
        return this.name;
    }

    /**
     * The surname of the account holder.
     *
     * @return the surname of the bank account holder.
     */
    public String getSurname() {
        return this.surname;
    }

    /**
     * The user id of the account holder.
     *
     * @return the user id of the bank account holder.
     */
    public int getUserID() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AccountHolder [name=" + this.name
            + ", surname=" + this.surname
            + ", userID=" + this.id + "]";
    }
}

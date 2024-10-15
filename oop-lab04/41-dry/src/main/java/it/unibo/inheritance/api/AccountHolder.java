package it.unibo.inheritance.api;

public class AccountHolder {

    private final String name;
    private final String surname;
    private final int id;

    public AccountHolder(final String name, final String surname, final int id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public int getUserID() {
        return this.id;
    }

    public String toString() {
        return "AccountHolder [name=" + this.name
            + ", surname=" + this.surname
            + ", userID=" + this.id + "]";
    }
}

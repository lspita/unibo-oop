package it.unibo.mvc;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation of {@link Controller}.
 */
public final class SimpleController implements Controller {

    private final List<String> printHistory = new ArrayList<>();
    private String currentMessage;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMessage(final String message) {
        this.currentMessage = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        return this.currentMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getHistory() {
        return List.copyOf(this.printHistory);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException if no message is set
     */
    @Override
    public void printMessage() {
        if (this.currentMessage == null) {
            throw new IllegalStateException("Message is unset");
        }

        System.out.println(this.currentMessage); // NOPMD required for exercise
        this.printHistory.add(this.currentMessage);
    }

}

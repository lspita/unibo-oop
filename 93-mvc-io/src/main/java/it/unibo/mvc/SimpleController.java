package it.unibo.mvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Basic implementation of {@link Controller}.
 */
public final class SimpleController implements Controller {

    private final List<String> printHistory = newHistory();
    private String currentMessage;

    private List<String> newHistory(final String... elements) {
        return newHistory(Arrays.asList(elements));
    }

    private List<String> newHistory(final Collection<String> elements) {
        return new ArrayList<>(elements);
    }

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
        return newHistory(this.printHistory);
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

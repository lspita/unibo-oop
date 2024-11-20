package it.unibo.mvc;

import java.util.List;

/**
 * Interface of the base app MVC controller.
 */
public interface Controller {

    /**
     * Set next message to print.
     *
     * @param message message to print
     */
    void setMessage(String message);

    /**
     * Get next message to print.
     *
     * @return the message
     */
    String getMessage();

    /**
     * Get copy of history of all the printed messages.
     *
     * @return list of messages
     */
    List<String> getHistory();

    /**
     * Print currently setted message.
     */
    void printMessage();
}

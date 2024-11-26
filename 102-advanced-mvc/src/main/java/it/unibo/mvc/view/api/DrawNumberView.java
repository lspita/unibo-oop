package it.unibo.mvc.view.api;

import it.unibo.mvc.model.DrawResult;

/**
 *
 */
public interface DrawNumberView {

    /**
     * Attach a controller to the view.
     * 
     * @param observer the controller to attach
     */
    void setObserver(DrawNumberViewObserver observer);

    /**
     * This method is called before the UI is used. It should finalize its
     * status (if needed).
     */
    void start();

    /**
     * Informs the user that the inserted number is not correct.
     */
    void numberIncorrect();

    /**
     * Use the result of the last draw
     * 
     * @param res the result
     */
    void result(DrawResult res);

    /**
     * Informs the user that an error occured
     * 
     * @param message the error message
     */
    void displayError(String message);
}

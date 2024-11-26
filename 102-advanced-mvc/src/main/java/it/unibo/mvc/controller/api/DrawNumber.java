package it.unibo.mvc.controller.api;

import it.unibo.mvc.model.DrawResult;

/**
 * This class models a draw number game.
 *
 */
public interface DrawNumber {

    /**
     * resets the game.
     */
    void reset();

    /**
     * Guesses a number.
     *
     * @param n the guess
     * @return the result of the guess
     */
    DrawResult attempt(int n);
}

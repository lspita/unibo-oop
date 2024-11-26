package it.unibo.mvc;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import it.unibo.mvc.controller.api.DrawNumber;
import it.unibo.mvc.controller.impl.DrawNumberImpl;
import it.unibo.mvc.model.DrawResult;
import it.unibo.mvc.view.api.DrawNumberView;
import it.unibo.mvc.view.api.DrawNumberViewObserver;
import it.unibo.mvc.view.impl.DrawNumberViewImpl;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private static final int MIN = 0;
    private static final int MAX = 100;
    private static final int ATTEMPTS = 10;

    private final DrawNumber model;
    private final List<DrawNumberView> views;

    /**
     * @param views the views to attach
     */
    public DrawNumberApp(final DrawNumberView... views) {
        /*
         * Side-effect proof
         */
        this.views = Arrays.asList(Arrays.copyOf(views, views.length));
        this.model = new DrawNumberImpl(MIN, MAX, ATTEMPTS);
    }

    /**
     * Start the application on every view
     */
    public void start() {
        for (final DrawNumberView view : views) {
            view.setObserver(this);
            view.start();
        }
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view : views) {
                view.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView view : views) {
                view.numberIncorrect();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        /*
         * A bit harsh. A good application should configure the graphics to exit by
         * natural termination when closing is hit. To do things more cleanly, attention
         * should be paid to alive threads, as the application would continue to persist
         * until the last thread terminates.
         */
        System.exit(0);
    }

    /**
     * @param args ignored
     * @throws FileNotFoundException
     */
    public static void main(final String... args) throws FileNotFoundException {
        final var app = new DrawNumberApp(new DrawNumberViewImpl());
        app.start();
    }

}

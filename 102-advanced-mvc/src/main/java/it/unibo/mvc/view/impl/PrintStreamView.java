package it.unibo.mvc.view.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import it.unibo.mvc.model.DrawResult;
import it.unibo.mvc.view.api.DrawNumberView;
import it.unibo.mvc.view.api.DrawNumberViewObserver;

/**
 * This class implements a view that can write on any PrintStream.
 */
public final class PrintStreamView implements DrawNumberView {

    private static final String ERROR_PREFIX = "[ERR] ";

    private final PrintStream out;

    /**
     * Builds a new PrintStreamView.
     *
     * @param stream the {@link PrintStream} where to write
     */
    public PrintStreamView(final PrintStream stream) {
        out = stream;
    }

    /**
     * Builds a {@link PrintStreamView} that writes on file, given a path.
     *
     * @param path a file path
     * @throws FileNotFoundException
     */
    public PrintStreamView(final String path) throws FileNotFoundException {
        out = new PrintStream(new FileOutputStream(new File(path)));
    }

    @Override
    public void setObserver(final DrawNumberViewObserver observer) {
        /*
         * This UI is output only.
         */
    }

    @Override
    public void start() {
        /*
         * PrintStreams are always ready.
         */
    }

    @Override
    public void numberIncorrect() {
        out.println("You number is incorrect");
    }

    @Override
    public void result(final DrawResult res) {
        out.println(res.getDescription());
    }

    @Override
    public void displayError(String message) {
        out.println(ERROR_PREFIX + message);
    }

}

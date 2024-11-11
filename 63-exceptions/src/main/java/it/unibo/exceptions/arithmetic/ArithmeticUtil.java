package it.unibo.exceptions.arithmetic;

import java.util.Objects;

import static java.lang.Double.parseDouble;

/**
 * Shared utilities.
 */
public final class ArithmeticUtil {

    private ArithmeticUtil() { }

    /**
     * Checks whether a String can be parsed as a finite double.
     * Returns null if so, and a NumberFormatException otherwise.
     *
     * @param data the string to
     * @return null if the provided String can be parsed as a finite double
     */
    public static NumberFormatException nullIfNumberOrException(final String data) {
        Objects.requireNonNull(data);
        try {
            // Much better with a regex-match on "[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?"
            if (Double.isFinite(parseDouble(data))) {
                return null;
            } else {
                return new NumberFormatException(data + " is not a finite number");
            }
        } catch (final NumberFormatException e) {
            return e; // NOPMD
        }
    }
}

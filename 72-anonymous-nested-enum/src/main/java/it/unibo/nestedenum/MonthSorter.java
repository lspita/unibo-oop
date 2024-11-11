package it.unibo.nestedenum;

import java.util.Comparator;

/**
 * A factory of Comparators that interprets strings as Month names.
 * <p>
 * Implementations must be flexible and consider both upper and lower case strings,
 * as well as partial names.
 * </p>
 * <p>
 * For instance,
 * {@code "January"},
 * {@code "JANUARY"},
 * {@code "january"}
 * {@code "Jan"}, and
 * {@code "JA"}
 * should all match the month January.
 * Instead, {@code "J"} should throw an error, as it is ambiguous (January, June, July).
 * Similarly, {@code "Janissary"} should throw an error, as no month with such name.
 * </p>
 */
public interface MonthSorter {

    /**
     * Provides a comparator that sorts months by number of days.
     *
     * @return a comparator that sorts the Strings based on the number of days.
     */
    Comparator<String> sortByDays();

    /**
     * Provides a comparator that sorts months by their order in time.
     *
     * @return a comparator that sorts the Strings based on their order in time.
     */
    Comparator<String> sortByOrder();
}

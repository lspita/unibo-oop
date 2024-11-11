package it.unibo.nestedenum;

import it.unibo.functional.api.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static it.unibo.functional.Transformers.flattenTransform;
import static it.unibo.functional.Transformers.reject;
import static it.unibo.functional.Transformers.select;
import static it.unibo.functional.Transformers.transform;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the {@link MonthSorterNested} class.
 */
class TestMonthSorter {

    private static final int TIMES = 100;
    private static final int SHORT_MONTH = 28;
    private static final int USUAL_MONTH = 30;
    private static final int LONG_MONTH = 31;
    private static final List<String> ALL_MONTHS_ORDERED = List.of(
        "january",
        "february",
        "march",
        "april",
        "may",
        "june",
        "july",
        "august",
        "september",
        "october",
        "november",
        "december"
    );

    private static final Map<String, Integer> MONTHLY_DAYS = new LinkedHashMap<>() {{ // NOPMD
        // Class-initializer syntax
        put("january", LONG_MONTH);
        put("february", SHORT_MONTH);
        put("march", LONG_MONTH);
        put("april", USUAL_MONTH);
        put("may", LONG_MONTH);
        put("june", USUAL_MONTH);
        put("july", LONG_MONTH);
        put("august", LONG_MONTH);
        put("september", USUAL_MONTH);
        put("october", LONG_MONTH);
        put("november", USUAL_MONTH);
        put("december", LONG_MONTH);
    }};

    private static final List<List<String>> EXPECTED_RESULTS = List.of(
        ALL_MONTHS_ORDERED,
        transform(ALL_MONTHS_ORDERED, new Function<>() {
            @Override
            public String call(final String input) {
                return input.substring(0, 3);
            }
        }),
        // test multiple entries
        flattenTransform(ALL_MONTHS_ORDERED, new Function<String, List<String>>() {
            @Override
            public List<String> call(final String input) {
                return List.of(input, input, input);
            }
        }),
        transform(ALL_MONTHS_ORDERED, new Function<>() {
            @Override
            public String call(final String input) {
                return input.toUpperCase(Locale.ROOT);
            }
        }),
        select(ALL_MONTHS_ORDERED, new Function<>() {
            @Override
            public Boolean call(final String input) {
                return input.startsWith("j");
            }
        }),
        reject(ALL_MONTHS_ORDERED, new Function<>() {
            @Override
            public Boolean call(final String input) {
                return input.endsWith("y");
            }
        }),
        List.of("jan", "F", "march", "April", "JUNE", "July", "AUG", "Sept", "dec")
    );

    @Test
    void testSorting() {
        final var randomGenerator = new Random(1);
        final MonthSorter sorter = new MonthSorterNested();
        for (final var expected: EXPECTED_RESULTS) {
            final var expectedDays = monthsToDays(expected);
            expectedDays.sort(new Comparator<>() {
                @Override
                public int compare(final Integer o1, final Integer o2) {
                    return Integer.compare(o1, o2);
                }
            });
            for (int i = 0; i < TIMES; i++) {
                final var shallowCopy = transform(expected, Function.identity());
                while (expected.equals(shallowCopy)) {
                    Collections.shuffle(shallowCopy, randomGenerator);
                }
                assertNotEquals(expected, shallowCopy);
                shallowCopy.sort(sorter.sortByOrder());
                assertEquals(expected, shallowCopy);
                shallowCopy.sort(sorter.sortByDays());
                assertEquals(expectedDays, monthsToDays(shallowCopy));
            }
        }
    }

    @Test
    void testAmbiguousAndIllegalName() {
        final var ambiguous = Arrays.asList("J", "dec");
        final var illegal = Arrays.asList("sunny", "November");
        final var sorter = new MonthSorterNested();
        for (final var target: List.of(ambiguous, illegal)) {
            for (final var comparator: List.of(sorter.sortByDays(), sorter.sortByOrder())) {
                assertThrows(IllegalArgumentException.class, makeExecutable(target, comparator));
            }
        }
    }

    private static Executable makeExecutable(final List<String> target, final Comparator<String> comparator) {
        return new Executable() {
            @Override
            public void execute() throws Throwable {
                target.sort(comparator);
            }
        };
    }

    private static List<Integer> monthsToDays(final Iterable<String> input) {
        return transform(input, new Function<>() {
            @Override
            public Integer call(final String input) {
                var match = MONTHLY_DAYS.get(input);
                if (match == null) {
                    for (final var entry: MONTHLY_DAYS.entrySet()) {
                        if (entry.getKey().toLowerCase(Locale.ROOT).startsWith(input.toLowerCase(Locale.ROOT))) {
                            if (match != null) {
                                throw new IllegalStateException(
                                    "Ambiguous entry: " + input + " matches both " + match + " and " + entry.getKey()
                                );
                            }
                            match = entry.getValue();
                        }
                    }
                }
                return Objects.requireNonNull(match, "No valid month found for " + input);
            }
        });
    }
}

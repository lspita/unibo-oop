package it.unibo.functional;

import it.unibo.functional.api.Function;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.unibo.functional.Transformers.flatten;
import static it.unibo.functional.Transformers.flattenTransform;
import static it.unibo.functional.Transformers.reject;
import static it.unibo.functional.Transformers.select;
import static it.unibo.functional.Transformers.transform;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the {@link Transformers}.
 */
class TestFunctionalLibrary {

    private static final List<String> LOREM_IPSUM = List.of(
        "Lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt".split("\\s")
    );

    @Test
    void testTransform() {
        assertEquals(
            // CHECKSTYLE: MagicNumber OFF
            List.of(5, 5, 5, 3, 4, 11, 10, 4, 3, 2, 7, 6, 10),
            // CHECKSTYLE: MagicNumber ON
            transform(LOREM_IPSUM, new Function<String, Integer>() {
                @Override
                public Integer call(final String input) {
                    return input.length();
                }
            })
        );
    }


    @Test
    void testFlatten() {
        final Function<String, List<String>> triplicator = new Function<>() {
            @Override
            public List<String> call(final String input) {
                return List.of(input, input, input);
            }
        };
        final var base = transform(LOREM_IPSUM, triplicator);
        assertEquals(flattenTransform(LOREM_IPSUM, triplicator), flatten(base));
    }

    @Test
    void testSelectAndReject() {
        final var startsWithAOrS = new Function<String, Boolean>() {
            @Override
            public Boolean call(final String input) {
                return input.startsWith("a") || input.startsWith("s");
            }
        };
        assertEquals(
            List.of("sit", "amet", "adipiscing", "sed"),
            select(LOREM_IPSUM, startsWithAOrS)
        );
        assertEquals(
            List.of("Lorem", "ipsum", "dolor", "consectetur", "elit", "do", "eiusmod", "tempor", "incididunt"),
            reject(LOREM_IPSUM, startsWithAOrS)
        );
    }
}

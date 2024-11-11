package it.unibo.collections.comparators;

import java.util.Comparator;

public class DoubleStringComparator implements Comparator<String> {
    @Override
    public int compare(final String o1, final String o2) {
        final double d1 = Double.parseDouble(o1);
        final double d2 = Double.parseDouble(o2);

        return Double.compare(d1, d2);
    }
}

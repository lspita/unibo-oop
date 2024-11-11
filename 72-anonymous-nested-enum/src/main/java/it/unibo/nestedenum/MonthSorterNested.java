package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.List;

import it.unibo.functional.Transformers;
import it.unibo.functional.api.Function;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    /**
     * Month of the year
     */
    public enum Month {
        /**
         * 1st month of the year
         */
        JANUARY(Month.LONG_DAYS),
        /**
         * 2nd month of the year
         */
        FEBRUARY(Month.SHORT_DAYS),
        /**
         * 3rd month of the year
         */
        MARCH(Month.LONG_DAYS),
        /**
         * 4th month of the year
         */
        APRIL,
        /**
         * 5th month of the year
         */
        MAY(Month.LONG_DAYS),
        /**
         * 6th month of the year
         */
        JUNE,
        /**
         * 7th month of the year
         */
        JULY(Month.LONG_DAYS),
        /**
         * 8th month of the year
         */
        AUGUST(Month.LONG_DAYS),
        /**
         * 9th month of the year
         */
        SEPTEMBER,
        /**
         * 10th month of the year
         */
        OCTOBER(Month.LONG_DAYS),
        /**
         * 11th month of the year
         */
        NOVEMBER,
        /**
         * 12th month of the year
         */
        DECEMBER(Month.LONG_DAYS);
        
        /**
         * Default month length
         */
        public static final int DEFAULT_DAYS = 30;
        /**
         * Long month length
         */
        public static final int LONG_DAYS = 31;
        /**
         * Short month length
         */
        public static final int SHORT_DAYS = 28;
        
        private final int days;
        
        private Month(final int days) {
            this.days = days;
        }

        private Month() {
            this(DEFAULT_DAYS);
        }

        /**
         * Get the amount of days in the month
         * 
         * @return amount of days
         */
        public int getDays() {
            return this.days;
        }

        /**
         * Get month from name, even if incomplete
         * @param monthName name to search
         * @return the associated month
         * @throws IllegalArgumentException if the name is invalid or ambiguous
         */
        public static Month fromString(final String monthName) throws IllegalArgumentException {
            final String monthNameUppercase = monthName.toUpperCase();
            final List<Month> candidates = Transformers.select(
                List.of(Month.values()), 
                new Function<Month, Boolean>() {
                    @Override
                    public Boolean call(Month input) {
                        return input.toString().startsWith(monthNameUppercase);
                    }
                    
                }
            );
            if (candidates.size() == 1) {
                return candidates.get(0);
            }
            throw new IllegalArgumentException("Invalid month name");
        }
    }

    private final class SortByDays implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            final Month month1 = Month.fromString(o1);   
            final Month month2 = Month.fromString(o2);
            return month1.getDays() - month2.getDays();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<String> sortByDays() {
        return new SortByDays();
    }

    private final class SortByMonthOrder implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            final Month month1 = Month.fromString(o1);   
            final Month month2 = Month.fromString(o2);
            return month1.compareTo(month2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }
}

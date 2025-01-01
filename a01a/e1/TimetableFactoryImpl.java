package a01a.e1;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class TimetableFactoryImpl implements TimetableFactory {

    private static <T> Set<T> setUnion(final Set<T> set1, final Set<T> set2) {
        final var unionSet = new HashSet<>(set1);
        unionSet.addAll(set2);
        return unionSet;
    }

    public record TimetableImpl(Set<String> activities, Set<String> days, BiFunction<String, String, Integer> durationProvider) implements Timetable {

        @Override
        public Timetable addHour(final String activity, final String day) {
            return new TimetableImpl(
                setUnion(this.activities, Set.of(activity)),
                setUnion(this.days, Set.of(day)),
                (a, d) -> durationProvider.apply(a, d) + (activity.equals(a) && day.equals(d) ? 1 : 0)
            );
        }
    
        @Override
        public int getSingleData(final String activity, final String day) {
            return this.durationProvider.apply(activity, day);
        }
    
        @Override
        public int sums(final Set<String> activities, final Set<String> days) {
            return activities.stream()
                .flatMap(a -> days.stream().map(d -> new Pair<>(a, d)))
                .mapToInt(pair -> this.getSingleData(pair.get1(), pair.get2()))
                .sum();
        }
        
    }

    @Override
    public Timetable empty() {
        return new TimetableImpl(Collections.emptySet(), Collections.emptySet(), (activity, day) -> 0);
    }

    @Override
    public Timetable single(final String activity, final String day) {
        return this.empty().addHour(activity, day);
    }

    @Override
    public Timetable join(final Timetable table1, final Timetable table2) {
        return new TimetableImpl(
            setUnion(table1.activities(), table2.activities()),
            setUnion(table1.days(), table2.days()),
            (a, d) -> table1.getSingleData(a, d) + table2.getSingleData(a, d)
        );
    }

    @Override
    public Timetable cut(final Timetable table, final BiFunction<String, String, Integer> bounds) {
        return new TimetableImpl(
            table.activities(), 
            table.days(),
            (a, d) -> Math.min(table.getSingleData(a, d), bounds.apply(a, d))
        );
    }

}

package a01c.e1;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class TimeSheetFactoryImpl implements TimeSheetFactory {

    @FunctionalInterface
    private static interface DataFilter {
        public boolean test(String activity, String day, int hours);
    }

    private record TimeSheetImpl(
        Set<String> activities, 
        Set<String> days, 
        BiFunction<String, String, Integer> dataFunction,
        DataFilter dataFilter
    ) implements TimeSheet {
        @Override
        public int getSingleData(String activity, String day) {
            return activities.contains(activity) && days.contains(day) ? dataFunction.apply(activity, day) : 0;
        }
        @Override
        public boolean isValid() {
            return activities.stream()
                .flatMap(a -> days.stream().map(d -> new Pair<>(a, d)))
                .allMatch(pair -> dataFilter.test(pair.get1(), pair.get2(), getSingleData(pair.get1(), pair.get2())));
        }
    }

    @Override
    public TimeSheet ofRawData(final List<Pair<String, String>> data) {
        return this.withBounds(data, Collections.emptyMap(), Collections.emptyMap());
    }

    @Override
    public TimeSheet withBoundsPerActivity(final List<Pair<String, String>> data, final Map<String, Integer> boundsOnActivities) {
        return this.withBounds(data, boundsOnActivities, Collections.emptyMap());
    }

    @Override
    public TimeSheet withBoundsPerDay(final List<Pair<String, String>> data, final Map<String, Integer> boundsOnDays) {
        return this.withBounds(data, Collections.emptyMap(), boundsOnDays);
    }
    
    @Override
    public TimeSheet withBounds(
        final List<Pair<String, String>> data, 
        final Map<String, Integer> boundsOnActivities, 
        final Map<String, Integer> boundsOnDays
    ) {
        return new TimeSheetImpl(
            data.stream().map(Pair::get1).collect(Collectors.toSet()),
            data.stream().map(Pair::get2).collect(Collectors.toSet()),
            (a, d) -> (int) data.stream().filter(new Pair<>(a, d)::equals).count(),
            (a, d, hours) -> hours <= boundsOnActivities.getOrDefault(a, hours) && hours <= boundsOnDays.getOrDefault(d, hours)
        );
    }

}

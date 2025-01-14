package a01c.e1;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TimeSheetFactoryImpl implements TimeSheetFactory {

    public record TimeSheetImpl(
        Set<String> activities, 
        Set<String> days,
        BiFunction<String, String, Integer> dataFunction,
        BiPredicate<String, Integer> activityFilter,
        BiPredicate<String, Integer> dayFilter
    ) implements TimeSheet {

        @Override
        public int getSingleData(final String activity, final String day) {
            return activities.contains(activity) && days.contains(day) ? dataFunction.apply(activity, day) : 0;
        }

        @Override
        public boolean isValid() {
            System.out.println(activities);
            System.out.println(days);
            return 
                activities.stream().allMatch(
                    a -> activityFilter.test(a, days.stream().collect(Collectors.summingInt(d -> getSingleData(a, d))))
                ) &&
                days.stream().allMatch(
                    d -> dayFilter.test(d, activities.stream().collect(Collectors.summingInt(a -> getSingleData(a, d))))
                );
        }
    }

    private Set<String> dataConverter(
        final List<Pair<String, String>> data, 
        final Function<Pair<String, String>, String> mapper
    ) {
        return data.stream().map(mapper).collect(Collectors.toSet());
    }

    @Override
    public TimeSheet ofRawData(final List<Pair<String, String>> data) {
        return withBounds(data, Collections.emptyMap(), Collections.emptyMap());
    }

    @Override
    public TimeSheet withBoundsPerActivity(final List<Pair<String, String>> data, final Map<String, Integer> boundsOnActivities) {
        return withBounds(data, boundsOnActivities, Collections.emptyMap());
    }

    @Override
    public TimeSheet withBoundsPerDay(final List<Pair<String, String>> data, final Map<String, Integer> boundsOnDays) {
        return withBounds(data, Collections.emptyMap(), boundsOnDays);
    }

    @Override
    public TimeSheet withBounds(
        final List<Pair<String, String>> data, 
        final Map<String, Integer> boundsOnActivities,
        final Map<String, Integer> boundsOnDays
    ) {
        return new TimeSheetImpl(
            dataConverter(data, Pair::get1),
            dataConverter(data, Pair::get2), 
            (a, d) -> (int) data.stream().filter(new Pair<>(a, d)::equals).count(),
            (a, h) -> h <= boundsOnActivities.getOrDefault(a, h),
            (d, h) -> h <= boundsOnDays.getOrDefault(d, h)
        );
    }

}

package a01b.e1;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class TimeSheetFactoryImpl implements TimeSheetFactory {

    private TimeSheet ofInstance(
        final List<String> activities, 
        final List<String> days,
        final BiFunction<List<String>, List<String>, BiFunction<String, String, Integer>> mapper
    ) {
        return new TimeSheetImpl(activities, days, mapper.apply(activities, days));
    }

    private TimeSheet ofRaw(
        final int numActivities, 
        final int numDays,
        final BiFunction<List<String>, List<String>, BiFunction<String, String, Integer>> mapper
    ) {
        return ofInstance(
            getRawList(numActivities, "act"), 
            getRawList(numDays, "day"),
            mapper
        );
    }

    private List<String> getRawList(final int size, final String prefix) {
        return IntStream.rangeClosed(1, size)
            .mapToObj(Integer::toString)
            .map(prefix::concat)
            .toList();
    }

    @Override
    public TimeSheet flat(int numActivities, int numDays, int hours) {
        return ofRaw(
            numActivities,
            numDays,
            (activities, days) -> (a, d) -> activities.contains(a) && days.contains(d) ? hours : 0
        );
    }

    @Override
    public TimeSheet ofListsOfLists(List<String> activities, List<String> days, List<List<Integer>> data) {
        return ofInstance(
            List.copyOf(activities),
            List.copyOf(days),
            (newActivities, newDays) -> (a, d) -> data.get(newActivities.indexOf(a)).get(newDays.indexOf(d))
        );
    }

    @Override
    public TimeSheet ofRawData(int numActivities, int numDays, List<Pair<Integer, Integer>> data) {
        return ofRaw(
            numActivities,
            numDays, 
            (activities, days) -> (a, d) -> (int) data.stream()
                .filter(
                    new Pair<>(activities.indexOf(a), days.indexOf(d))::equals
                )
                .count()
        );
    }

    @Override
    public TimeSheet ofPartialMap(List<String> activities, List<String> days, Map<Pair<String, String>, Integer> data) {
        return ofInstance(
            List.copyOf(activities), 
            List.copyOf(days), 
            (newActivities, newDays) -> (a, d) -> data.getOrDefault(new Pair<>(a, d), 0)
        );
    }

}

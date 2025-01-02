package a01b.e1;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TimeSheetFactoryImpl implements TimeSheetFactory {

    private static final String RAW_ACTIVITY_PREFIX = "act";
    private static final String RAW_DAY_PREFIX = "day";

    public record TimeSheetImpl(List<String> activities, List<String> days, BiFunction<String, String, Integer> data) implements TimeSheet {
        private record StringTimePair(String key, int time) { }
        
        @Override
        public int getSingleData(String activity, String day) {
            return this.data.apply(activity, day);
        }
        
        @Override
        public Map<String, Integer> sumsPerActivity() {
            return this.activities.stream()
                .map(a -> new StringTimePair(a, this.days.stream().mapToInt(d -> this.getSingleData(a, d)).sum()))
                .collect(Collectors.toMap(StringTimePair::key, StringTimePair::time));
        }

        @Override
        public Map<String, Integer> sumsPerDay() {
            return this.days.stream()
                .map(d -> new StringTimePair(d, this.activities.stream().mapToInt(a -> this.getSingleData(a, d)).sum()))
                .collect(Collectors.toMap(StringTimePair::key, StringTimePair::time));
        }
    }

    private TimeSheet newInstance(final List<String> activities, final List<String> days, final BiFunction<String, String, Integer> data) {
        return new TimeSheetImpl(List.copyOf(activities), List.copyOf(days), data);
    }

    private String prefixNumber(final String prefix, final int i) {
        return new StringBuilder(prefix).append(i).toString();
    }

    private String rawActivityName(final int i) {
        return prefixNumber(RAW_ACTIVITY_PREFIX, i);
    }

    private String rawDayName(final int i) {
        return prefixNumber(RAW_DAY_PREFIX, i);
    }

    private List<String> generateRaw(final Function<Integer, String> nameGenerator, final int quantity) {
        return IntStream.rangeClosed(1, quantity)
            .mapToObj(nameGenerator::apply)
            .toList();
    }

    private List<String> rawActivities(final int quantity) {
        return this.generateRaw(this::rawActivityName, quantity);
    }

    private List<String> rawDays(final int quantity) {
        return this.generateRaw(this::rawDayName, quantity);
    }

    @Override
    public TimeSheet flat(int numActivities, int numNames, int hours) {
        final var activities = this.rawActivities(numActivities);
        final var days = this.rawDays(numNames);
        return this.newInstance(activities, days, (a, d) -> activities.contains(a) && days.contains(d) ? hours : 0);
    }

    @Override
    public TimeSheet ofListsOfLists(final List<String> activities, final List<String> days, final List<List<Integer>> data) {
        return this.newInstance(activities, days, (a, d) -> {
            final var activityIndex = activities.indexOf(a);
            final var dayIndex = days.indexOf(d);
            try {
                return data.get(activityIndex).get(dayIndex);
            } catch (IndexOutOfBoundsException e) {
                return 0;
            }
        });
    }

    @Override
    public TimeSheet ofRawData(int numActivities, int numDays, List<Pair<Integer, Integer>> data) {
        return this.ofPartialMap(
            this.rawActivities(numActivities),
            this.rawDays(numDays),
            data.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> Map.entry(
                    new Pair<>(this.rawActivityName(entry.getKey().get1() + 1), this.rawDayName(entry.getKey().get2() + 1)),
                    (int) entry.getValue().longValue()
                ))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue))
        );
    }

    @Override
    public TimeSheet ofPartialMap(List<String> activities, List<String> days, Map<Pair<String, String>, Integer> data) {
        return this.newInstance(activities, days, (a, d) -> data.getOrDefault(new Pair<>(a, d), 0));
    }

}

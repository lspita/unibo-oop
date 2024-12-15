package a01a.e1;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TimetableImpl implements Timetable {

    private final Map<Pair<String, String>, Integer> data;

    TimetableImpl(final Map<Pair<String, String>, Integer> data) {
        this.data = data;
    }

    @Override
    public Timetable addHour(String activity, String day) {
        this.data.merge(new Pair<>(activity, day), 1, Integer::sum);
        return new TimetableImpl(this.data);
    }

    @Override
    public Set<String> activities() {
        return this.data.keySet().stream()
            .map(Pair::get1)
            .collect(Collectors.toSet());
    }

    @Override
    public Set<String> days() {
        return this.data.keySet().stream()
            .map(Pair::get2)
            .collect(Collectors.toSet());
    }

    @Override
    public int getSingleData(String activity, String day) {
        return this.data.getOrDefault(new Pair<>(activity, day), 0);
    }

    @Override
    public int sums(Set<String> activities, Set<String> days) {
        return activities.stream()
            .mapToInt(
                a -> days.stream()
                    .mapToInt(d -> this.getSingleData(a, d))
                    .sum()
            ).sum();
    }
    
}

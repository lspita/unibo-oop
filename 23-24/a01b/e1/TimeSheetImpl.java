package a01b.e1;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public record TimeSheetImpl(
    List<String> activities, 
    List<String> days,
    BiFunction<String, String, Integer> mapper // function (activity, day) -> time spent
) implements TimeSheet {

    @Override
    public int getSingleData(String activity, String day) {
        return this.mapper.apply(activity, day);
    }

    @Override
    public Map<String, Integer> sumsPerActivity() {
        return this.sumsPerList(this.activities);
    }

    @Override
    public Map<String, Integer> sumsPerDay() {
        return this.sumsPerList(this.days);
    }

    private Map<String, Integer> sumsPerList(final List<String> keysList) {
        final var sumList = keysList == this.activities ? this.days : this.activities;
        return keysList.stream()
            .map(k -> new Pair<>(
                k, 
                sumList.stream()
                    .mapToInt(v -> keysList == this.activities ? this.getSingleData(k, v) : this.getSingleData(v, k))
                    .sum()
            )).collect(Collectors.toMap(Pair::get1, Pair::get2));
    }
    
}

package a01a.e1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class TimetableFactoryImpl implements TimetableFactory {

    private Timetable ofData(final Map<Pair<String, String>, Integer> data) {
        return new TimetableImpl(data);
    }

    @Override
    public Timetable empty() {
        return this.ofData(new HashMap<>());
    }

    @Override
    public Timetable single(String activity, String day) {
        return this.empty().addHour(activity, day);
    }

    @Override
    public Timetable join(Timetable table1, Timetable table2) {
        return this.joinWithCopies(table1, table2, table2::getSingleData);
    }

    @Override
    public Timetable cut(Timetable table, BiFunction<String, String, Integer> bounds) {
        return this.joinWithCopies(this.empty(), table, (a, d) -> Math.min(table.getSingleData(a, d), bounds.apply(a, d)));
    }

    private Timetable joinWithCopies(
        final Timetable table1, 
        final Timetable table2, 
        final BiFunction<String, String, Integer> copiesCalculator
    ) {
        return table2.activities().stream()
            .flatMap(
                a -> table2.days().stream()
                    .map(d -> new Pair<>(a, d))
                    .flatMap(
                        ad -> Stream.generate(() -> ad)
                            .limit(copiesCalculator.apply(ad.get1(), ad.get2()))
                    )
            )
            .reduce(
                table1, 
                (t, ad) -> t.addHour(ad.get1(), ad.get2()),
                this::join
            );
    }
    
}

package a01c.sol1;

import java.util.*;
import java.util.stream.*;

import java.util.function.*;

public class TimeSheetFactoryImpl implements TimeSheetFactory {

    // An implementation of a Timesheet as an immutable class with activities, days, and an association of activities+days to hours
    // Since it is immutable, we use a record, which automatically gives fields, getters, constructors, hashcode, equals, toString.
    private static record TimeSheetData(Set<String> activities, Set<String> days, BiFunction<String, String, Integer> fun) implements TimeSheet {

        @Override
        public int getSingleData(String activity, String day) {
            return fun.apply(activity,day);
        }

        @Override
        public boolean isValid() {
            return true;
        }
    }

    // We handle various validy policies, and combine them, by the decorator pattern
    private static class TimeSheetDecorator implements TimeSheet {
        private final TimeSheet base;

        public TimeSheetDecorator(TimeSheet base) {
            this.base = base;
        }

        public Set<String> activities() {
            return base.activities();
        }

        public Set<String> days() {
            return base.days();
        }

        public int getSingleData(String activity, String day) {
            return base.getSingleData(activity, day);
        }

        public boolean isValid() {
            return base.isValid();
        }

        public int sumPerActivity(String activity) {
            return days().stream()
                            .map(day -> getSingleData(activity,day))
                            .collect(Collectors.summingInt(i->i));
        }

        public int sumPerDay(String day) {
            return activities().stream()
                            .map(act -> getSingleData(act,day))
                            .collect(Collectors.summingInt(i->i));
        }
    }

    @Override
    public TimeSheet ofRawData(List<Pair<String, String>> data) {
        var activities = data.stream().map(Pair::get1).sorted().collect(Collectors.toSet());
        var days = data.stream().map(Pair::get2).sorted().collect(Collectors.toSet());
        return new TimeSheetData(
            activities,
            days, 
            (a,d) -> (int)data.stream().filter(p -> p.get1().equals(a) && p.get2().equals(d)).count());
    }

    private TimeSheet decorateWithBoundsPerActivity(TimeSheet base, Map<String, Integer> bounds) {
        return new TimeSheetDecorator(base){
            public boolean isValid() {
                return base.isValid() && bounds.entrySet()
                        .stream()
                        .allMatch(e -> sumPerActivity(e.getKey()) <= e.getValue());
            }
        };

    }

    private TimeSheet decorateWithBoundsPerDay(TimeSheet base, Map<String, Integer> bounds) {
        return new TimeSheetDecorator(base){
            public boolean isValid() {
                return base.isValid() && bounds.entrySet()
                        .stream()
                        .allMatch(e -> sumPerDay(e.getKey()) <= e.getValue());
            }
        };

    }

    private TimeSheet decorateWithBounds(TimeSheet base, Map<String, Integer> boundsPerActivity, Map<String, Integer> boundsPerDay){
        return decorateWithBoundsPerActivity(decorateWithBoundsPerDay(base, boundsPerDay), boundsPerActivity);
    }

    @Override
    public TimeSheet withBoundsPerActivity(List<Pair<String, String>> data, Map<String, Integer> bounds) {
        return decorateWithBoundsPerActivity(ofRawData(data), bounds);
    }

    @Override
    public TimeSheet withBoundsPerDay(List<Pair<String, String>> data, Map<String, Integer> bounds) {
        return decorateWithBoundsPerDay(ofRawData(data), bounds);
    }

    @Override
    public TimeSheet withBounds(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities, Map<String, Integer> boundsOnDays) {
        return decorateWithBounds(ofRawData(data), boundsOnActivities, boundsOnDays);
    }

}

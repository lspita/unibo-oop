package a01b.sol1;

import java.util.*;
import java.util.stream.*;

import java.util.function.*;

public class TimeSheetFactoryImpl implements TimeSheetFactory {

    // An implementation of a Timetable as an immutable class with activities, days, and an association of activities+days to hours
    // Since it is immutable, we use a record, which automatically gives fields, getters, constructors, hashcode, equals, toString.
    private static record TimeSheetData(List<String> activities, List<String> days, BiFunction<String, String, Integer> fun) implements TimeSheet {

        @Override
        public Map<String, Integer> sumsPerActivity() {
            return activities.stream()
                    .map(act -> new Pair<>(act, days.stream()
                            .map(day -> fun.apply(act,day))
                            .collect(Collectors.summingInt(i->i))))
                    .collect(Collectors.toMap(Pair::get1, Pair::get2));
        }

        @Override
        public Map<String, Integer> sumsPerDay() {
            return days.stream()
                    .map(day -> new Pair<>(day,activities.stream()
                            .map(act -> fun.apply(act,day))
                            .collect(Collectors.summingInt(i->i))))
                    .collect(Collectors.toMap(Pair::get1, Pair::get2));
        }

        @Override
        public int getSingleData(String activity, String day) {
            return activities.contains(activity) && days.contains(day) ? fun.apply(activity,day) : 0;
        }
    }

    private static List<String> createActivities(int numActivities){
        return Stream.iterate(1, i -> i + 1).map(i -> "act"+i).limit(numActivities).collect(Collectors.toList());
    }

    private static List<String> createDays(int numDays){
        return Stream.iterate(1, i -> i + 1).map(i -> "day"+i).limit(numDays).collect(Collectors.toList());
    }


    @Override
    public TimeSheet flat(int numActivities, int numDays, int hours) {
        var activities = createActivities(numActivities);
        var days = createDays(numDays);
        return new TimeSheetData(
            activities,
            days, 
            (a,d) -> hours);
    }

    @Override
    public TimeSheet ofRawData(int numActivities, int numDays, List<Pair<Integer, Integer>> data) {
        var activities = createActivities(numActivities);
        var days = createDays(numDays);
        return new TimeSheetData(
            activities,
            days, 
            (a,d) -> (int)data.stream().filter(p -> p.get1().equals(activities.indexOf(a)) && p.get2().equals(days.indexOf(d))).count());
    }

    @Override
    public TimeSheet ofListsOfLists(List<String> activities, List<String> days, List<List<Integer>> data) {
        return new TimeSheetData(
            List.copyOf(activities),
            List.copyOf(days), 
            (a,d) -> data.get(activities.indexOf(a)).get(days.indexOf(d)));
    }

    @Override
    public TimeSheet ofPartialMap(List<String> activities, List<String> days, Map<Pair<String, String>, Integer> data) {
        return new TimeSheetData(
            List.copyOf(activities),
            List.copyOf(days), 
            (a,d) -> data.getOrDefault(new Pair<>(a,d),0));
    }
}

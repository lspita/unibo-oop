package a01d.e1;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import a01d.e1.Timetable.Day;

public class TimetableFactoryImpl implements TimetableFactory {

    private static <X> Set<X> concatSet(final Set<X> left, final Set<X> right) {
        return Stream.concat(left.stream(), right.stream()).collect(Collectors.toSet());
    }

    private static <X> Set<X> concatSet(final Set<X> left, final X right) {
        return concatSet(left, Set.of(right));
    }

    interface DataFunction {
        List<Integer> getHours(String room, String course, Day day);
    }

    private record TimetableImpl(
        Set<String> rooms, 
        Set<String> courses,
        DataFunction dataFunction
    ) implements Timetable {

        private List<Integer> hours(final Set<String> rooms, Set<String> courses, Set<Day> days) {
            return rooms.stream()
                .flatMap(r -> courses.stream().flatMap(
                    c -> days.stream().flatMap(d -> dataFunction.getHours(r, c, d).stream())
                ))
                .sorted()
                .distinct()
                .toList();
        }

        @Override
        public List<Integer> hours() {
            return hours(rooms, courses, Set.of(Day.values()));
        }

        @Override
        public Timetable addBooking(final String room, final String course, final Day day, final int hour, final int duration) {
            return new TimetableImpl(
                concatSet(rooms, room),
                concatSet(courses, course),
                (r, c, d) -> r.equals(room) && c.equals(course) && d.equals(day) ? 
                    Stream.iterate(hour, h -> h < hour + duration, h -> h + 1).toList() :
                    dataFunction.getHours(r, c, d)
            );
        }

        @Override
        public Optional<Integer> findPlaceForBooking(final String room, final Day day, final int duration) {
            final var occupiedHours = hours(Set.of(room), courses, Set.of(day));
            final var freeHours = hours().stream().filter(h -> !occupiedHours.contains(h)).toList();
            return freeHours.stream()
                .filter(
                    h -> Stream.iterate(0, i -> i < freeHours.size() - freeHours.indexOf(h), i -> i + 1)
                        .filter(i -> freeHours.indexOf(h + i) == freeHours.indexOf(h) + i)
                        .count() >= duration
                ).findFirst();

        }

        @Override
        public Map<Integer, String> getDayAtRoom(final String room, final Day day) {
            final var coursesHours = courses.stream()
                .map(c -> new Pair<>(c, hours(Set.of(room), Set.of(c), Set.of(day))))
                .toList();
            
            return hours(Set.of(room), courses, Set.of(day)).stream().collect(Collectors.toMap(
                Function.identity(),
                h -> coursesHours.stream().filter(ch -> ch.get2().contains(h)).map(Pair::get1).findFirst().get()
            ));
        }

        @Override
        public Optional<Pair<String, String>> getDayAndHour(final Day day, final int hour) {
            return courses.stream()
                .flatMap(c -> rooms.stream().map(r -> new Pair<>(c, r)))
                .filter(pair -> hours(Set.of(pair.get2()), Set.of(pair.get1()), Set.of(day)).contains(hour))
                .findFirst();
        }

        @Override
        public Map<Day, Map<Integer, String>> getCourseTable(final String course) {
            return Set.of(Day.values()).stream()
                .map(d -> new Pair<>(
                    d, 
                    hours(rooms, Set.of(course), Set.of(d)).stream()
                        .map(h -> new Pair<>(h, getDayAndHour(d, h).map(p -> p.get2())))
                        .filter(p -> p.get2().isPresent())
                        .collect(Collectors.toMap(Pair::get1, p -> p.get2().get()))
                )).filter(pair -> pair.get2().size() > 0)
                .collect(Collectors.toMap(Pair::get1, Pair::get2));
        }

    }

    @Override
    public Timetable empty() {
        return new TimetableImpl(Collections.emptySet(), Collections.emptySet(), (r, c, d) -> Collections.emptyList());
    }

}

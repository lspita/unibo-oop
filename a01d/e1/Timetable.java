package a01d.e1;

import java.util.*;

/**
 * It models the timetable of lessons for a give group of students following the same set of courses,
 * associating to each course where and when (day + hour) it has lessons.
 * It is expected that, once created, the timetable is immutable.
 */
public interface Timetable {

    public static enum Day { MON, TUE, WED, THU, FRI }


    /**
     * @return the rooms used by this timetable
     */
    Set<String> rooms();

    /**
     * @return the courses covered by this timetable
     */
    Set<String> courses();

    /**
     * @return the hours in which courses has some lesson, in this timetable
     * e.g., if OOP has lessons from 9 to 12, it contributes with hours 9, 10 and 11
     */
    List<Integer> hours();

    /**
     * @param room
     * @param course
     * @param day
     * @param hour
     * @param duration
     * @return a new timetable similar to 'this' but with an additional booking for a @course, in @room, @day, @hour and @duration
     * e.g., if it's a booking at 9 with duration 3, it covers hours 9, 10 and 11
     * It can be assumed that this booking won't interfere with others, and no check has to be implemented.
     */
    Timetable addBooking(String room, String course, Day day, int hour, int duration);

    /**
     * @param room
     * @param day
     * @param duration
     * @return a hour in the day (taken from hours()), such that a booking in @room at @day from that hour for @duration 
     * would be available without interfering with other pre-existing bookings:
     * - if many hours are available, the method should return the earliest (smaller) possible
     * - the result hour should be in hours()
     */
    Optional<Integer> findPlaceForBooking(String room, Day day, int duration);

    /**
     * @param room
     * @param day
     * @return a map from hours of the day to courses that have lesson in that @day in that @room
     */
    Map<Integer, String> getDayAtRoom(String room, Day day); // 9 --> OOP, 10 --> OOP, 11 --> SISOP, 12 --> SISOP

    /**
     * @param day
     * @param hour
     * @return (optionally, if existing) a pair of a room and a course having lesson there at @day and @hour
     */
    Optional<Pair<String, String>> getDayAndHour(Day day, int hour); // 4.1 --> OOP, 4.2 --> SISOP

    /**
     * @param course
     * @return a map associating to days a map, itself associating to hours in which room there is a lesson of @course
     * // please, refer to tests
     */
    Map<Day, Map<Integer, String>> getCourseTable(String course); // day --> hour --> 4.1

}

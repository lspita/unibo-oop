package a01d.e1;

public interface TimetableFactory {
    
    /**
     * @return creates an empty timetable, the starting point for creating other ones
     * (typically with the Timetable method addBooking()) 
     */
    Timetable empty();
}

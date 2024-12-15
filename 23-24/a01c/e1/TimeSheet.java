package a01c.e1;

import java.util.*;

/**
 * Models a timesheet, a table in which each row reports the number of hours (>= 0) spent in each activity, and each column represents a given day.
 * Though not strictly necessary, a timesheet implementation may be immutable.
 * A specific implementation may provide rules on the validity of the timesheet: e.g. we can't have more than 8 hours of work in a day.
 */
public interface TimeSheet {
	
	/**
	 * @return the set of (names of) activities (e.g. "act1", "act2",...)
	 */
	Set<String> activities();

	/**
	 * @return the list of (names of) days (e.g. "day1", "day2",...)
	 */
	Set<String> days();

	/**
	 * @param activity
	 * @param day
	 * @return how many hours were spent on @activity at the given @day
	 */
	int getSingleData(String activity, String day);

	/**
	 * @return whether the hours of this timesheet are valid, according to the specific rules of the implementation
	 */
	boolean isValid();

}

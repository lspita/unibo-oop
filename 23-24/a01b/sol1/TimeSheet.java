package a01b.sol1;

import java.util.List;
import java.util.Map;

/**
 * Models a timesheet, a table in which each row reports the number of hours (>= 0) spent in each activity, and each column represents a given day.
 * Though not strictly necessary, a timesheet implementation may be immutable.
 */
public interface TimeSheet {
	
	/**
	 * @return the list of (names of) activities (e.g. "act1", "act2",...)
	 */
	List<String> activities();

	/**
	 * @return the list of (names of) days (e.g. "day1", "day2",...)
	 */
	List<String> days();

	/**
	 * @param activity
	 * @param day
	 * @return how many hours were spent on @activity at the given @day
	 */
	int getSingleData(String activity, String day);

	/**
	 * @return a map from activity (names) to hours spent on that activity overall
	 */
	Map<String, Integer> sumsPerActivity();

	/**
	 * @return a map from day (names) to hours spent on that day overall
	 */
	Map<String, Integer> sumsPerDay();
}

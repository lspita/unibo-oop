package a01c.sol1;

import java.util.*;

public interface TimeSheetFactory {
	
	/**
	 * @param data, expressed as a list in which each element (activity,day) represents a single hour in the timesheet
	 * @return a timesheet corresponding to the input @data, and with no constraint on hours
	 */
	TimeSheet ofRawData(List<Pair<String, String>> data);

	/**
	 * @param data, expressed as a list in which each element (activity,day) represents a single hour in the timesheet
	 * @param boundsOnActivities, as a map from activity and maximum number of hours of such activity overall
	 * @return a timesheet corresponding to the input @data, and with the @boundsOnActivities constraint
	 */
	TimeSheet withBoundsPerActivity(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities);

	/**
	 * @param data, expressed as a list in which each element (activity,day) represents a single hour in the timesheet
	 * @param boundsOnDays, as a map from days and maximum number of hours in such a day overall
	 * @return a timesheet corresponding to the input @data, and with the @boundsOnDays constraint
	 */
	TimeSheet withBoundsPerDay(List<Pair<String, String>> data, Map<String, Integer> boundsOnDays);

	/**
	 * @param data, expressed as a list in which each element (activity,day) represents a single hour in the timesheet
	 * @param boundsOnActivities, as a map from activity and maximum number of hours of such activity overall
	 * @param boundsOnDays, as a map from days and maximum number of hours in such a day overall
	 * @return a timesheet corresponding to the input @data, and with the @boundsOnDays and @boundsOnActivities constraint
	 */
	TimeSheet withBounds(List<Pair<String, String>> data, Map<String, Integer> boundsOnActivities, Map<String, Integer> boundsOnDays);
}

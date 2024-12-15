package a01b.e1;

import java.util.*;

public interface TimeSheetFactory {
	
	/**
	 * @param numActivities
	 * @param numNames
	 * @param hours
	 * @return a timesheet with:
	 * - activities "act1",..."actn" (numActivities elements)
	 * - days "day1",..."daym" (numDays elements)
	 * - the same number of hours spent in each acticity in each day
	 */
	TimeSheet flat(int numActivities, int numNames, int hours);

	/**
	 * @param activities
	 * @param days
	 * @param data
	 * @return a timesheet with:
	 * - activities as indicated in the argument
	 * - days as indicated in the argument
	 * - data.get(i).get(j) is the number of hours spent in i-th activity in j-th day
	 */
	TimeSheet ofListsOfLists(List<String> activities, List<String> days, List<List<Integer>> data);

	/**
	 * @param activities
	 * @param days
	 * @param data
	 * @return a timesheet with:
	 * - activities "act1",..."actn" (numActivities elements)
	 * - days "day1",..."daym" (numDays elements)
	 * - for each single hour spent in i-th activity in j-th day there's one Pair<>(i,j) in data 
	 */
	TimeSheet ofRawData(int numActivities, int numDays, List<Pair<Integer,Integer>> data);

	/**
	 * @param activities
	 * @param days
	 * @param data
	 * @return a timesheet with:
	 * - activities as indicated in the argument
	 * - days as indicated in the argument
	 * - @data maps pairs (activity,day) to hours spent (if greater than zero)
	 */
	TimeSheet ofPartialMap(List<String> activities, List<String> days, Map<Pair<String,String>,Integer> data);
}

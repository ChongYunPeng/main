//@@author A0132785Y
package doordonote.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import doordonote.common.Task;

public class DateUtil {
	
	/** String objects for days of the week */
	private static final String DAY_SUN = "Sunday";
	private static final String DAY_MON = "Monday";
	private static final String DAY_TUES = "Tuesday";
	private static final String DAY_WED = "Wednesday";
	private static final String DAY_THURS = "Thursday";
	private static final String DAY_FRI = "Friday";
	private static final String DAY_SAT = "Saturday";
	
	/** String objects for months of the year */
	private static final String MONTH_JAN = "Jan";
	private static final String MONTH_FEB = "Feb";
	private static final String MONTH_MAR = "Mar";
	private static final String MONTH_APR = "Apr";
	private static final String MONTH_MAY = "May";
	private static final String MONTH_JUN = "Jun";
	private static final String MONTH_JUL = "Jul";
	private static final String MONTH_AUG = "Aug";
	private static final String MONTH_SEPT = "Sept";
	private static final String MONTH_OCT = "Oct";
	private static final String MONTH_NOV = "Nov";
	private static final String MONTH_DEC = "Dec";
	
	/** String objects for time period conventions */
	private static final String TIME_AM = "am";
	private static final String TIME_PM = "pm";
	
	/** Messages displayed for assertion fails */
	private static final String MESSAGE_ASSERT_WRONG_DAY = "Day cannot be outside the range 1-7";
	private static final String MESSAGE_ASSERT_WRONG_MONTH = "Month cannot be outside the range 0-11";
	private static final String MESSAGE_ASSERT_NEGATIVE_TIME = "Time cannot be negative";
	private static final String MESSAGE_ASSERT_WRONG_MINUTES = "Minutes cannot exceed 59";
	private static final String MESSAGE_ASSERT_WRONG_HOUR = "Hour cannot exceed 23";
	
	/**
	 * Converts a Date object to a Calendar object and returns it, 
	 * since some of Date's API has been deprecated
	 *  
	 * @param cal         Date object for the date of a task
	 * 
	 * @return            Calendar object for the Date object passed
	 */
	public static Calendar dateToCalendar(Date date) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal;
	}
	
	/**
	 * Returns a String for the day of the Calendar object
	 * passed for the task (Mon, Tues etc), since 
	 * Calendar only gives an int value for day
	 *  
	 * @param cal         Calendar object for the date of a task
	 * 
	 * @return            String for the day of the cal object
	 */
	public static String getDay(Calendar cal) {
		
		String day = null;
		
		switch(cal.get(Calendar.DAY_OF_WEEK)) {
		  case 1 : {
			  day = DAY_SUN;
			  break;
		  }
		  case 2 : {
			  day = DAY_MON;
				break;
		  }
		  case 3 : {
			  day = DAY_TUES;
			  break;
		  }
		  case 4 : {
			  day = DAY_WED;
			  break;
		  }
		  case 5 : {
			  day = DAY_THURS;
			  break;
		  }
		  case 6 : {
			  day = DAY_FRI;
			  break;
		  }
		  case 7 : {
			  day = DAY_SAT;
			  break;
		  }
		  default : {
			  assert false: MESSAGE_ASSERT_WRONG_DAY; 
		  }
		}

		return day;
	}
	
	/**
	 * Returns a String for the month of the Calendar object
	 * passed for the task (January, February etc), since 
	 * Calendar only gives an int value for month
	 *  
	 * @param cal         Calendar object for the date of a task
	 * 
	 * @return            String for the month of the cal object
	 */
	public static String getMonth(Calendar cal) {
		
		String month = null;
		
		switch(cal.get(Calendar.MONTH)) {
		  case 0 : {
			  month = MONTH_JAN;
			  break;
		  }
		  case 1 : {
			  month = MONTH_FEB;
			  break;
		  }
	
		  case 2 : {
			  month = MONTH_MAR;
			  break;
		  }
		  case 3 : {
			  month = MONTH_APR;
			  break;
		  }
		  case 4 : {
			  month = MONTH_MAY;
			  break;
		  }
		  case 5 : {
			  month = MONTH_JUN;
			  break;
		  }
		  case 6 : {
			  month = MONTH_JUL;
			  break;
		  }
		  case 7 : {
			  month = MONTH_AUG;
			  break;
		  }
		  case 8 : {
			  month = MONTH_SEPT;
			  break;
		  }
		  case 9 : {
			  month = MONTH_OCT;
			  break;
		  }
		  case 10: {
			  month = MONTH_NOV;
			  break;
		  }
		  case 11 : {
			  month = MONTH_DEC;
			  break;
		  }
		  default : {
			  assert false: MESSAGE_ASSERT_WRONG_MONTH; 
		  }
		}

		return month;
	}
	
	/**
	 * Returns a String for the minutes of the Calendar
	 * object passed for the task (in mm format, null for 0 minutes)
	 * since Calendar only gives an int value
	 *  
	 * @param cal         Calendar object for the date of a task
	 * 
	 * @return            String for the minutes of the cal object
	 */
	public static String getMinutes(Calendar cal) {
		
		String minutesString;
		int minutes = cal.get(Calendar.MINUTE);
        
		// Single digit minutes
		if (minutes < 10) {
			if(minutes == 0) {
				minutesString = null;
			}
			else {
				// Minutes cannot be negative
				assert minutes > 0: MESSAGE_ASSERT_NEGATIVE_TIME;
				minutesString = "0" + minutes;
			}
		}
		// Double digit minutes
		else {
			// Minutes cannot be greater than 59 (60 becomes 0)
			assert minutes < 60: MESSAGE_ASSERT_WRONG_MINUTES;
			minutesString = "" + minutes;
		}

		return minutesString;        
	}
	
	/**
	 * Returns a String for the time (hour, minutes and am/pm) of the Calendar
	 * object passed for the task 
	 *  
	 * @param cal         Calendar object for the date of a task
	 * 
	 * @return            String for the time of the cal object
	 */
	public static String getTime(Calendar cal) {
		
		String time;
		String minutes = getMinutes(cal);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
        
		// for pm time period (and conversion to 12-hour clock)
		if (hour > 12) {
			// hour cannot be greater than 23 (24 becomes 0)
			assert hour < 24: MESSAGE_ASSERT_WRONG_HOUR;
			if (minutes != null) {
				time = (hour - 12) + ":" + minutes + TIME_PM;
			}
			else {
				// if minutes are zero, they are simply omitted from string
				time = (hour - 12) + TIME_PM;
			}
		}
		// for am time period
		else if (hour < 12){
			if (minutes != null) {
				if (hour == 0) {

					time = hour + 12 + ":" + minutes + TIME_AM;
				}
				else{
					// hour cannot be negative
					assert hour > 0: MESSAGE_ASSERT_NEGATIVE_TIME;
					time = hour + ":" + minutes + TIME_AM;
				}
			}
			else {
				if (hour == 0) {
					time = hour + 12 + TIME_AM;
				}
				else {
					assert hour > 0: MESSAGE_ASSERT_NEGATIVE_TIME;
					time = hour + TIME_AM;
				}
			}
		}
		// if hour is 12 then pm time period but no conversion needed
		else {
			if (minutes != null) {
				time = hour + ":" + minutes + TIME_PM;
			}
			else {
				time = hour + TIME_PM;
			}
		}

		return time;  

	}
	
	/**
	 * Returns a boolean value after checking if the current day (today)  
	 * is same as the day of the Date object passed
	 *  
	 * @param date        Date object of a task
	 * 
	 * @return            boolean value returning true if day of Date passed
	 *                    is today, and false otherwise
	 */
	public static boolean checkForToday(Date date) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date today = new Date();
		boolean isToday = dateFormat.format(date).equals(dateFormat.format(today));
		
		return isToday;
	}
	
	/**
	 * Returns a boolean value after checking if the current time falls 
	 * after the end time for a task i.e. the task is overdue 
	 *  
	 * @param date        end date Date object of a task
	 * 
	 * @return            boolean value returning true if task is overdue,
	 *                    and false otherwise
	 */
	public static boolean checkForOverdue(Date date) {
		
		Date today = new Date();
		boolean isOverdue = date.before(today);
		
		return isOverdue;

	}
	
	/**
	 * Returns a boolean value after checking if the current time falls 
	 * between the start and end time for a task i.e. the task is ongoing 
	 *  
	 * @param start       start date Date object of a task
	 * @param end       end date Date object of a task
	 * 
	 * @return            boolean value returning true if task is ongoing,
	 *                    and false otherwise
	 */
	public static boolean checkForOngoing(Date start, Date end) {
		
		Date today = new Date();
		boolean isOngoing = (!today.before(start)) && (!today.after(end));
		
		return isOngoing;
	}
	
	/**
	 * Returns a boolean value after checking if the two Tasks
	 * passed are on the same day or not 
	 *  
	 * @param task1       Task object 1 to be checked for same day with task2
	 * @param task2       Task object 2 to be checked for same day with task1
	 * 
	 * @return            boolean value returning true if both Tasks are NOT
	 *                    on the same day, and false otherwise
	 */
	public static boolean checkForSameDay(Task task1, Task task2) {
		
		Calendar calEnd1 = dateToCalendar(task1.getEndDate());
		String month1 = getMonth(calEnd1);
		int date1 = calEnd1.get(Calendar.DAY_OF_MONTH);
		
		Calendar calEnd2 = dateToCalendar(task2.getEndDate());
		String month2 = getMonth(calEnd2);
		int date2 = calEnd2.get(Calendar.DAY_OF_MONTH);
		
		boolean isNotSameDate = date1 != date2 || !(month1.equals(month2));
		
		return isNotSameDate;
		
	}
	
	/**
	 * Returns a boolean value after checking if the event Task 
	 * passed is a multiple day event or not 
	 *  
	 * @param task        EventTask object to be checked for multiple day event 
	 * @param formatter   SimpleDateFormat object to format Date objects
	 * 
	 * @return            boolean value returning true if task is multiple day
	 *                    event, false otherwise
	 */
	public static boolean checkIfMultipleDayEvent(Task task, SimpleDateFormat formatter) {
		
		String startDate = formatter.format(task.getStartDate());
		String endDate = formatter.format(task.getEndDate());

		boolean isMultipleDayEvent = !(startDate.equals(endDate));
		
		return isMultipleDayEvent;
	}
	
}

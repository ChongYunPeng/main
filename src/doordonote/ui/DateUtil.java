//@@author A0132785Y
package doordonote.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private static final String DAY_SUN = "Sunday";
	private static final String DAY_MON = "Monday";
	private static final String DAY_TUES = "Tuesday";
	private static final String DAY_WED = "Wednesday";
	private static final String DAY_THURS = "Thursday";
	private static final String DAY_FRI = "Friday";
	private static final String DAY_SAT = "Saturday";
	
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
	
	private static final String TIME_AM = "am";
	private static final String TIME_PM = "pm";
	
	private static final String MESSAGE_ASSERT_WRONG_DAY = "Day cannot be outside the range 1-7";
	private static final String MESSAGE_ASSERT_WRONG_MONTH = "Month cannot be outside the range 0-11";
	private static final String MESSAGE_ASSERT_NEGATIVE_TIME = "Time cannot be negative";
	private static final String MESSAGE_ASSERT_WRONG_MINUTES = "Minutes cannot exceed 59";
	private static final String MESSAGE_ASSERT_WRONG_HOUR = "Hour cannot exceed 23";
	
	protected static Calendar DateToCalendar(Date date) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal;
	}

	protected static String getDay(Calendar cal) {
		
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

	protected static String getMonth(Calendar cal) {
		
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

	protected static String getMinutes(Calendar cal) {
		
		String minutesString;
		int minutes = cal.get(Calendar.MINUTE);

		if (minutes < 10) {
			if(minutes == 0) {
				minutesString = null;
			}
			else {
				assert minutes > 0: MESSAGE_ASSERT_NEGATIVE_TIME;
				minutesString = "0" + minutes;
			}
		}
		else {
			assert minutes < 60: MESSAGE_ASSERT_WRONG_MINUTES;
			minutesString = "" + minutes;
		}

		return minutesString;        
	}

	protected static String getTime(Calendar cal) {
		
		String time;
		String minutes = getMinutes(cal);
		int hour = cal.get(Calendar.HOUR_OF_DAY);

		if (hour > 12) {
			assert hour < 24: MESSAGE_ASSERT_WRONG_HOUR;
			if (minutes != null) {
				time = (hour - 12) + ":" + minutes + TIME_PM;
			}
			else {
				time = (hour - 12) + TIME_PM;
			}
		}
		else if (hour < 12){
			if (minutes != null) {
				if (hour == 0) {

					time = hour + 12 + ":" + minutes + TIME_AM;
				}
				else{
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

	protected static boolean checkForToday(Date date) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date today = new Date();
		boolean isToday = dateFormat.format(date).equals(dateFormat.format(today));
		
		return isToday;
	}

	protected static boolean checkForOverdue(Date date) {
		
		Date today = new Date();
		boolean isOverdue = date.before(today);
		
		return isOverdue;

	}

	protected static boolean checkForOngoing(Date start, Date end) {
		
		Date today = new Date();
		boolean isOngoing = (!today.before(start)) && (!today.after(end));
		
		return isOngoing;
	}
}

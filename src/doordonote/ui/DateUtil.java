package doordonote.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	protected static Calendar DateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	protected static String getDay(Calendar cal) {
		String day = null;
		switch(cal.get(cal.DAY_OF_WEEK)) {
		case 1: day = "Sunday";
		break;
		case 2: day = "Monday";
		break;
		case 3: day = "Tuesday";
		break;
		case 4: day = "Wednesday";
		break;
		case 5: day = "Thursday";
		break;
		case 6: day = "Friday";
		break;
		case 7: day = "Saturday";

		}

		return day;
	}

	protected static String getMonth(Calendar cal) {
		String month = null;
		switch(cal.get(cal.MONTH)) {
		case 0: month = "Jan";
		break;
		case 1: month = "Feb";
		break;
		case 2: month = "Mar";
		break;
		case 3: month = "Apr";
		break;
		case 4: month = "May";
		break;
		case 5: month = "Jun";
		break;
		case 6: month = "Jul";
		break;
		case 7: month = "Aug";
		break;
		case 8: month = "Sept";
		break;
		case 9: month = "Oct";
		break;
		  case 10: month = "Nov";
		break;
		  case 11 : month = "Dec";
		}

		return month;
	}

	protected static String getMinutes(Calendar cal) {
		String minutes;

		if(cal.get(cal.MINUTE) < 10) {
			if(cal.get(cal.MINUTE) == 0) {
				minutes = null;
			}
			else {
				minutes = "0" + cal.get(cal.MINUTE);
			}
		}
		else {
			minutes = "" + cal.get(cal.MINUTE);
		}

		return minutes;        
	}

	protected static String getTime(Calendar cal) {
		String time;
		String minutes = getMinutes(cal);
		int hour = cal.get(cal.HOUR_OF_DAY);

		if(hour > 12) {
			if(minutes != null) {
				time = (hour - 12) + ":" + minutes + "pm";
			}
			else {
				time = (hour - 12) + "pm";
			}
		}
		else if (hour < 12){
			if(minutes != null) {
				if(hour == 0) {

					time = hour+12 + ":" + minutes + "am";
				}
				else{
					time = hour + ":" + minutes + "am";
				}
			}
			else {
				if(hour == 0) {
					time = hour+12 + "am";
				}
				else {
					time = hour + "am";
				}
			}
		}
		else {
			if(minutes != null) {
				time = hour + ":" + minutes + "pm";
			}
			else {
				time = hour + "pm";
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

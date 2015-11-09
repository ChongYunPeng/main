package doordonote.commandfactory;

import java.util.Date;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.joda.time.DateTime;

import com.joestelmach.natty.CalendarSource;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

//@@author A0131436N

/**
 * @author yunpeng
 * A natty date parser wrapper that sets the default time to 8am in the morning.
 * Parser only takes dates in American date format. E.g. 10/3 is parsed as 3 Oct
 */
public class DateParser {
	protected Parser natty = null;

	protected DateParser() {
		BasicConfigurator.configure();
		natty = new Parser();
	}
	
	public Date parse(String input) {
		List<Date> dateList = parseAndGetDateList(input);
		if (dateList == null) {
			return null;
		} else {
			return dateList.get(0);
		}
	}
	
	public Date parse(String input, Date defaultTime) {
		List<Date> dateList = parseAndGetDateList(input, defaultTime);
		if (dateList == null) {
			return null;
		} else {
			return dateList.get(0);
		}
	}
	
	public List<Date> parseAndGetDateList(String input) {
		Date defaultDate = getDateTodayEightAm();
		return parseAndGetDateList(input, defaultDate);
	}

	private Date getDateTodayEightAm() {
		DateTime midnightToday = new DateTime().withTimeAtStartOfDay();
		DateTime eightAm = midnightToday.plusHours(8);
		Date defaultDate = eightAm.toDate();
		return defaultDate;
	}
	
	public List<Date> parseAndGetDateList(String input, Date defaultTime) {
		assert(input != null); // input should always be checked to be no null
		
		CalendarSource.setBaseDate(defaultTime);
		List<DateGroup> baseDateGroupList = natty.parse(input);
		if (baseDateGroupList.isEmpty()) {
			return null;
		}
		DateGroup baseDateGroup = baseDateGroupList.get(0);
		List<Date> dateList = baseDateGroup.getDates();
		return dateList;
	}
}

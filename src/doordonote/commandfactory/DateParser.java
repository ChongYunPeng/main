package doordonote.commandfactory;

import java.util.Date;
import java.util.List;

import com.joestelmach.natty.CalendarSource;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * @author yunpeng
 * A natty date parser wrapper that sets the default time to 8am in the morning.
 * Parser only takes dates in American date format. E.g. 10/3 is parsed as 3 Oct
 */
public class DateParser {
	protected Parser natty = null;
	protected Date defaultDate = null;

	public DateParser() {
		natty = new Parser();
		defaultDate = parse("today 8am");
	}
	
	public Date parse(String input) {
		List<Date> dateList = parseAndGetDateList(input);
		if (dateList == null || dateList.isEmpty()) {
			return null;
		} else {
			return dateList.get(0);
		}
	}
	
	public List<Date> parseAndGetDateList(String input) {
		// input should always be checked to be no null
		assert(input != null);
		CalendarSource.setBaseDate(defaultDate);
		List<DateGroup> baseDateList = natty.parse(input);
		try {
			DateGroup baseDateGroup = baseDateList.get(0);
			List<Date> dateList = baseDateGroup.getDates();
			if (dateList.isEmpty()) {
				return null;
			} else {
				return dateList;
			}
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		
	}
}
